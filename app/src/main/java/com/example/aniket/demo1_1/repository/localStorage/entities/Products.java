package com.example.aniket.demo1_1.repository.localStorage.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.aniket.demo1_1.models.Tax;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = "product_table")
public class Products implements Parcelable {

    @Embedded
    private Tax tax;
    @PrimaryKey
    private int id;
    @Ignore
    private int order_count;
    private int view_count;
    private int shares;
    private String date_added;
    private String name;
    private String subCateogryName;
    private ArrayList<Variants> variants;
    private ArrayList<String> ranks;

    public Products(Tax tax, int id, int order_count, int view_count, int shares, String date_added, String name, String subCateogryName, ArrayList<Variants> variants, ArrayList<String> ranks) {
        this.tax = tax;
        this.id = id;
        this.order_count = order_count;
        this.view_count = view_count;
        this.shares = shares;
        this.date_added = date_added;
        this.name = name;
        this.subCateogryName = subCateogryName;
        this.variants = variants;
        this.ranks = ranks;
    }

    protected Products(Parcel in) {
        id = in.readInt();
        order_count = in.readInt();
        view_count = in.readInt();
        shares = in.readInt();
        date_added = in.readString();
        name = in.readString();
        subCateogryName = in.readString();
        ranks = in.createStringArrayList();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public ArrayList<String> getRanks() {
        return ranks;
    }

    public void setRanks(ArrayList<String> ranks) {
        this.ranks = ranks;
    }

    public Products(int id) {
        this.id = id;
    }

    public Products() {}

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubCateogryName() {
        return subCateogryName;
    }

    public void setSubCateogryName(String subCateogryName) {
        this.subCateogryName = subCateogryName;
    }

    public ArrayList<Variants> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<Variants> variants) {
        this.variants = variants;
    }

    @Override
    public boolean equals(Object obj) {

        return this.id == ((Products) obj).id;
    }

    //Tax tax, int id, int order_count, int view_count, int shares, String date_added, String name, String subCateogryName, ArrayList<Variants> variants, ArrayList<RankBased> rankBaseds
    public Products copy(Products products) {

        Tax originalTax = products.tax;
        ArrayList<Variants> variants = new ArrayList<>();
        for (Variants variants1: products.getVariants()) {

            variants.add(new Variants(variants1.getColor(), variants1.getSize(), variants1.getPrice(), variants1.getId(), variants1.getProduct_id()));
        }
        Tax tax = new Tax(originalTax.getId(), originalTax.getName(), originalTax.getValue(), originalTax.getProduct_id());
        return new Products(tax, products.id, products.order_count, products.view_count, products.shares, products.date_added, products.name, products.subCateogryName, variants, products.ranks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(order_count);
        dest.writeInt(view_count);
        dest.writeInt(shares);
        dest.writeString(date_added);
        dest.writeString(name);
        dest.writeString(subCateogryName);
        dest.writeStringList(ranks);
    }
}
