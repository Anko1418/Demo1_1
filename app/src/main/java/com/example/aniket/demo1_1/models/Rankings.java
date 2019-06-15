package com.example.aniket.demo1_1.models;

import com.example.aniket.demo1_1.repository.localStorage.entities.Products;

import java.util.ArrayList;

public class Rankings {

    private String ranking;

    private ArrayList<Products> products;

    public String getRanking ()
    {
        return ranking;
    }

    public void setRanking (String ranking)
    {
        this.ranking = ranking;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ranking = "+ranking+", products = "+products+"]";
    }
}
