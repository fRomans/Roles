package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    public static Properties getPropertyValue(String fileName) {
        Properties properties = new Properties();
        try (InputStream is = PropertyReader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            return null;
        }
    }
}
