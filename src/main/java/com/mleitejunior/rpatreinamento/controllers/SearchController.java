package com.mleitejunior.rpatreinamento.controllers;

import com.mleitejunior.rpatreinamento.crawler.MercadoLivreCrawler;
import com.mleitejunior.rpatreinamento.models.MLSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SearchController {

    @GetMapping("/search/webdriver/{searchTerm}")
    public MLSearch getMLSearchByWebdriver(@PathVariable("searchTerm") String term, MercadoLivreCrawler crawler) throws InterruptedException {
        return crawler.searchWithWebdriver(term);
    }

    @GetMapping("/search/htmlunit/{searchTerm}")
    public MLSearch getMLSearchByHtmlUnit(@PathVariable("searchTerm") String term, MercadoLivreCrawler crawler) throws IOException {
        return crawler.searchWithHtmlUnit(term);
    }

}
