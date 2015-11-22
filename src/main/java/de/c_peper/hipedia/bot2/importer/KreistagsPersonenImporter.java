package de.c_peper.hipedia.bot2.importer;

import com.google.common.collect.Lists;
import de.c_peper.hipedia.bot2.model.KreistagsAufgabe;
import de.c_peper.hipedia.bot2.model.KreistagsPerson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Importer of content from kreistags.info.
 */
public class KreistagsPersonenImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(KreistagsPersonenImporter.class);

    public static final String BASE_URL = "http://213.165.68.250:8080";

    public static final String SOURCE_URL = BASE_URL + "/ratsinfo/hildesheimlk/Person.html";

    /**
     * Extracts a list of {@link KreistagsPerson}en from the ratsinfo website of Kreistag Hildesheim.
     *
     * @return the extracted list of KreistagsPersonen.
     */
    public static List<KreistagsPerson> getKreistagsPersonen() {
        try {
            Document doc = Jsoup.connect(SOURCE_URL).get();
            Elements tableElements = extractTableFromMainColumn(doc);
            List<KreistagsPerson> kreistagsPersonen = extractKreistagsPerson(tableElements);
            return kreistagsPersonen;
        } catch (IOException e) {
            LOGGER.error("Exception", e);
        }
        return null;
    }

    /**
     * extracts the URLs from a group of elements from a html table.
     *
     * @param tableElements the list of html table rows.
     * @return the URLs from each table row
     */
    private static List<KreistagsPerson> extractKreistagsPerson(Elements tableElements) {
        List<KreistagsPerson> kreistagsPersonen = Lists.newArrayList();
        for (Element tableElement : tableElements) {
            String href = tableElement.child(0).child(0).attr("href");
            if (href.contains("persid")) { // check if there is "persid" present (personen id)
                KreistagsPerson kreistagsPerson = KreistagsPerson.builder()
                    .URL(href)
//                    .aufgaben(extractKreistagsAufgaben(href))
                    .nachname(tableElement.child(1).child(0).text())
                    .vorname(tableElement.child(2).child(0).text())
                    .ort(tableElement.child(3).child(0).text())
                    .fraktion(tableElement.child(4).children().isEmpty() ? "" : tableElement.child(4).child(0).text())
                    .build();
                if (kreistagsPerson.getNachname().equals("Peper")) {
                    kreistagsPerson.setAufgaben(extractKreistagsAufgaben(href)); // TODO move this into builder when development is finished
                    kreistagsPersonen.add(kreistagsPerson);
                }
            }
        }
        return kreistagsPersonen;
    }

    /**
     * extract a {@link KreistagsPerson} from a given URL.
     *
     * @param personURL the URL of the KreistagsPerson data.
     * @return the extracted KreistagsPerson.
     */
    private static List<KreistagsAufgabe> extractKreistagsAufgaben(String personURL) {
        try {
            Document doc = Jsoup.connect(BASE_URL + personURL).get();
            Elements elements = extractPersonTableFromMainColumn(doc);
            elements.remove(0); // remove headings
            List<KreistagsAufgabe> kreistagsAufgaben = Lists.newArrayList();
            for (Element element : elements) {
                KreistagsAufgabe kreistagsAufgabe = KreistagsAufgabe.builder()
                    .group(element.child(0).text())
                    .function(element.child(1).text())
                    .start(element.child(2).text())
                    .end(element.child(3).text())
                    .build();
                kreistagsAufgaben.add(kreistagsAufgabe);
            }
            return kreistagsAufgaben;
        } catch (IOException e) {
            LOGGER.error("Exception!", e);
        }
        return null;
    }

    /**
     * Extracts the element representing the main table in the main column (only at this position if called without a "persid").
     *
     * @param doc the document representing the website from kreistags.info
     * @return the main html table
     */
    private static Elements extractTableFromMainColumn(Document doc) {
        Element contentDivMainColumn = doc.body().getElementById("contentDivMainColumn");
        return contentDivMainColumn.child(1).child(1).child(0).children();
    }

    /**
     * Extracts the element representing the person table in the main column (only present if called with a "persid").
     *
     * @param doc the document representing the website from kreistags.info (called for a specific person)
     * @return the person html table
     */
    private static Elements extractPersonTableFromMainColumn(Document doc) {
        Element contentDivMainColumn = doc.body().getElementById("contentDivMainColumn");
        return contentDivMainColumn.child(1).child(2).child(0).child(0).child(0).child(1).child(0).children();
    }
}
