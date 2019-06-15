package com.example.aniket.demo1_1.models;

import com.example.aniket.demo1_1.repository.localStorage.entities.Category;

import java.util.ArrayList;

public class CategoryList {

    private ArrayList<Rankings> rankings;

    private ArrayList<Category> categories;

    public ArrayList<Rankings> getRankings ()
    {
        return rankings;
    }

    public void setRankings (ArrayList<Rankings> rankings)
    {
        this.rankings = rankings;
    }

    public ArrayList<Category> getCategories ()
    {
        return categories;
    }

    public void setCategories (ArrayList<Category> categories)
    {
        this.categories = categories;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rankings = "+rankings+", categories = "+categories+"]";
    }
}
