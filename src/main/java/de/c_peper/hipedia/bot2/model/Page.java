package de.c_peper.hipedia.bot2.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by trublu on 21/11/15.
 */
@Data
@Builder
public class Page {

    private String text;

    private String sourceURL;

    private String name;
}
