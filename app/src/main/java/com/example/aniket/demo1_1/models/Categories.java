package com.example.aniket.demo1_1.models;

import com.example.aniket.demo1_1.repository.localStorage.entities.Products;

import java.util.ArrayList;

public class Categories {

    private String name;

    private String id;

    private ArrayList<Integer> child_categories;

    private ArrayList<Products> products;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public ArrayList<Integer> getChild_categories() {
        return child_categories;
    }

    public void setChild_categories(ArrayList<Integer> child_categories) {
        this.child_categories = child_categories;
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
        return "ClassPojo [name = "+name+", id = "+id+", child_categories = "+child_categories+", products = "+products+"]";
    }
}
