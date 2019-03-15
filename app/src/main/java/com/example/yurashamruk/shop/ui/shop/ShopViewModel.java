package com.example.yurashamruk.shop.ui.shop;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.yurashamruk.shop.api.ShopRepository;
import com.example.yurashamruk.shop.model.ShopCollection;
import com.example.yurashamruk.shop.util.DataWrapper;

import java.util.List;

public class ShopViewModel extends ViewModel {

    private MutableLiveData <DataWrapper<List<ShopCollection>>> collections;
    private MutableLiveData <DataWrapper<String>> name;

    public LiveData<DataWrapper<List<ShopCollection>>> getCollections() {
        if (collections == null) {
            collections = new MutableLiveData<>();
            loadCollections();
        }
        return collections;
    }


    public LiveData<DataWrapper<String>> getShopName() {
        if (name == null) {
            name = new MutableLiveData<>();
            loadShopName();
        }
        return name;
    }

    private void loadCollections() {
        ShopRepository.getInstance().getCollections(new ShopRepository.Callback<List<ShopCollection>>() {
            @Override
            public void onResponse(List<ShopCollection> response) {
                DataWrapper<List<ShopCollection>> responseData = new DataWrapper<>(response, null);
                collections.postValue(responseData);
            }

            @Override
            public void onError(String error) {
                DataWrapper<List<ShopCollection>> responseData = new DataWrapper<>(null, error);
                collections.postValue(responseData);
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
