package de.c_peper.hipedia.bot2.importer;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by trublu on 20/11/15.
 */
public class WikipediaImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikipediaImporter.class);

    public static final String URL = "https://de.wikipedia.org/w/";
    private final MediaWikiBot wikiBot;

    /**
     * Creates a new importer with connection to (de) wikipedia.
     */
    public WikipediaImporter() {
        wikiBot = new MediaWikiBot(URL);
    }

    /**
     * Gets page content by page name
     *
     * @param pageName
     * @return
     */
    public String getPageContent(String pageName) {
        return getPage(pageName).getText();
    }

    /**
     * Gets page by page name.
     *
     * @param pageName
     * @return the page
     */
    public Article getPage(String pageName) {
        LOGGER.info("fetch article {} from de.wikipedia", pageName);
        return wikiBot.getArticle(pageName);
    }
}
