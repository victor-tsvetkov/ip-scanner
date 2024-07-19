package viktor.tsvetkov.ip_scanner.launcher;

import lombok.extern.slf4j.Slf4j;

import static viktor.tsvetkov.ip_scanner.paths.FilePathsProvider.hostsPath;
import static viktor.tsvetkov.ip_scanner.paths.FilePathsProvider.defaultHostsPath;
import static viktor.tsvetkov.ip_scanner.paths.FilePathsProvider.ipScannerDefaultPath;

import static viktor.tsvetkov.ip_scanner.utils.FileUtils.createFile;
import static viktor.tsvetkov.ip_scanner.utils.FileUtils.createDirectory;
import static viktor.tsvetkov.ip_scanner.utils.FileUtils.rewriteTextToFile;
import static viktor.tsvetkov.ip_scanner.utils.FileUtils.getTextFromFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class LauncherProperties {

    private final String pathToProperties = hostsPath == null ? defaultHostsPath : hostsPath;
    private final String fileProperties = pathToProperties + "properties.txt";

    public LauncherProperties() {
        init();
    }

    public List<String> getMainHosts() {
        String text = getTextFromFile(fileProperties);
        String[] array = text.split(",");
        return new ArrayList<>(Arrays.asList(array));
    }

    public void removeAddress(String host) {
        List<String> hosts = getMainHosts();
        hosts.remove(host);
        String text = String.join(",", hosts);
        rewriteTextToFile(fileProperties, text);
    }

    public void addAddress(String host, int index) {
        List<String> hosts = getMainHosts();
        if (index == -1 || index == hosts.size()) {
            hosts.add(host);
        } else {
            hosts.set(index, host);
        }
        String text = String.join(",", hosts);
        rewriteTextToFile(fileProperties, text);
    }

    private void init() {
        createDirectory(ipScannerDefaultPath);
        createDirectory(pathToProperties);
        createFile(fileProperties);
    }
}
