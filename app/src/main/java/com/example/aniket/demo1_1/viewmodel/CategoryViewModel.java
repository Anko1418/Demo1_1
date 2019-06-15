package com.example.aniket.demo1_1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.aniket.demo1_1.Constants;
import com.example.aniket.demo1_1.models.CategoryList;
import com.example.aniket.demo1_1.models.Rankings;
import com.example.aniket.demo1_1.repository.Repository;
import com.example.aniket.demo1_1.repository.localStorage.entities.Category;
import com.example.aniket.demo1_1.repository.localStorage.entities.Products;
import com.example.aniket.demo1_1.repository.localStorage.entities.Variants;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends AndroidViewModel {

    private Repository repository = Repository.getInstance(getApplication());
    private MutableLiveData<ArrayList<Category>> mainCategory = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Category>> subCategory = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Products>> allProducts = new MutableLiveData<>();

    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }

    private void fetchDataFromServer() {


        repository.getProductBaseCategory().enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(@NonNull Call<CategoryList> call, @NonNull Response<CategoryList> response) {

                if (response.isSuccessful()) {

                    CategoryList list = response.body();
                    if (list != null) {

                        parseData(list);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

            }
        });
    }

    private void parseData(final CategoryList list) {

        setCategories(list);
        setProducts(list);
    }

    public MutableLiveData<ArrayList<Category>> getMainCategory() {

        if (mainCategory.getValue() == null || mainCategory.getValue().size() == 0) {

            repository.getTypeBasedCategory(Constants.MAIN_CATEGORY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(categories -> {

                        if (categories != null && categories.size() > 0) {

                            mainCategory.setValue(new ArrayList<>(categories));
                        } else {

                            fetchDataFromServer();
                        }
                    })
                    .subscribe();
        }
        return mainCategory;
    }

    public MutableLiveData<ArrayList<Category>> getSubCategories(ArrayList<String> categoryNames) {

        if (mainCategory.getValue() == null || mainCategory.getValue().size() == 0) {

            repository.getTypeBasedCategory(Constants.SUB_CATEGORY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(categories -> {

                        for (int i=0; i<categories.size(); i++) {

                            if (!categoryNames.contains(categories.get(i).getName())) {

                                categories.remove(i);
                                i++;
                            }
                        }
                        return categories;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(categories -> {

                        if (categories != null && categories.size() > 0) {

                            mainCategory.setValue(new ArrayList<>(categories));
                        } else {

                            fetchDataFromServer();
                        }
                    })
                    .subscribe();
        }
        return mainCategory;
    }

    private void setCategories(CategoryList list) {

        ArrayList<Category> mainCategories = new ArrayList<>();
        ArrayList<Category> subCategories = new ArrayList<>();
        ArrayList<Category> allCategories = new ArrayList<>();
        Completable
                .fromAction(() -> {

                    for (Category category : list.getCategories()) {

                        if (category.getChild_categories().size() > 0) {

                            category.setCategoryType(Constants.MAIN_CATEGORY);
                            ArrayList<String> subCategoryNames = new ArrayList<>();
                            for (Integer integer : category.getChild_categories()) {

                                subCategoryNames.add(list.getCategories().get(list.getCategories()
                                        .indexOf(new Category(integer))).getName());
                            }
                            category.setSubCategoryNames(subCategoryNames);
                            mainCategories.add(category);
                        } else {

                            category.setCategoryType(Constants.SUB_CATEGORY);
                            subCategories.add(category);
                        }
                        allCategories.add(category);
                    }
                    repository.insertAllCategories(allCategories);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {

                    CategoryViewModel.this.mainCategory.setValue(mainCategories);
                    CategoryViewModel.this.subCategory.setValue(subCategories);
                })
                .subscribe();
    }

    private void setProducts(CategoryList list) {

        ArrayList<Products> allProducts = new ArrayList<>();
        Completable
                .fromAction(() -> {

                    for (Category category : list.getCategories()) {

                        for (Products product : category.getProducts()) {

                            product.setSubCateogryName(category.getName());
                            for (Variants variants : product.getVariants()) {

                                variants.setProduct_id(product.getId());
                            }
                            product.getTax().setProduct_id(product.getId());
                            allProducts.add(setRanking(product, list.getRankings()));
                        }
                    }
                    repository.insertAllProducts(allProducts);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {

                    Log.e("Aniket", "" + allProducts.size());
                })
                .subscribe();
    }

    public MutableLiveData<ArrayList<Products>> getAllProducts() {

        if (allProducts.getValue() == null || allProducts.getValue().size() == 0) {

            repository.getAllProducts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(products -> {

                        if (products != null && products.size() > 0) {

                            allProducts.setValue(new ArrayList<>(products));
                        }
                    })
                    .subscribe();
        }
        return allProducts;
    }

    private Products setRanking(Products product, ArrayList<Rankings> rankings) {

        ArrayList<String> ranks = new ArrayList<>();
        for (Rankings rank : rankings) {

            if (rank.getProducts().contains(product)) {

                ranks.add(rank.getRanking());
            }
        }
        product.setRanks(ranks);
        return product;
    }

}
