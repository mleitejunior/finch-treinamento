package com.mleitejunior.rpatreinamento.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MLItem {
    private String preVisualizationImageLink;
    private String price;
    private String description;

    public MLItem(String preVisualizationImageLink, String price, String description) {
        this.preVisualizationImageLink = preVisualizationImageLink;
        this.price = price;
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreVisualizationImageLink() {
        return preVisualizationImageLink;
    }

    public void setPreVisualizationImageLink(String preVisualizationImageLink) {
        this.preVisualizationImageLink = preVisualizationImageLink;
    }
    @Override
    public String toString()
    {
        return "MLItem [preVisualizationImageLink="
                + preVisualizationImageLink
                + ", price="
                + price
                + ", description="
                + description + "]";
    }
}
