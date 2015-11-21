package de.c_peper.hipedia.bot2.exporter;

import de.c_peper.hipedia.bot2.config.Config;
import de.c_peper.hipedia.bot2.model.Page;
import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by trublu on 20/11/15.
 */
public class HiPediaExporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HiPediaExporter.class);

    public static final String URL = "https://www.hipedia.de/w/";
    public static final String BOT_NAME = "TruBot";
    public static final String BOT_MARKER = "BOT:TruBot";
    public static final String PASSWD = Config.PASSWD;
    public static final String USERNAME = Config.USER;
    private final MediaWikiBot wikiBot;

    /**
     *
     */
    public HiPediaExporter() {
        HttpActionClient client = HttpActionClient.builder() //
            .withUrl(URL) //
            .withUserAgent(BOT_NAME, Config.VERSION, "Benutzer:Trublu") //
            .withRequestsPerUnit(10, TimeUnit.MINUTES) //
            .build();
        wikiBot = new MediaWikiBot(client);
        wikiBot.login(USERNAME, PASSWD);
        LOGGER.info("HiPedia Login");
    }

    /**
     * Stores new content in the given page and surrounds it with markers. Replaces content already present with those exact markers.
     *
     * @param page the internal model page to be stored.
     */
    public void storePage(final Page page) {
        String output = "<!-- " + BOT_MARKER + " -->";
        output += "<!-- - Inhalt durch BOT geschrieben. Bis zur nächsten Markierung nicht bearbeiten --> \n";
        output += page.getText();
        output += "\n<!-- Ende " + BOT_MARKER + " -->";
        LOGGER.info("start to fetch article from HiPedia: {}", page.getName());
        Article article = wikiBot.getArticle(page.getName());
        LOGGER.info("fetched article from HiPedia: {}", page.getName());
        String articleText = article.getText();
        if (articleText.contains(BOT_MARKER)) {
            articleText = articleText.replaceFirst(getBotMarkerRegex(), output);
        } else {
            articleText = output + articleText;
        }
        article.setText(articleText);
        article.save();
        LOGGER.info("stored article from HiPedia: {}", page.getName());
    }

    /**
     * @return
     */
    private String getBotMarkerRegex() {
        return "(?s)<!-- " + BOT_MARKER + ".*" + BOT_MARKER + " -->";
    }
}
