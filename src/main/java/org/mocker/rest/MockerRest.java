package org.mocker.rest;

import org.mocker.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MockerRest {

    public MockService mockService;

    @Autowired
    public MockerRest(MockService mockService) {
        this.mockService = mockService;
    }

    @RequestMapping(path = "/**", method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.TRACE},
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    public ResponseEntity mockGet(HttpServletRequest request) throws Exception {
        return mockService.handle(request);
    }

}
