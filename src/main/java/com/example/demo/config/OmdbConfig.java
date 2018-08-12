package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "omdb")
public class OmdbConfig {
    private String host;
    private String apiKey;

    public OmdbConfig() {
    }

    public OmdbConfig(String host, String apiKey) {
        this.host = host;
        this.apiKey = apiKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
