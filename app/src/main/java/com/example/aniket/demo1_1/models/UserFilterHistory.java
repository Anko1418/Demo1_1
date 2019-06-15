package com.example.aniket.demo1_1.models;

import java.util.ArrayList;

public class UserFilterHistory {

    private static UserFilterHistory userFilterHistory;
    private ArrayList<String> colors = new ArrayList<>();
    private int userMin = 0;
    private int userMax = 0;
    private int size = 0;

    private UserFilterHistory() {}

    public static UserFilterHistory getInstance() {

        if (userFilterHistory == null) {

            userFilterHistory = new UserFilterHistory();
        }
        return userFilterHistory;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public int getUserMin() {
        return userMin;
    }

    public void setUserMin(int userMin) {
        this.userMin = userMin;
    }

    public int getUserMax() {
        return userMax;
    }

    public void setUserMax(int userMax) {
        this.userMax = userMax;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void clearHistory() {

        colors.clear();
        userMin = 0;
        userMax = 0;
        size = 0;
    }
}
