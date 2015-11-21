package de.c_peper.hipedia.bot2.Processor;

import de.c_peper.hipedia.bot2.model.Page;
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
     * @param page the input page.
     * @return the processed string.
     */
    public static Page process(Page page) {
        // remove everything after first paragraph
        String input = "{{Wikipedia-Logo}}\n" + page.getText();
        input = input.replaceAll("(?s)==.*", "");
//        // remove all templates
//        input = input.replaceAll("(?s)\\{\\{.*\\}\\}", "");
        // replace Gemeinde template
        input = input.replaceAll("Infobox Gemeinde in Deutschland", "Infobox Gemeinde");
        // remove images
        input = input.replaceAll("(?is)\\[\\[(Datei|file):.*?\\]\\]", "");
        // remove empty lines
        input = input.replaceAll("(?s)\n\n", "");
        input += "\n\n<small>Inhalt abgerufen von [" + page.getSourceURL() + " Wikipedia:" + page.getName() + "] ";
        input += "\n[[Kategorie:Gemeinde]]\n";
        page.setText(input);
        return page;
    }

}
