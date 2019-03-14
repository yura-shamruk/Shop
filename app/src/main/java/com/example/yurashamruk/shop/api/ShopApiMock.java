package com.example.yurashamruk.shop.api;

import com.shopify.buy3.Storefront;

import java.util.ArrayList;
import java.util.List;

public class ShopApiMock {

    private static ShopApiMock instance;

    public static synchronized ShopApiMock getInstance(){
        if(instance == null){
            instance = new ShopApiMock();
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

    public List<String> productDetails(){
        Storefront.QueryRootQuery query = Storefront.query(rootQuery ->
                rootQuery.shop(Storefront.ShopQuery::name)
        );
        return null;
    }
}
