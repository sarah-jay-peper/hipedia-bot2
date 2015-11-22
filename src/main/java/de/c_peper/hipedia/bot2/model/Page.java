package de.c_peper.hipedia.bot2.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by trublu on 21/11/15.
 */
@Data
@Builder
public class Page {

    /**
     * The text content of the page
     */
    private String text;

    /**
     * source of this page (may not be escaped yet)
     */
    private String sourceURL;

    /**
     * the name of this page
     */
    private String name;

    /**
     * Returns the sourceURL escaped for a link
     *
     * @return
     */
    public String getSourceURLAsLink() {
        return getSourceURL().replaceAll(" ", "_");
    }
}
