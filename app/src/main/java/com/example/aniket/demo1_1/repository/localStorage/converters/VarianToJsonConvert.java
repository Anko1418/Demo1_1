package com.example.aniket.demo1_1.repository.localStorage.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.aniket.demo1_1.repository.localStorage.entities.Variants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VarianToJsonConvert {

    @TypeConverter
    public static ArrayList<Variants> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Variants>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(ArrayList<Variants> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
