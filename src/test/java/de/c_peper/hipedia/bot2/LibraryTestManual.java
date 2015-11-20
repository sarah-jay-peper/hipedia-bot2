package de.c_peper.hipedia.bot2;

import de.c_peper.hipedia.bot2.config.Config;
import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests if the 3rd party library and access to HiPedia are available.
 * <p/>
 * Created by trublu on 20/11/15.
 */
public class LibraryTestManual {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryTestManual.class);

    /**
     * Tests read-only access to (de) wikipedia.
     *
     * @throws Exception
     */
    @Test
    public void testLibraryWithDeWikipedia() throws Exception {
        MediaWikiBot wikiBot = new MediaWikiBot("https://de.wikipedia.org/w/");
        Article article = wikiBot.getArticle("42");
        String articleText = article.getText();
        assertTrue(articleText.contains("{{Artikel Jahr}}"));
    }

    /**
     * Tests read-only access to HiPedia.
     *
     * @throws Exception
     */
    @Test
    public void testLibraryWithHiPedia() throws Exception {
        HttpActionClient client = HttpActionClient.builder() //
            .withUrl("https://www.hipedia.de/w/") //
            .withUserAgent("TruBot", "1.0", "Benutzer:Trublu") //
            .withRequestsPerUnit(10, TimeUnit.MINUTES) //
            .build();
        MediaWikiBot wikiBot = new MediaWikiBot(client);
        Article article = wikiBot.getArticle("Benutzer:Trublu");
        String articleText = article.getText();
        assertTrue(articleText.contains("Jason Peper"));
    }

    /**
     * Tests write access to HiPedia.
     *
     * @throws Exception
     */
    @Test
    public void testLibraryWriteTestWithHiPedia() throws Exception {
        HttpActionClient client = HttpActionClient.builder() //
            .withUrl("https://www.hipedia.de/w/") //
            .withUserAgent("TruBot", "1.0", "Benutzer:Trublu") //
            .withRequestsPerUnit(10, TimeUnit.MINUTES) //
            .build();
        MediaWikiBot wikiBot = new MediaWikiBot(client);
        wikiBot.login(Config.USER, Config.PASSWD);
        LOGGER.info("logged in");
        Article article = wikiBot.getArticle("Benutzer:TruBot/Test");
        LOGGER.info("fetched article");
        article.setText("Test page, please do not edit. Will be overwritten during manual Bot test");
        article.save();
        LOGGER.info("stored edited article");
        Article article2 = wikiBot.getArticle("Benutzer:TruBot/Test");
        LOGGER.info("fetched article after edit");
        assertEquals("Test page, please do not edit. Will be overwritten during manual Bot test", article2.getText());
    }
}
