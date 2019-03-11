package com.example.yurashamruk.shop.api;

import java.util.ArrayList;
import java.util.List;

public class ShopRepository {
    private static ShopRepository instance;

    public static synchronized ShopRepository getInstance(){
        if(instance == null){
            instance = new ShopRepository();
        }
        return instance;
    }

    public void getCategories(Callback callback){
        List<String> categories = ShopApi.getInstance().getCategories();
        callback.onResponse(categories);
    }

    public interface Callback<T>{
        void onResponse(T response);

        void onError();
    }
}
