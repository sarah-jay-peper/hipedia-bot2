package de.c_peper.hipedia.bot2;

import com.google.common.collect.Lists;
import de.c_peper.hipedia.bot2.Processor.GemeindeProcessor;
import de.c_peper.hipedia.bot2.exporter.HiPediaExporter;
import de.c_peper.hipedia.bot2.importer.WikipediaImporter;
import de.c_peper.hipedia.bot2.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by trublu on 20/11/15.
 */
public class GemeindeStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GemeindeStarter.class);

    private static WikipediaImporter wikipediaImporter = new WikipediaImporter();
    private static HiPediaExporter hiPediaExporter = new HiPediaExporter();

    public static void main(String[] args) {
        for (String gemeinde : getGemeinden()) {
            LOGGER.info("Start to handle {}", gemeinde);
            handleGemeinde(gemeinde);
        }
    }

    private static void handleGemeinde(String gemeinde) {
        // get content from wikipedia
        Page page = wikipediaImporter.getPageContent(gemeinde);
        // modify content
        page = GemeindeProcessor.process(page);
        // store and mark content
        hiPediaExporter.storePage(page);
    }

    private static List<String> getGemeinden() {
        return Lists.newArrayList("Adenstedt",
            "Alfeld (Leine)",
            "Algermissen",
            "Almstedt",
            "Bad Salzdetfurth",
            "Banteln",
            "Betheln",
            "Bockenem",
            "Brüggen",
            "Coppengrave",
            "Despetal",
            "Diekholzen",
            "Duingen",
            "Eberholzen",
            "Eime",
            "Elze",
            "Everode",
            "Freden (Leine)",
            "Giesen",
            "Gronau (Leine)",
            "Harbarnsen",
            "Harsum",
            "Hildesheim",
            "Holle",
            "Hoyershausen",
            "Lamspringe",
            "Landwehr",
            "Marienhagen",
            "Neuhof",
            "Nordstemmen",
            "Rheden",
            "Sarstedt",
            "Schellerten",
            "Sehlem",
            "Sibbesse",
            "Söhlde",
            "Weenzen",
            "Westfeld",
            "Winzenburg",
            "Woltershausen");
    }
}
