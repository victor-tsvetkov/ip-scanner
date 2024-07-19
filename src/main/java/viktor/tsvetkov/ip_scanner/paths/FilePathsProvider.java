package viktor.tsvetkov.ip_scanner.paths;

public class FilePathsProvider {
    public static String logsPath;
    public static String hostsPath;
    public static final String username = System.getProperty("user.name");
    public static final String ipScannerDefaultPath = String.format("C:/Users/%s/Desktop/IPScanner", username);
    public static final String defaultLogsPath = String.format("%s/logs/", ipScannerDefaultPath);
    public static final String defaultHostsPath = String.format("%s/hosts/", ipScannerDefaultPath);
}
