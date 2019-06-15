package com.example.aniket.demo1_1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.aniket.demo1_1.repository.Repository;
import com.example.aniket.demo1_1.repository.localStorage.entities.Products;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductViewModel extends AndroidViewModel {

    private Repository repository = Repository.getInstance(getApplication());

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Products>> getCategoryBasedProducts(String categoryName) {

        MutableLiveData<ArrayList<Products>> products = new MutableLiveData<>();
        if (products.getValue() == null || products.getValue().size() == 0) {

            repository.getCategoryBasedProducts(categoryName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(fetchedProducts -> {

                        if (fetchedProducts != null && fetchedProducts.size() > 0) {

                            products.setValue(new ArrayList<>(fetchedProducts));
                        }
                    })
                    .subscribe();

        }
        return products;
    }

    public MutableLiveData<ArrayList<Products>> getRankBasedProducts(String rank) {

        MutableLiveData<ArrayList<Products>> products = new MutableLiveData<>();
        if (products.getValue() == null || products.getValue().size() == 0) {

            repository.getRankBasedProducts(rank)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(fetchedProducts -> {

                        if (fetchedProducts != null && fetchedProducts.size() > 0) {

                            products.setValue(new ArrayList<>(fetchedProducts));
                        }
                    })
                    .subscribe();

        }
        return products;
    }
}
