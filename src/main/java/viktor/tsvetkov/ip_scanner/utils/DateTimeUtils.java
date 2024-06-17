package viktor.tsvetkov.ip_scanner.utils;

import java.time.LocalDateTime;

public class DateTimeUtils {

    public static String getCurrentLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        String month = convertToTimeValue(dateTime, TimeValue.MONTH);
        String day = convertToTimeValue(dateTime, TimeValue.DAY);
        String hours = convertToTimeValue(dateTime, TimeValue.HOUR);
        String minutes = convertToTimeValue(dateTime, TimeValue.MINUTE);
        String seconds = convertToTimeValue(dateTime, TimeValue.SECOND);
        return day + "." + month + " " + hours + ":" + minutes + ":" + seconds;
    }

    private static String convertToTimeValue(LocalDateTime dateTime, TimeValue value) {
        String result;
        switch (value) {
            case MONTH -> result = dateTime.getMonthValue() < 10 ? "0" + dateTime.getMonthValue() : String.valueOf(dateTime.getMonthValue());
            case DAY -> result = dateTime.getDayOfMonth() < 10 ? "0" + dateTime.getDayOfMonth() : String.valueOf(dateTime.getDayOfMonth());
            case HOUR -> result = dateTime.getHour() < 10 ? "0" + dateTime.getHour() : String.valueOf(dateTime.getHour());
            case MINUTE -> result = dateTime.getMinute() < 10 ? "0" + dateTime.getMinute() : String.valueOf(dateTime.getMinute());
            case SECOND -> result = dateTime.getSecond() < 10 ? "0" + dateTime.getSecond() : String.valueOf(dateTime.getSecond());
            default -> result = dateTime.getYear() < 10 ? "0" + dateTime.getYear() : String.valueOf(dateTime.getYear());
        }
        return result;
    }
}
