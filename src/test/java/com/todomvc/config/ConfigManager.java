package com.todomvc.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigManager {
    public static Map<String, Object> config;

    public static void LoadConfig() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config.yml")) {
            config = yaml.load(inputStream);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load config.yml", ex);
        }
    }

    public static Map<String, Object> getConfig() {
        return config;
    }
}