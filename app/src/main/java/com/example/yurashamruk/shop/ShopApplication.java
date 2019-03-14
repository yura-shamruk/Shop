package com.example.yurashamruk.shop;

import android.app.Application;

import com.example.yurashamruk.shop.api.ShopRepository;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.HttpCachePolicy;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ShopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initShop();
    }

    private void initShop() {
//        ShopRepository.getInstance().init(getApplicationContext(), "occultatum",
//                "9a809b0f4950c5181e86274b0502a6c7");

//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY))
//                .build();

        GraphClient graphClient = GraphClient.builder(this)
                .shopDomain("occultatum.myshopify.com")
                .accessToken("9a809b0f4950c5181e86274b0502a6c7")
                .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST)
                .build();
        ShopRepository.getInstance().init(graphClient);
    }

}
