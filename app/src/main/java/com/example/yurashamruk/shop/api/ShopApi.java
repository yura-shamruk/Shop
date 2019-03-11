package com.example.yurashamruk.shop.api;

import java.util.ArrayList;
import java.util.List;

public class ShopApi {

    private static ShopApi instance;

    public static synchronized ShopApi getInstance(){
        if(instance == null){
            instance = new ShopApi();
        }
        return instance;
    }

    public List<String> getCategories(){
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Cat 1");
        categories.add("Cat 2");
        categories.add("Cat 3");
        categories.add("Cat 4");
        return categories;
    }
}
