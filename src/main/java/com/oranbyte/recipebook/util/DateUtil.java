package com.oranbyte.recipebook.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String dateTimeFormat(LocalDateTime localTime) {
        if (localTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a", Locale.ENGLISH);
        return localTime.format(formatter).toLowerCase();
    }
    public static String dateTimeFormat(Date date) {
        if (date == null) {
            return "";
        }
        LocalDateTime localTime = date.toInstant()
                                      .atZone(ZoneId.systemDefault())
                                      .toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a", Locale.ENGLISH);
        return localTime.format(formatter).toLowerCase();
    }
    
    public static String timeAgo(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);
        long seconds = duration.getSeconds();

        if (seconds < 60) return seconds + " seconds ago";
        long minutes = seconds / 60;
        if (minutes < 60) return minutes + " minutes ago";
        long hours = minutes / 60;
        if (hours < 24) return hours + " hours ago";
        long days = hours / 24;
        if (days < 30) return days + " days ago";
        long months = days / 30;
        if (months < 12) return months + " months ago";
        long years = months / 12;
        return years + " years ago";
    }

}
