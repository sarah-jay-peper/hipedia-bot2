package de.c_peper.hipedia.bot2.Processor;

import junit.framework.TestCase;

/**
 * Created by trublu on 22/11/15.
 */
public class FormatUtil_FormatTwoDigitYear_Test extends TestCase {

    /**
     * Tests that a two digit year gets expanded
     *
     * @throws Exception
     */
    public void testTwoDigitYearInput() throws Exception {
        String input = "01.01.15";
        String expected = "01.01.2015";

        assertEquals(expected, FormatUtil.formatTwoDigitYear(input));
    }

    /**
     * Tests that a four digit year remains unchanged.
     *
     * @throws Exception
     */
    public void testFourDigitYearInput() throws Exception {
        String input = "01.01.1915";
        String expected = "01.01.1915";

        assertEquals(expected, FormatUtil.formatTwoDigitYear(input));
    }

    /**
     * Tests that a two digit year gets expanded
     *
     * @throws Exception
     */
    public void testTwoDigitYearInputInTwentiethCentury() throws Exception {
        String input = "01.01.95";
        String expected = "01.01.1995";

        assertEquals(expected, FormatUtil.formatTwoDigitYear(input));
    }
}