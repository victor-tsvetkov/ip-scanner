package viktor.tsvetkov.ip_scanner.launcher;

import lombok.extern.slf4j.Slf4j;

import static viktor.tsvetkov.ip_scanner.utils.FileUtils.createFile;
import static viktor.tsvetkov.ip_scanner.utils.FileUtils.createDirectory;
import static viktor.tsvetkov.ip_scanner.utils.FileUtils.rewriteTextToFile;
import static viktor.tsvetkov.ip_scanner.utils.FileUtils.getTextFromFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class LauncherProperties {

    private final String hostsPath = defaultHostsPath + "hosts.txt";

    public static final String username = System.getProperty("user.name");
    public static final String windowsMainPath = String.format("C:/Users/%s/Desktop/IPScanner", username);
    public static final String linuxMainPath = String.format("/home/%s/Desktop/IPScanner", username);
    public static final String osName = System.getProperty("os.name").toLowerCase();
    public static final String defaultLogsPath = String.format("%s/logs/", osName.contains("windows") ? windowsMainPath : linuxMainPath);
    public static final String defaultHostsPath = String.format("%s/hosts/", osName.contains("windows") ? windowsMainPath : linuxMainPath);

    public LauncherProperties() {
        init();
    }

    public List<String> getMainHosts() {
        String text = getTextFromFile(hostsPath);
        if (text != null) {
            String[] array = text.split(",");
            return new ArrayList<>(Arrays.asList(array));
        }
        return new ArrayList<>();
    }

    public void removeAddress(String host) {
        List<String> hosts = getMainHosts();
        hosts.remove(host);
        String text = String.join(",", hosts);
        rewriteTextToFile(hostsPath, text);
    }

    public void addAddress(String host, int index) {
        List<String> hosts = getMainHosts();
        if (index == -1 || index == hosts.size()) {
            hosts.add(host);
        } else {
            hosts.set(index, host);
        }
        String text = String.join(",", hosts);
        rewriteTextToFile(hostsPath, text);
    }

    private void init() {
        createDirectory(windowsMainPath);
        createDirectory(defaultHostsPath);
        createFile(hostsPath);
    }
}
