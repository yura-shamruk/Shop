package com.example.yurashamruk.shop.ui.shop;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.yurashamruk.shop.api.ShopRepository;
import com.example.yurashamruk.shop.util.DataWrapper;

import java.util.List;

public class ShopViewModel extends ViewModel {

    private MutableLiveData <DataWrapper<List<String>>> categories;
    private MutableLiveData <DataWrapper<String>> name;

    public LiveData<DataWrapper<List<String>>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<>();
            loadCategories();
        }
        return categories;
    }



    public LiveData<DataWrapper<String>> getShopName() {
        if (name == null) {
            name = new MutableLiveData<>();
            loadShopName();
        }
        return name;
    }

    private void loadCategories() {
        ShopRepository.getInstance().getCategories(new ShopRepository.Callback<List<String>>() {
            @Override
            public void onResponse(List<String> response) {
                DataWrapper<List<String>> responseData = new DataWrapper<>(response, null);
                categories.setValue(responseData);
            }

            @Override
            public void onError(String error) {
                DataWrapper<List<String>> responseData = new DataWrapper<>(null, error);
                categories.setValue(responseData);
            }
        });
    }

    private void loadShopName() {
        ShopRepository.getInstance().getShopName(new ShopRepository.Callback<String>() {
            @Override
            public void onResponse(String response) {
                DataWrapper<String> responseData = new DataWrapper<>(response, null);
                name.postValue(responseData);
            }

            @Override
            public void onError(String errorMessage) {
                DataWrapper<String> responseData = new DataWrapper<>(null, errorMessage);
                name.postValue(responseData);
            }
        });
    }
}
