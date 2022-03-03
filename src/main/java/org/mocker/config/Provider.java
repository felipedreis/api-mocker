package org.mocker.config;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Provider {
    private String provider;
    private List<Path> paths = new ArrayList<>();
    private Behaviour defaultBehaviour;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public Behaviour getDefaultBehaviour() {
        return defaultBehaviour;
    }

    public void setDefaultBehaviour(Behaviour defaultBehaviour) {
        this.defaultBehaviour = defaultBehaviour;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "provider='" + provider + '\'' +
                ", paths=" + paths +
                ", defaultBehaviour='" + defaultBehaviour + '\'' +
                '}';
    }
}
