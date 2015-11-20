package de.c_peper.hipedia.bot2.Processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by trublu on 20/11/15.
 */
public class GemeindeProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GemeindeProcessor.class);

    /**
     * Process input string.
     *
     * @param input the input string.
     * @return the processed string.
     */
    public static String process(String input) {
        // remove everything after first paragraph
        input = input.replaceAll("(?s)==.*", "");
        // remove all templates
        input = input.replaceAll("(?s)\\{\\{.*\\}\\}", "");
        // remove images
        input = input.replaceAll("(?is)\\[\\[(Datei|file):.*?\\]\\]", "");
        // remove empty lines
        input = input.replaceAll("(?s)\n\n", "");
        input += "\n[[Kategorie:Gemeinde]]\n";
        return input;
    }

}
