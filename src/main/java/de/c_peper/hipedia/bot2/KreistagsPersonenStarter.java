package de.c_peper.hipedia.bot2;

import de.c_peper.hipedia.bot2.Processor.KreistagsPersonProcessor;
import de.c_peper.hipedia.bot2.exporter.HiPediaExporter;
import de.c_peper.hipedia.bot2.importer.KreistagsPersonenImporter;
import de.c_peper.hipedia.bot2.model.KreistagsPerson;
import de.c_peper.hipedia.bot2.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by trublu on 21/11/15.
 */
public class KreistagsPersonenStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(KreistagsPersonenStarter.class);

    private static HiPediaExporter hiPediaExporter = new HiPediaExporter();

    public static void main(String[] args) {
        List<KreistagsPerson> kreistagsPersonen = KreistagsPersonenImporter.getKreistagsPersonen();
        for (KreistagsPerson kreistagsPerson : kreistagsPersonen) {
            Page page = KreistagsPersonProcessor.process(kreistagsPerson);
            hiPediaExporter.storePage(page);
//            LOGGER.info("Page:\n {}", page);
        }
    }
}
