package de.c_peper.hipedia.bot2;

import com.google.common.collect.Lists;
import de.c_peper.hipedia.bot2.Processor.GemeindeProcessor;
import de.c_peper.hipedia.bot2.Processor.SamtgemeindeProcessor;
import de.c_peper.hipedia.bot2.exporter.HiPediaExporter;
import de.c_peper.hipedia.bot2.importer.WikipediaImporter;
import de.c_peper.hipedia.bot2.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by trublu on 20/11/15.
 */
public class SamtgemeindeStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SamtgemeindeStarter.class);

    private static WikipediaImporter wikipediaImporter = new WikipediaImporter();
    private static HiPediaExporter hiPediaExporter = new HiPediaExporter();

    public static void main(String[] args) {
        for (String gemeinde : getSamtgemeinden()) {
            LOGGER.info("Start to handle {}", gemeinde);
            handleGemeinde(gemeinde);
        }
    }

    private static void handleGemeinde(String gemeinde) {
        // get content from wikipedia
        Page page = wikipediaImporter.getPageContent(gemeinde);
        // modify content
        page = SamtgemeindeProcessor.process(page);
        // store and mark content
        hiPediaExporter.storePage(page);
    }

    private static List<String> getSamtgemeinden() {
        return Lists.newArrayList(
            "Samtgemeinde Duingen",
            "Samtgemeinde Freden (Leine)",
            "Samtgemeinde Gronau (Leine)",
            "Samtgemeinde Lamspringe",
            "Samtgemeinde Sibbesse");
    }
}
