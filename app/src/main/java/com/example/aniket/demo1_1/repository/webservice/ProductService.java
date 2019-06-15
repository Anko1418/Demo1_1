package com.example.aniket.demo1_1.repository.webservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.aniket.demo1_1.models.CategoryList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("json")
    Call<CategoryList> getCategoriesBasedProducts();
}
