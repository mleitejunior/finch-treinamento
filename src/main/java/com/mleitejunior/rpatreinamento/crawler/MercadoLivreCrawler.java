package com.mleitejunior.rpatreinamento.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.mleitejunior.rpatreinamento.crawler.pages.mercadolivre.MLMainPage;
import com.mleitejunior.rpatreinamento.models.MLItem;
import com.mleitejunior.rpatreinamento.models.MLSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MercadoLivreCrawler {

    private final List<MLItem> itemList = new ArrayList<>();

    private static final String ITEM_DESCRIPTION_HEADER = ".//h2[@class='ui-search-item__title']";
    private static final String ITEM_PRICE_SPAN = ".//div[@class='ui-search-price ui-search-price--size-medium ui-search-item__group__element']";
    private static final String ITEM_PRE_VISUALIZATION_IMAGE = ".//img[@class='ui-search-result-image__element' or @class='ui-search-result-image__element lazy-loadable']";

    private static final String ITEM_PRICE_SYMBOL = ".//span[@class='price-tag-symbol']";
    private static final String ITEM_PRICE_FRACTION = ".//span[@class='price-tag-fraction']";
    private static final String ITEM_PRICE_CENTS = ".//span[@class='price-tag-cents']";

    private static final String NEXT_PAGE_ANCHOR = "//a[@title='Próxima']";

    private static final String ITEM_LIST_CONTAINER = "//li[@class='ui-search-layout__item']";


    private static final String BASE_URL = "https://lista.mercadolivre.com.br/";

    public MLSearch searchWithWebdriver(String searchTerm) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "dependencies\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        MLMainPage mercadoLivre = new MLMainPage();

        driver.get(BASE_URL + searchTerm + "_DisplayType_LF");
        driver.manage().window().maximize();
        Thread.sleep(5000);

        try {
            driver.findElement(By.xpath(mercadoLivre.getCookiesConfirmButton())).click();
            driver.findElement(By.xpath(mercadoLivre.getInformCepCloseButton())).click();
        } catch (Exception e) {
            //"Busca sem Botao Cookies / Botao CEP"
        }

        boolean searchHasNextPage;

        do {
            List<WebElement> searchItemsContainers = driver.findElements(By.xpath(mercadoLivre.getSearchItemsContainers()));

            searchItemsContainers.forEach(
                    i -> {
                        String itemImageUrl = (i.findElement(By.xpath(ITEM_PRE_VISUALIZATION_IMAGE)).getAttribute("src") == null) ?
                                i.findElement(By.xpath(ITEM_PRE_VISUALIZATION_IMAGE)).getAttribute("data-src") :
                                i.findElement(By.xpath(ITEM_PRE_VISUALIZATION_IMAGE)).getAttribute("src");
                        String itemPrice = i.findElement(By.xpath(ITEM_PRICE_SPAN)).getText().replace("\n", "");
                        String itemDescription = i.findElement(By.xpath(ITEM_DESCRIPTION_HEADER)).getText();

                        itemList.add(
                                new MLItem(itemImageUrl,itemPrice,itemDescription)
                        );
                    }
            );

            try {
                driver.findElement(By.xpath(mercadoLivre.getNextPageButton())).click();
                searchHasNextPage = true;
            } catch (Exception e) {
                searchHasNextPage = false;
            }
        } while (searchHasNextPage);
        
        driver.quit();

        return new MLSearch(searchTerm, itemList);

    }

    public MLSearch searchWithHtmlUnit(String searchTerm) throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);

        HtmlPage page = webClient.getPage( BASE_URL + searchTerm + "_DisplayType_LF");

        HtmlAnchor nextPageAnchor;

        do {
            nextPageAnchor = page.getFirstByXPath(NEXT_PAGE_ANCHOR);
            List<HtmlElement> items = page.getByXPath(ITEM_LIST_CONTAINER);

            items.forEach(i -> {
                HtmlHeading2 itemDescriptionHeader = i.getFirstByXPath(ITEM_DESCRIPTION_HEADER);

                HtmlSpan itemPriceSymbolSpan = i.getFirstByXPath(ITEM_PRICE_SYMBOL);
                HtmlSpan itemPriceFractionSpan = i.getFirstByXPath(ITEM_PRICE_FRACTION);
                HtmlSpan itemPriceCentsSpan = i.getFirstByXPath(ITEM_PRICE_CENTS);

                HtmlImage itemPreVisualizationImage = i.getFirstByXPath(ITEM_PRE_VISUALIZATION_IMAGE);

                String description = itemDescriptionHeader.asText();
                String price =
                        itemPriceSymbolSpan.asText() +
                                itemPriceFractionSpan.asText() + "," +
                                ((itemPriceCentsSpan == null) ? "00" : itemPriceCentsSpan.asText());
                String preVisualizationImageUrl = (itemPreVisualizationImage.getAttribute("src") == null) ?
                        itemPreVisualizationImage.getAttribute("data-src") :
                        itemPreVisualizationImage.getAttribute("src");

                itemList.add(new MLItem(preVisualizationImageUrl, price, description));
            });

            if (nextPageAnchor != null) {
                page = webClient.getPage(nextPageAnchor.getAttribute("href"));
            }
        } while (nextPageAnchor != null);

        webClient.close();

        return new MLSearch(searchTerm, itemList);
    }
}
