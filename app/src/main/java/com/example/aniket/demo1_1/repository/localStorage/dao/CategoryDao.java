package com.example.aniket.demo1_1.repository.localStorage.dao;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.aniket.demo1_1.repository.localStorage.entities.Category;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Insert
    void insertAll(ArrayList<Category> category);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM category_table")
    Maybe<List<Category>> getAllCategories();

    @Query("SELECT * FROM category_table WHERE categoryType = :categoryType")
    Maybe<List<Category>> getTypeBasedCategory(String categoryType);
}
