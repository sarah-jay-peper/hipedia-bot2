package de.c_peper.hipedia.bot2;

import de.c_peper.hipedia.bot2.importer.KreistagsPersonenImporter;
import de.c_peper.hipedia.bot2.model.KreistagsPerson;

import java.util.List;

/**
 * Created by trublu on 21/11/15.
 */
public class KreistagsPersonenStarter {

    public static void main(String[] args) {
        List<KreistagsPerson> kreistagsPersonen = KreistagsPersonenImporter.getKreistagsPersonen();
    }
}
