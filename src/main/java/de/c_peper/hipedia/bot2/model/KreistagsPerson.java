package de.c_peper.hipedia.bot2.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by trublu on 21/11/15.
 */
@Data
@Builder
public class KreistagsPerson {

    private String vorname;

    private String nachname;

    private String ort;

    private String fraktion;

    private String URL;

    private List<KreistagsAufgabe> aufgaben;

    private Boolean kreistagsAbgeordnet;
}
