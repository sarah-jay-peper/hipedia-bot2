package de.c_peper.hipedia.bot2.Processor;

import de.c_peper.hipedia.bot2.model.KreistagsAufgabe;
import de.c_peper.hipedia.bot2.model.KreistagsPerson;
import de.c_peper.hipedia.bot2.model.Page;

/**
 * Created by trublu on 22/11/15.
 */
public class KreistagsPersonProcessor {

    /**
     * Processes the {@link KreistagsPerson} to create a page from it's information.
     *
     * @param kreistagsPerson
     * @return
     */
    public static Page process(KreistagsPerson kreistagsPerson) {
        String text = createIntro(kreistagsPerson);
        text += listAufgaben(kreistagsPerson);
        text += "\n\n[[Kategorie:Kreistagsperson]]";
        if (extractKreistagsAufgabeKTA(kreistagsPerson) != null) {
            text += "\n[[Kategorie:Mitglied des Kreistags]]";
        }
        Page page = Page.builder()
            .sourceURL(kreistagsPerson.getURL())
            .name(createName(kreistagsPerson))
            .text(text)
            .build();
        return page;
    }

    /**
     * @param kreistagsPerson
     * @return
     */
    private static String listAufgaben(KreistagsPerson kreistagsPerson) {
        String output = "== Aufgaben im Kreistag ==\n\n";
        output += "{|  class=\"wikitable\"";
        output += "\n!Fraktion/Gremium\n!Funktion\n!Von\n!bis\n";
        for (KreistagsAufgabe kreistagsAufgabe : kreistagsPerson.getAufgaben()) {
            output += "|-\n";
            output += "| [[" + kreistagsAufgabe.getGroup() + "]]\n";
            output += "| " + kreistagsAufgabe.getFunction() + "\n";
            output += "| " + FormatUtil.formatTwoDigitYear(kreistagsAufgabe.getStart()) + "\n";
            output += "| " + FormatUtil.formatTwoDigitYear(kreistagsAufgabe.getEnd()) + "\n";
        }

        output += "|}";
        return output;
    }

    /**
     * Creates the intro line for the article about this {@link KreistagsPerson}
     *
     * @param kreistagsPerson
     * @return
     */
    private static String createIntro(KreistagsPerson kreistagsPerson) {
        KreistagsAufgabe kreistagsAufgabeKTA = extractKreistagsAufgabeKTA(kreistagsPerson);
        String intro = createName(kreistagsPerson) + " ist ";
        String function = "beratendes Mitglied in einem Ausschuss vom";
        if (kreistagsAufgabeKTA != null) {
            function = kreistagsAufgabeKTA.getFunction();
            if (!kreistagsPerson.getFraktion().isEmpty()) {
                function += " für die Fraktion [[" + kreistagsPerson.getFraktion() + "]]";
            }
            function += " im";
        }
        intro += function + " [[Kreistag Hildesheim]].\n\n";
        return intro;
    }

    /**
     * Creates the full name of the {@link KreistagsPerson}
     *
     * @param kreistagsPerson the person.
     * @return the full name.
     */
    private static String createName(KreistagsPerson kreistagsPerson) {
        return kreistagsPerson.getVorname() + " " + kreistagsPerson.getNachname();
    }

    /**
     * Returns the {@link KreistagsAufgabe} for the Kreistag itself (i.e. the {@link KreistagsPerson} is a
     * member of Kreistag (KTA)
     *
     * @param kreistagsPerson
     * @return the KreistagsAufgabe for the Kreistag or null if KreistagsPerson is not a KTA
     */
    private static KreistagsAufgabe extractKreistagsAufgabeKTA(KreistagsPerson kreistagsPerson) {
        for (KreistagsAufgabe kreistagsAufgabe : kreistagsPerson.getAufgaben()) {
            if (kreistagsAufgabe.getGroup().equals("Kreistag")) {
                return kreistagsAufgabe;
            }
        }
        return null;
    }
}
