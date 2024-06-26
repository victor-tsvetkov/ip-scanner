package viktor.tsvetkov.ip_scanner.logging;

import lombok.Getter;
import viktor.tsvetkov.ip_scanner.utils.DateTimeUtils;
import viktor.tsvetkov.ip_scanner.utils.FileUtils;

import java.util.Date;
import java.util.Set;

@Getter
public class LogFileManager {

    private final String logsFile = "src/main/resources/logs/";

    public void createLogsFile() {
        String filename = DateTimeUtils.formatDateToString("dd.MM.yyyy", new Date()) + ".txt";
        FileUtils.createFile(logsFile + filename);
    }

    public String findTodayFile() {
        Set<String> fileNames = FileUtils.getFilesFromDirectory(logsFile);
        for (String fileName : fileNames) {
            Date date = DateTimeUtils.parseStringToDate(fileName);
            if (DateTimeUtils.isToday(date)) {
                return fileName;
            }
        }
        return null;
    }

    public synchronized void writeLogToFile(String log) {
        String filename = findTodayFile();
        if (filename != null) {
            FileUtils.writeTextToFile(logsFile + filename, log);
        } else {
            createLogsFile();
        }
    }
}
