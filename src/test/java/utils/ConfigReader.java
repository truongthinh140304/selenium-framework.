package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static ConfigReader instance;
    private Properties properties;

    private ConfigReader() {
        properties = new Properties();
        String env = System.getProperty("env", "dev");
        String filePath = "src/test/resources/config/" + env + ".properties";

        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Khong the load file config: " + filePath, e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String get(String key) {
        String envValue = null;

        if ("app.username".equals(key)) {
            envValue = System.getenv("SAUCEDEMO_USERNAME");
        } else if ("app.password".equals(key)) {
            envValue = System.getenv("SAUCEDEMO_PASSWORD");
        }

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        return properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}