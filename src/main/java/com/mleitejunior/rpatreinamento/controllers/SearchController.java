package com.mleitejunior.rpatreinamento.controllers;

import com.mleitejunior.rpatreinamento.crawler.MercadoLivreCrawler;
import com.mleitejunior.rpatreinamento.models.MLSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @GetMapping("/{searchTerm}")
    public MLSearch getMLSearch(@PathVariable("searchTerm") String term, MercadoLivreCrawler crawler) throws InterruptedException {
        return crawler.search(term);
    }
}
