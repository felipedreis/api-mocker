package org.mocker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "mock", ignoreInvalidFields = true)
public class MockConfig {
    private List<Provider> providers = new ArrayList<>();

    private Map<String, Provider> providerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        providerMap = providers.stream().collect(Collectors.toMap(Provider::getProvider, Function.identity()));
    }

    public Optional<Provider> getProvider(String providerUrl) {
        Provider provider = providerMap.getOrDefault(providerUrl, null);
        return Optional.ofNullable(provider);
    }


    public Map<String, Provider> getProviderMap() {
        return providerMap;
    }

    public void setProviderMap(Map<String, Provider> providerMap) {
        this.providerMap = providerMap;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    @Override
    public String toString() {
        return "MockConfig{" +
                "providers=" + providerMap +
                '}';
    }
}
