package core.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ConfigReader {

    private static final String CONFIG_FILE = "/config.properties";

    public static void readConfig() {
        readCustomProperties(CONFIG_FILE);          // Read from src/test/resources
    }

    private static void readCustomProperties(String configFileName) {
        log.info("Read config file: {}", configFileName);
        InputStream resourceAsStream = ConfigReader.class.getResourceAsStream(configFileName);
        if (resourceAsStream == null) {
            log.warn("Config file {} not found", configFileName);
        } else {
            try {
                readCustomProperties(resourceAsStream);
            } catch (IOException e) {
                log.error("Config file reading error {}", e.getMessage());
            }
        }
    }

    private static void readCustomProperties(InputStream stream) throws IOException {
        Properties customProperties = new Properties();
        customProperties.load(stream);
        stream.close();
        putCustomPropertiesIntoSystemProperties(customProperties);
    }

    private static void putCustomPropertiesIntoSystemProperties(Properties customProperties) {
        for (Map.Entry<Object, Object> prop : customProperties.entrySet()) {
            if (System.getProperty((String) prop.getKey()) != null) {
                // Avoid overriding property if it was set by JVM option '-D'
            } else {
                System.setProperty((String) prop.getKey(), (String) prop.getValue());
            }
        }
    }
}

