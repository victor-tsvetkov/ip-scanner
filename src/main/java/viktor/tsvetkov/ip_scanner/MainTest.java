package viktor.tsvetkov.ip_scanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String outDate = dateFormat.format(date);
        Date dateFromString = dateFormat.parse(outDate);
        System.out.println(outDate);
        System.out.println(dateFromString);
    }
}
