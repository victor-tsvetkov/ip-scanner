package viktor.tsvetkov.ip_scanner.launcher;

import lombok.extern.slf4j.Slf4j;
import viktor.tsvetkov.ip_scanner.executor.SimpleExecutor;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;

import static viktor.tsvetkov.ip_scanner.utils.DateTimeUtils.getCurrentLocalDateTime;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class IPScanner {

    private final List<NetworkNode> mainHosts;
    private SimpleExecutor executor;
    private final StringBuilder stringLog = new StringBuilder();

    public IPScanner(String[] requiredHosts, SimpleExecutor executor) {
        this.mainHosts = new ArrayList<>(requiredHosts.length);
        for (String address : requiredHosts) {
            NetworkNode networkNode = new NetworkNode();
            networkNode.setIpAddress(address);
            this.mainHosts.add(networkNode);
        }
        this.executor = executor;
    }

    public List<NetworkNode> scanMainHosts() {
        return scanHosts(mainHosts);
    }

    private void writeLog(NetworkNode networkNode) {
        String logItem;
        String host = networkNode.getIpAddress();
        try {
            if (InetAddress.getByName(host).isReachable(600)) {
                logItem = getCurrentLocalDateTime() + " Адрес " + host + " в сети";
                log.info(logItem);
                networkNode.setOnline(true);
                networkNode.setLastOnlineTime(getCurrentLocalDateTime());
            } else {
                logItem = getCurrentLocalDateTime() + " Внимание! Адрес " + host + " не в сети!";
                log.warn(logItem);
                networkNode.setOnline(false);
            }
        } catch (UnknownHostException e) {
            logItem = getCurrentLocalDateTime() + " Ошибка при попытке пинга - неизвестный адрес: " + host + "\n";
            logItem += "Подробная информация об ошибке: " + e.getMessage();
            log.error(logItem);
        } catch (IOException e) {
            logItem = e.getMessage();
            log.error(logItem);
        }
        stringLog.append(logItem);
        stringLog.append("\n");
        networkNode.setLog(logItem);
    }

    private List<NetworkNode> scanHosts(List<NetworkNode> hosts) {
        for (NetworkNode host : hosts) {
            executor.execute(() -> writeLog(host));
        }
        boolean executed = executor.close();
        if (executed) {
            log.info("Сканирование узлов завершено");
            executor = new SimpleExecutor();
            return hosts;
        } else {
            return new ArrayList<>();
        }
    }
}
