package org.mocker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Answer {
    private int status = 200;
    private MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    private String body = "";

    private boolean httpException = false;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MultiValueMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(MultiValueMap<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isHttpException() {
        return httpException;
    }

    public void setHttpException(boolean httpException) {
        this.httpException = httpException;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "status=" + status +
                ", headers=" + headers +
                ", body=" + body +
                '}';
    }
}
