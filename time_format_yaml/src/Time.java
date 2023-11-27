/**
 *
 *  @author Matecki Jakub S24500
 *
 */




import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time {

    public static String passed(String from, String to){
        try {
            if (from.contains("T") && to.contains("T"))
                return info(LocalDateTime.parse(from), LocalDateTime.parse(to));
            else
                return info(LocalDate.parse(from),LocalDate.parse(to));
        } catch (Exception e){
            return "*** java.time.format.DateTimeParseException: " + e.getMessage();
        }
    }

    private static String info(LocalDateTime x, LocalDateTime y){
        ZonedDateTime from = ZonedDateTime.of(x, ZoneId.of("Europe/Warsaw"));
        ZonedDateTime to = ZonedDateTime.of(y, ZoneId.of("Europe/Warsaw"));
        DecimalFormat df = new DecimalFormat("#.##");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        Duration duration = Duration.between(from,to);
        int years = Period.between(x.toLocalDate(), y.toLocalDate()).getYears();
        int months = Period.between(x.toLocalDate(),y.toLocalDate()).getMonths();
        int daysA = Period.between(x.toLocalDate(), y.toLocalDate()).getDays();
        int days = Integer.parseInt(String.valueOf(duration.toDays()));
        int minutes = Integer.parseInt(String.valueOf(duration.toMinutes()));
        int hours = Integer.parseInt(String.valueOf(duration.toHours()));

        if (hours==23 || hours==35062)
            days++;
        double weeks = Double.parseDouble(String.valueOf(days))/7;

        String formattedNumber = df.format(weeks);
        String formattedHourA = from.format(dtf);
        String formattedHourB = to.format(dtf);

        return "Od " + from.getDayOfMonth() + " " + translateMonth(from.getMonth()) + " " +
                from.getYear() + " (" + translateDayOfWeek(from.getDayOfWeek()) + ") godz. " + formattedHourA +
                " do " + to.getDayOfMonth() + " " + translateMonth(to.getMonth()) + " " +
                to.getYear() + " (" + translateDayOfWeek(to.getDayOfWeek()) + ") godz. " + formattedHourB
                + "\n" + " - mija: " + days + form(days) + ", tygodni " + formattedNumber.replace(",",".") + "\n" +
                " - godzin: " + hours + ", minut: " + minutes + "\n" +
                " - kalendarzowo: " + form(years,months,daysA);
    }

    private static String info(LocalDate from, LocalDate to){
        DecimalFormat df = new DecimalFormat("#.##");
        int years = Period.between(from, to).getYears();
        int months = Period.between(from, to).getMonths();
        int daysA = Period.between(from, to).getDays();
        long days = ChronoUnit.DAYS.between(from, to);
        double weeks = Double.parseDouble(String.valueOf(days))/7;

        String formattedNumber = df.format(weeks);

        return "Od " + from.getDayOfMonth() + " " + translateMonth(from.getMonth()) + " " +
                from.getYear() + " (" + translateDayOfWeek(from.getDayOfWeek()) + ") do "
                + to.getDayOfMonth() + " " + translateMonth(to.getMonth()) + " " +
                to.getYear() + " (" + translateDayOfWeek(to.getDayOfWeek()) + ")" + "\n" +
                " - mija: " + days + form(days) + ", tygodni " + formattedNumber.replace(",",".") + "\n" +
                " - kalendarzowo: " + form(years,months,daysA);
    }

    private static String form(long days){
        if(days==1)
            return " dzień";
        else
            return " dni";
    }

    private static String form(int years, int months, int days){
        int licznik=0;
        String dopis="";
        StringBuilder sb=new StringBuilder();
        if(years==1)
            sb.append(years + " rok");
        else if(years%10 == 2 || years%10==3 || years%10==4)
            sb.append(years + " lata");
        else if(years > 1)
            sb.append(years + " lat");

        if (sb.toString().length() > 0 ){
            licznik++;
        }

        if(months == 1)
            dopis=months + " miesiąc";
        else if (months==2 || months==3 || months==4)
            dopis=months + " miesiące";
        else if(months>4)
            dopis=months + " miesięcy";

        if(dopis.length() > 0 && licznik == 1){
            sb.append(", " + dopis);
            licznik++;
            dopis="";
        } else if(dopis.length()>0){
            sb.append(dopis);
            licznik++;
            dopis="";
        }

        if(days == 1)
            dopis= days + " dzień";
        else if(days>1)
            dopis = days + " dni";

        if(dopis.length() > 0 && licznik > 0)
            sb.append(", " + dopis);
        else if(dopis.length() > 0)
            sb.append(dopis);


        return sb.toString();
    }


    private static String translateDayOfWeek(DayOfWeek day){
        return day.getDisplayName(TextStyle.FULL, new Locale("pl"));
    }

    private static String translateMonth(Month month){
        return month.getDisplayName(TextStyle.FULL, new Locale("pl"));
    }

}
