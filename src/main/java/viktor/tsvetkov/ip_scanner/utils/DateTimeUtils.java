package viktor.tsvetkov.ip_scanner.utils;

import lombok.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

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

    public static Date localDateTimeToDate(@NonNull LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
    }

    public static String formatDateToString(String format, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date parseStringToDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isToday(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar specifiedDate = Calendar.getInstance();
        specifiedDate.setTime(date);
        return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
                &&  today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                &&  today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
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
