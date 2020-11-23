package com.mleitejunior.rpatreinamento.crawler;

import com.mleitejunior.rpatreinamento.crawler.pages.mercadolivre.MLMainPage;
import com.mleitejunior.rpatreinamento.models.MLItem;
import com.mleitejunior.rpatreinamento.models.MLSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MercadoLivreCrawler {

    private List<MLItem> itemList = new ArrayList<>();

    public MLSearch search(String searchTerm) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "dependencies\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        MLMainPage mercadoLivre = new MLMainPage(driver);

        driver.get("https://lista.mercadolivre.com.br/" + searchTerm + "_DisplayType_LF");
        driver.manage().window().maximize();
        Thread.sleep(5000);

        try {
            mercadoLivre.getCookiesConfirmButton().click();
            mercadoLivre.getInformeCepCloseButton().click();
        } catch (Exception e) {
            //"Busca sem Botao Cookies / Botao CEP"
        }

        boolean searchHasNextPage;

        By itemDescriptionHeader = By.xpath(
                ".//h2[@class='ui-search-item__title']");
        By itemPriceSpan = By.xpath(
                ".//div[@class='ui-search-price ui-search-price--size-medium ui-search-item__group__element']");
        By itemPreVisualizationImage = By.xpath(
                ".//img[@class='ui-search-result-image__element' or @class='ui-search-result-image__element lazy-loadable']");

        do {
            List<WebElement> searchItemsContainers = mercadoLivre.getSearchItemsContainers();

            //searchItemsContainers.forEach(i -> System.out.println(i.getText()));

            searchItemsContainers.forEach(
                    i -> {
                        String itemImageUrl = (i.findElement(itemPreVisualizationImage).getAttribute("src") == null) ?
                                i.findElement(itemPreVisualizationImage).getAttribute("data-src") :
                                i.findElement(itemPreVisualizationImage).getAttribute("src");
                        String itemPrice = i.findElement(itemPriceSpan).getText().replaceAll("\n", "");
                        String itemDescription = i.findElement(itemDescriptionHeader).getText();

                        itemList.add(
                                new MLItem(itemImageUrl,itemPrice,itemDescription)
                        );
                    }
            );

            try {
                mercadoLivre.getNextPageButton().click();
                searchHasNextPage = true;
            } catch (Exception e) {
                searchHasNextPage = false;
            }
        } while (searchHasNextPage);
        
        driver.quit();

        MLSearch mlSearch = new MLSearch(searchTerm, itemList);

        return mlSearch;
    }
}
