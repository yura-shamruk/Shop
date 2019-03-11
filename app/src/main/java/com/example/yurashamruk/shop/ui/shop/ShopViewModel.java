package com.example.yurashamruk.shop.ui.shop;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.yurashamruk.shop.api.ShopApi;

import java.util.List;

public class ShopViewModel extends ViewModel {

    private MutableLiveData <List<String>> categories;

    public LiveData<List<String>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<>();
            loadCategories();
        }
        return categories;
    }

    private void loadCategories() {
        categories.setValue(ShopApi.getInstance().getCategories());
    }


}
