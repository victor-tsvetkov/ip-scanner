package viktor.tsvetkov.ip_scanner.launcher;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Slf4j
public class LauncherProperties {

    private Properties properties;

    public LauncherProperties() {
        init();
    }

    public List<String> getMainHosts() {
        String[] array = properties.getProperty("mainHosts").split(",");
        return new ArrayList<>(Arrays.asList(array));
    }

    private void init() {
        try (InputStream inputStream = this.getClass().
                getClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Error while reading properties: {}", e.getMessage());
        }
    }
}
