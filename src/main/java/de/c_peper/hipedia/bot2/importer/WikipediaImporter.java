package de.c_peper.hipedia.bot2.importer;

import de.c_peper.hipedia.bot2.model.Page;
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
    private static final String BASE_URL = "https://de.wikipedia.org/wiki/";
    private final MediaWikiBot wikiBot;

    /**
     * Creates a new importer with connection to (de) wikipedia.
     */
    public WikipediaImporter() {
        wikiBot = new MediaWikiBot(URL);
        LOGGER.info("Wikipedia init");
    }

    /**
     * Gets page content by page name
     *
     * @param pageName
     * @return
     */
    public Page getPageContent(String pageName) {
        Article article = getPage(pageName);
        return Page.builder()
            .text(article.getText())
            .sourceURL(BASE_URL + pageName)
            .name(pageName)
            .build();
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

//    public List<String> getCategory(String name) {
//        wikiBot.getArticle("Kategorie:Gemeinde im Landkreis Hildesheim");
//        return Collections.EMPTY_LIST;
//    }
}
