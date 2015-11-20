package de.c_peper.hipedia.bot2;

import de.c_peper.hipedia.bot2.Processor.GemeindeProcessor;
import de.c_peper.hipedia.bot2.exporter.HiPediaExporter;
import de.c_peper.hipedia.bot2.importer.WikipediaImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by trublu on 20/11/15.
 */
public class GemeindeStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GemeindeStarter.class);

    public static void main(String[] args) {
        // get content from wikipedia
        WikipediaImporter wikipediaImporter = new WikipediaImporter();
        String pageContent = wikipediaImporter.getPageContent("Nordstemmen");

        // modify content
        pageContent = GemeindeProcessor.process(pageContent);

        // store and mark content
        HiPediaExporter hiPediaExporter = new HiPediaExporter();
        hiPediaExporter.storePage("Nordstemmen", pageContent);

    }
}
