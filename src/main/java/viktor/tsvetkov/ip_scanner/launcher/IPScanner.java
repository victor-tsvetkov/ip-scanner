package viktor.tsvetkov.ip_scanner.launcher;

import lombok.extern.slf4j.Slf4j;
import viktor.tsvetkov.ip_scanner.executor.SimpleExecutor;
import viktor.tsvetkov.ip_scanner.logging.LogFileManager;
import viktor.tsvetkov.ip_scanner.model.NetworkNode;

import static viktor.tsvetkov.ip_scanner.utils.DateTimeUtils.getCurrentLocalDateTime;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class IPScanner {

    private final List<NetworkNode> mainHosts;
    private SimpleExecutor executor = new SimpleExecutor();
    private final LogFileManager logFileManager;

    public IPScanner(String[] requiredHosts, LogFileManager logFileManager) {
        this.mainHosts = new ArrayList<>(requiredHosts.length);
        for (String address : requiredHosts) {
            NetworkNode networkNode = new NetworkNode();
            networkNode.setIpAddress(address);
            this.mainHosts.add(networkNode);
        }
        this.logFileManager = logFileManager;
    }

    public List<NetworkNode> scanMainHosts() {
        return scanHosts(mainHosts);
    }

    private void writeLog(NetworkNode networkNode) {
        String logItem;
        String host = networkNode.getIpAddress();
        Date start = new Date();
        try {
            if (InetAddress.getByName(host).isReachable(600)) {
                long delay = new Date().getTime() - start.getTime();
                networkNode.setDelayTimeMs(String.valueOf(delay));
                logItem = getCurrentLocalDateTime() + " Адрес " + host + " в сети\n";
                log.info(logItem);
                networkNode.setOnline("В сети");
                networkNode.setLastOnlineTime(getCurrentLocalDateTime());
                networkNode.setLastOnlineText("Сейчас");
            } else {
                logItem = getCurrentLocalDateTime() + " Внимание! Адрес " + host + " не в сети!\n";
                log.warn(logItem);
                networkNode.setOnline("Не в сети");
                networkNode.setDelayTimeMs("-");
                String lastOnlineTime = networkNode.getLastOnlineTime();
                if (networkNode.getLastOnlineTime() != null) {
                    networkNode.setLastOnlineText(lastOnlineTime);
                } else {
                    networkNode.setLastOnlineText("Сегодня не был активен");
                }
            }
        } catch (UnknownHostException e) {
            logItem = getCurrentLocalDateTime() + " Ошибка при попытке пинга - неизвестный адрес: " + host + "\n";
            logItem += "Подробная информация об ошибке: " + e;
            log.error(logItem);
        } catch (IOException e) {
            logItem = e.getMessage();
            log.error(logItem);
        }
        networkNode.setLog(logItem);
        logFileManager.writeLogToFile(logItem);
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
