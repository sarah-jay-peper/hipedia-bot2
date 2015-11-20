package de.c_peper.hipedia.bot2.exporter;

import de.c_peper.hipedia.bot2.config.Config;
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
            .withUserAgent(BOT_NAME, "1.0", "Benutzer:Trublu") //
            .withRequestsPerUnit(10, TimeUnit.MINUTES) //
            .build();
        wikiBot = new MediaWikiBot(client);
        wikiBot.login(USERNAME, PASSWD);
    }

    public void storePage(final String pageName, final String content) {
        String output = "<!-- " + BOT_MARKER + " -->";
        output += "<!-- - Inhalt durch BOT geschrieben. Bis zur nächsten Markierung nicht bearbeiten --> \n";
        output += content;
        output += "\n<!-- Ende " + BOT_MARKER + " -->";
        Article article = wikiBot.getArticle(pageName);
        LOGGER.info("fetched article from HiPedia: {}", pageName);
        String articleText = article.getText();
        if (articleText.contains(BOT_MARKER)) {
            articleText = articleText.replaceFirst(getBotMarkerRegex(), output);
        } else {
            articleText = output + articleText;
        }
        article.setText(articleText);
        article.save();
        LOGGER.info("stored article from HiPedia: {}", pageName);
    }

    /**
     * @return
     */
    private String getBotMarkerRegex() {
        return "(?s)<!-- " + BOT_MARKER + ".*" + BOT_MARKER + " -->";
    }
}
