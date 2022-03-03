package org.mocker.config;

import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class Path {
    private String path;
    private Method method = Method.GET;
    private Answer answer;

    private Pattern pathMatcher;

    public boolean matches(String path, Method method) {
        initPathMatcher();

        if (pathMatcher != null)
            return pathMatcher.matcher(path).matches() && this.method.equals(method);
        else return this.path.equals(path) && this.method.equals(method);
    }

    private void initPathMatcher() {
        try {
            if (pathMatcher == null)
                pathMatcher = Pattern.compile(this.path);
        } catch (Exception ex){

        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path='" + path + '\'' +
                " method='" + method + '\'' +
                ", answer=" + answer +
                '}';
    }
}
