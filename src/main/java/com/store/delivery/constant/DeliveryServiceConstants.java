package com.store.delivery.constant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DeliveryServiceConstants {

    public static final String MANCHESTER_STORE_NAME = "Manchester White Rose Store";
    public static final String LONDON_STORE_NAME = "London Kevin's Mart";
    public static final String CAMBRIDGE_STORE_NAME = "Cambridge Bloomsbury Store";
    public static final String MAPPING_OPENING_TIME = "08:00:00";
    public static final String MAPPING_CLOSING_TIME = "18:00:00";
    public static final String DATE_TIME_FORMATTER = "HH:mm:ss";
    public static final String UNSCHEDULED_STORE_NAME = "Unscheduled Store Name";

    // Parse strings to LocalTime for proper time comparison
    public static final LocalTime OPENING_TIME = LocalTime.parse(MAPPING_OPENING_TIME);
    public static final LocalTime CLOSING_TIME = LocalTime.parse(MAPPING_CLOSING_TIME);
    public static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
}
