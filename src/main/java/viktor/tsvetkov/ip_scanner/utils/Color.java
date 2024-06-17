package viktor.tsvetkov.ip_scanner.utils;

import lombok.Getter;

@Getter
public enum Color {
    GREEN("green"), RED("red");

    private final String color;

    Color(String color) {
        this.color = color;
    }
}
