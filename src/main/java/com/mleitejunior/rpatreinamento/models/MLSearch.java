package com.mleitejunior.rpatreinamento.models;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MLSearch {

    private String title;
    private List<MLItem> mLItems;

    public MLSearch(String title, List<MLItem> mLItems) {
        this.title = title;
        this.mLItems = mLItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MLItem> getmLItems() {
        return mLItems;
    }

    public void setmLItems(List<MLItem> mLItems) {
        this.mLItems = mLItems;
    }

    @Override
    public String toString()
    {
        return "MLSearch [title="
                + title
                + ", mLItems="
                + mLItems + "]";
    }
}
