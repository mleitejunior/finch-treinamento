package com.mleitejunior.rpatreinamento.crawler.pages.mercadolivre;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MLMainPage {

    String searchInputField = "//header/div[1]/form[1]/input[1]";
    String innerInputFieldSearchButton = "//header/div[1]/form[1]/button[1]/div[1]";
    String cookiesConfirmButton = "//span[@class='andes-tooltip-button-close']";
    String informCepCloseButton = "//button[@id='cookieDisclaimerButton']";
    String searchItemsContainers = "//li[@class='ui-search-layout__item']";
    String nextPageButton = "//span[@class='andes-pagination__arrow-title'][contains(text(),'Próxima')]";

    public String getSearchInputField() {
        return searchInputField;
    }

    public String getInnerInputFieldSearchButton() {
        return innerInputFieldSearchButton;
    }

    public String getCookiesConfirmButton() {
        return cookiesConfirmButton;
    }

    public String getInformCepCloseButton() {
        return informCepCloseButton;
    }

    public String getSearchItemsContainers() {
        return searchItemsContainers;
    }

    public String getNextPageButton() {
        return nextPageButton;
    }
}
