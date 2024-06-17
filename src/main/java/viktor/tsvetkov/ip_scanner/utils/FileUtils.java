package viktor.tsvetkov.ip_scanner.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    public static boolean checkIfPathExists(String path) {
        return Files.exists(Path.of(path));
    }

    public static void createFile(String filename) {
        if (!checkIfPathExists(filename)) {
            try {
                Files.createFile(Path.of(filename));
            } catch (Exception e) {
                log.error("Ошибка при создании файла {}, текст ошибки: {}", filename, e.getMessage());
            }
        } else {
            log.warn("Файл с именем {} уже существует", filename);
        }
    }

    public static Path getFile(String filename) {
        if (checkIfPathExists(filename)) {
            return Path.of(filename);
        } else {
            return null;
        }
    }

    public static void createDirectory(String directory) {
        if (!checkIfPathExists(directory)) {
            try {
                Files.createDirectory(Path.of(directory));
            } catch (Exception e) {
                log.error("Ошибка при создании директории {}, текст ошибки: {}", directory, e.getMessage());
            }
        } else {
            log.warn("Директория с именем {} уже существует", directory);
        }
    }

    public static void writeTextToFile(String fileName, String text) {
        if (checkIfPathExists(fileName)) {
            try {
                Files.write(Path.of(fileName), text.getBytes());
            } catch (Exception e) {
                log.error("Ошибка записи в файл {}, текст ошибки: {}", fileName, e.getMessage());
            }
        } else {
            log.warn("Файла с именем {} не существует", fileName);
        }
    }
}
