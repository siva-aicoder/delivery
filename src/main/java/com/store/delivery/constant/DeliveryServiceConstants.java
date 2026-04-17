package com.store.delivery.constant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DeliveryServiceConstants {

    public static final String CHENNAI_STORE_NAME = "Chennai Moore Market Central Book Store";
    public static final String DELHI_STORE_NAME = "Delhi Khan Market Book Store";
    public static final String HYDERABAD_STORE_NAME = "Hyderabad Ameerpet Market Book Store";
    public static final String MAPPING_OPENING_TIME = "08:00:00";
    public static final String MAPPING_CLOSING_TIME = "18:00:00";
    public static final String DATE_TIME_FORMATTER = "HH:mm:ss";
    public static final String UNDETERMINED_STORE_NAME = "Undetermined Store Name";

    // Parse strings to LocalTime for proper time comparison
    public static final LocalTime OPENING_TIME = LocalTime.parse(MAPPING_OPENING_TIME);
    public static final LocalTime CLOSING_TIME = LocalTime.parse(MAPPING_CLOSING_TIME);
    public static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
}
