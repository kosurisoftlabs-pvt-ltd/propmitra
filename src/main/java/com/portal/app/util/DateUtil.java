package com.portal.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final String pattern = "MM/dd/yyyy HH:mm:ss.SSSXXX";
    private static final String uiPattern = "MM/dd/yyyy HH:mm:ss";
    private static final String dbPattern = "yyyy-MM-dd HH:mm:ss";

    public static String dateToUTCStr(Date date) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    public static String dateToStr(Date date) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static String dateToUIStr(Date date) {
        DateFormat df = new SimpleDateFormat(uiPattern);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    public static Instant strToInstant(String dateStr) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dbPattern);
        TemporalAccessor temporalAccessor = formatter.parse(dateStr);
        LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return Instant.from(zonedDateTime);
    }
}
