package com.example.aniket.demo1_1.repository.localStorage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.aniket.demo1_1.models.Tax;
import com.example.aniket.demo1_1.repository.localStorage.converters.ArrayListToStringConverter;
import com.example.aniket.demo1_1.repository.localStorage.converters.VarianToJsonConvert;
import com.example.aniket.demo1_1.repository.localStorage.dao.CategoryDao;
import com.example.aniket.demo1_1.repository.localStorage.dao.ProductsDao;
import com.example.aniket.demo1_1.repository.localStorage.entities.Category;
import com.example.aniket.demo1_1.repository.localStorage.entities.Products;
import com.example.aniket.demo1_1.repository.localStorage.entities.Variants;

@Database(entities = {Category.class, Products.class, Variants.class, Tax.class}, version = 1)
@TypeConverters({ArrayListToStringConverter.class, VarianToJsonConvert.class})
public abstract class DatabaseHander extends RoomDatabase {

    private static DatabaseHander databaseHander;

    public abstract CategoryDao categoryDao();

    public abstract ProductsDao productsDao();

    public static synchronized DatabaseHander getInstance(Context context) {

        if (databaseHander == null) {

            databaseHander = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseHander.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseHander;
    }
}
