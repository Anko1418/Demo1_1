package com.example.aniket.demo1_1.repository.localStorage.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "product_variants",
        foreignKeys = @ForeignKey(entity = Products.class,
                parentColumns = "id",
                childColumns = "product_id",
                onDelete = ForeignKey.CASCADE))
public class Variants {

    private String color;

    private int size;

    private int price;

    @PrimaryKey
    @ColumnInfo(name = "variant_id")
    private int id;

    private int product_id;

    public Variants(String color, int size, int price, int id, int product_id) {
        this.color = color;
        this.size = size;
        this.price = price;
        this.id = id;
        this.product_id = product_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClassPojo [color = " + color + ", size = " + size + ", price = " + price + ", id = " + id + "]";
    }
}
