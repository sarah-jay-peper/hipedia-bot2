package de.c_peper.hipedia.bot2.model;

import junit.framework.TestCase;

/**
 * Created by trublu on 22/11/15.
 */
public class PageTest extends TestCase {

    /**
     * Tests that getSourceURLAsLink properly encoded spaces.
     *
     * @throws Exception
     */
    public void testGetPageURLAsLinkWithSpaces() throws Exception {
        Page page = Page.builder()
            .sourceURL("Samtgemeinde Freden (Leine)")
            .build();
        String expected = "Samtgemeinde_Freden_(Leine)";
        assertEquals(expected, page.getSourceURLAsLink());
    }
}