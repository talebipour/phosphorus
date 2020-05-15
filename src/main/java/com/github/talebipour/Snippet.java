package com.github.talebipour;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Snippet {
    public static void main(String[] args) {
        Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
