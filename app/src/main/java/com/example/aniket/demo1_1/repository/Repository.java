package com.example.aniket.demo1_1.repository;

import android.content.Context;

import com.example.aniket.demo1_1.models.CategoryList;
import com.example.aniket.demo1_1.repository.localStorage.DatabaseHander;
import com.example.aniket.demo1_1.repository.localStorage.entities.Category;
import com.example.aniket.demo1_1.repository.localStorage.entities.Products;
import com.example.aniket.demo1_1.repository.webservice.ProductService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository repository;
    private final Retrofit retrofit;
    private final DatabaseHander databaseHander;
    private final ProductService productService;

    private static final String BASE_URL = "https://stark-spire-93433.herokuapp.com/";

    private Repository(Context context) {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        databaseHander = DatabaseHander.getInstance(context);
    }

    public static Repository getInstance(Context context) {

        if (repository == null) {

            repository = new Repository(context);
        }
        return repository;
    }

    public Call<CategoryList> getProductBaseCategory() {

        return productService.getCategoriesBasedProducts();
    }

    public void insertAllCategories(ArrayList<Category> categories) {

        databaseHander.categoryDao().insertAll(categories);
    }

    public Maybe<List<Category>> getAllCategories() {

        return databaseHander.categoryDao().getAllCategories();
    }

    public Maybe<List<Category>> getTypeBasedCategory(String type) {

        return databaseHander.categoryDao().getTypeBasedCategory(type);
    }

    public void insertAllProducts(ArrayList<Products> products) {

        databaseHander.productsDao().insertAll(products);
    }

    public Maybe<List<Products>> getAllProducts() {

        return databaseHander.productsDao().getAllProducts();
    }

    public Maybe<List<Products>> getCategoryBasedProducts(String categoryNames) {

        return databaseHander.productsDao().getAllProducts(categoryNames);
    }

    public Maybe<List<Products>> getRankBasedProducts(String rank) {

        return databaseHander.productsDao().getRankBasedProducts("%" + rank + "%");
    }
}
