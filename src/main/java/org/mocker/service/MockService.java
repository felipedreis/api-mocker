package org.mocker.service;

import org.mocker.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MockService {

    private static Logger LOG = LoggerFactory.getLogger(MockService.class);

    private MockConfig mockConfig;

    private RestTemplate restTemplate;

    @Autowired
    public MockService(MockConfig mockConfig) {
        this.mockConfig = mockConfig;
        this.restTemplate = new RestTemplate();
        LOG.debug("The following configuration was found: {}", mockConfig);
    }

    public ResponseEntity handle(HttpServletRequest request) throws Exception {
        LOG.debug("Mocking request for {}", request.getRequestURL());
        URI url = new URI(request.getRequestURI());
        String mockedUrl = url.getPath().substring(1);

        LOG.info("Handling {} request for {}", request.getMethod(), mockedUrl);

        String [] mockedUrlTokens = mockedUrl.split(":");

        String protocol = "", host = "", query = "";

        if (mockedUrlTokens.length == 2) {
            int i = 0;
            protocol = mockedUrlTokens[0];
            host = mockedUrlTokens[1];
            while (host.charAt(i) == '/')
                i++;

            host = host.substring(i);
        }

        if (url.getQuery() != null)
            query = "?" + url.getQuery();

        URI requestUrl = new URI(protocol + "://" + host + query);
        String providerKey = requestUrl.getScheme() + "://" + requestUrl.getHost();

        Optional<Provider> optionalProvider = mockConfig.getProvider(providerKey);

        if (!optionalProvider.isPresent()) {
            LOG.info("Provider {} not found, raising exception", providerKey);
            throw new IllegalArgumentException("Provider " + providerKey + " isn't mocked");
        }

        Provider provider = optionalProvider.get();
        Method method = Method.valueOf(request.getMethod());

        for (Path path : provider.getPaths()) {
            if (path.matches(requestUrl.getPath(), method)) {
                LOG.debug("Path matches {} the mocking {}", requestUrl.getPath(), path);

                return handleAnswer(path.getAnswer());
            }
        }

        LOG.info("Mock not found, applying default behaviour");

        switch (provider.getDefaultBehaviour()) {
            case passthrough:
                return passThrough(request, requestUrl.toURL());
            case deny:
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied");
            case empty:
                return ResponseEntity.ok().body("");

        }

        throw new IllegalArgumentException("No action found for request " + host);
    }

    public ResponseEntity handleAnswer(Answer answer) {

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(answer.getStatus());

        if (answer.getHeaders() != null)
            responseBuilder = responseBuilder.headers(new HttpHeaders(answer.getHeaders()));

        ResponseEntity<String> responseEntity = responseBuilder.body(answer.getBody());

        LOG.debug("Answering {}", responseEntity.getBody());

        return responseEntity;
    }

    public ResponseEntity<String> passThrough(HttpServletRequest request, URL url) {
        String body = "";
        try {
            body = request.getReader().lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            LOG.error("Error parsing request body, assuming it is empty", ex);
        }

        HttpMethod method = HttpMethod.resolve(request.getMethod());

        ResponseEntity<String> responseEntity = restTemplate.exchange(url.toString(), method,
                new HttpEntity<>(body), String.class);

        System.out.println(responseEntity.getBody());
        return responseEntity;
    }
}
