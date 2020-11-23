package com.mleitejunior.rpatreinamento.crawler.pages.mercadolivre;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MLMainPage {
    public WebDriver driver;

    public MLMainPage(WebDriver driver) {
        this.driver = driver;
    }

    By searchInputField = By.xpath("//header/div[1]/form[1]/input[1]");
    By innerInputFieldSearchButton = By.xpath("//header/div[1]/form[1]/button[1]/div[1]");

    By cookiesConfirmButton = By.xpath("//span[@class='andes-tooltip-button-close']");
    By informeCepCloseButton = By.xpath("//button[@id='cookieDisclaimerButton']");

    By searchItemsContainers = By.xpath("//li[@class='ui-search-layout__item']");


    By nextPageButton = By.xpath("//span[@class='andes-pagination__arrow-title'][contains(text(),'Próxima')]");


    public WebElement getSearchInputField() {
        return driver.findElement(searchInputField);
    }
    public WebElement getInnerInputFieldSearchButton() {
        return driver.findElement(innerInputFieldSearchButton);
    }
    public WebElement getCookiesConfirmButton() {
        return driver.findElement(cookiesConfirmButton);
    }
    public WebElement getInformeCepCloseButton() {
        return driver.findElement(informeCepCloseButton);
    }
    public WebElement getNextPageButton() {
        return driver.findElement(nextPageButton);
    }

    public List<WebElement> getSearchItemsContainers() {
        return driver.findElements(searchItemsContainers);
    }
}
