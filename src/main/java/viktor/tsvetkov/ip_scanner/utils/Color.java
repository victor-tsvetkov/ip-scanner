package viktor.tsvetkov.ip_scanner.utils;

import lombok.Getter;

@Getter
public enum Color {
    GREEN("#1EE009"), RED("#E02840"), YELLOW("#E0C200");

    private final String color;

    Color(String color) {
        this.color = color;
    }
}
