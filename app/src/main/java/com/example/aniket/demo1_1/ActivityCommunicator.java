package com.example.aniket.demo1_1;

import com.example.aniket.demo1_1.repository.localStorage.entities.Products;

import java.util.ArrayList;

public interface ActivityCommunicator {

    void sortProducts(ArrayList<Products> products);

    void resetProducts();
}
