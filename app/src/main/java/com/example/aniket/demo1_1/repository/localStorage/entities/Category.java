package com.example.aniket.demo1_1.repository.localStorage.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@Entity(tableName = "category_table")
public class Category implements Parcelable {

    @PrimaryKey
    private int id;
    private String name;
    private String categoryType;
    private ArrayList<String> subCategoryNames;
    @Ignore
    private ArrayList<Integer> child_categories;
    @Ignore
    private ArrayList<Products> products;

    public Category(int id, String name, String categoryType, ArrayList<String> subCategoryNames, ArrayList<Integer> child_categories, ArrayList<Products> products) {
        this.id = id;
        this.name = name;
        this.categoryType = categoryType;
        this.subCategoryNames = subCategoryNames;
        this.child_categories = child_categories;
        this.products = products;
    }

    public Category(int id) {

        this.id = id;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
        categoryType = in.readString();
        subCategoryNames = in.createStringArrayList();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public ArrayList<String> getSubCategoryNames() {
        return subCategoryNames;
    }

    public void setSubCategoryNames(ArrayList<String> subCategoryNames) {
        this.subCategoryNames = subCategoryNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getChild_categories() {
        return child_categories;
    }

    public void setChild_categories(ArrayList<Integer> child_categories) {
        this.child_categories = child_categories;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object obj) {

        return this.id == ((Category)obj).id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", id = "+id+", child_categories = "+child_categories+", products = "+products+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(categoryType);
        dest.writeStringList(subCategoryNames);
    }
}
