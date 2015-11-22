package de.c_peper.hipedia.bot2.Processor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by trublu on 22/11/15.
 */
public class FormatUtil {

    /**
     * If the year has only two digits, then the millenium is prepenced. It tries to guess the correct one.
     *
     * @param input the input
     * @return the correctly formatted string
     */
    public static final String formatTwoDigitYear(String input) {
        DateFormat df  = new SimpleDateFormat("dd.MM.yy");
        try {
            Date parsedDate = df.parse(input);
            return new SimpleDateFormat("dd.MM.yyyy").format(parsedDate);
        } catch (ParseException e) {
            return input;
        }
    }
}
