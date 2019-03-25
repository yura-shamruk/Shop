package com.example.yurashamruk.shop;

import android.app.Application;

import co.monterosa.showstores.api.ShopRepository;

public class ShopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initShop();
    }

    private void initShop() {
        ShopRepository.getInstance().init(getApplicationContext(), "occultatum.myshopify.com",
                "8365677d0b83eb8a85bb2fa6422ef84f");

    }

}
