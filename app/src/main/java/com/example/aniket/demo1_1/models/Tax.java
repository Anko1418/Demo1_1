package com.example.aniket.demo1_1.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.aniket.demo1_1.repository.localStorage.entities.Products;

@Entity(tableName = "tax_table",
        foreignKeys = @ForeignKey(entity = Products.class,
                parentColumns = "id",
                childColumns = "product_id",
                onDelete = ForeignKey.CASCADE))
public class Tax {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tax_id")
    private int id;

    @ColumnInfo(name = "tax_name")
    private String name;

    private String value;

    private int product_id;

    public Tax(int id, String name, String value, int product_id) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
