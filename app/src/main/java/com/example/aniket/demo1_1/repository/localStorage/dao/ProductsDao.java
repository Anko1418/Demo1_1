package com.example.aniket.demo1_1.repository.localStorage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.example.aniket.demo1_1.repository.localStorage.entities.Products;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

@Dao
public abstract class ProductsDao {

    @Insert
    public abstract void insert(Products products);

    @Insert
    public abstract void insertAll(ArrayList<Products> products);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(Products products);

    @Delete
    public abstract void delete(Products products);

    @Query("SELECT * FROM product_table")
    public abstract Maybe<List<Products>> getAllProducts();

    @Query("SELECT * FROM product_table WHERE subCateogryName = :categoryNames")
    public abstract Maybe<List<Products>> getAllProducts(String categoryNames);

    @Query("SELECT * FROM product_table WHERE ranks LIKE :rank")
    public abstract Maybe<List<Products>> getRankBasedProducts(String rank);

}
