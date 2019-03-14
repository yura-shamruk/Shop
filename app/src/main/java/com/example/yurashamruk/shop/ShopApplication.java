package com.example.yurashamruk.shop;

import android.app.Application;
import android.net.wifi.hotspot2.pps.Credential;

import com.example.yurashamruk.shop.api.ShopRepository;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.HttpCachePolicy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ShopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initShop();
    }

    private void initShop() {
        ShopRepository.getInstance().init(getApplicationContext(), "occultatum.myshopify.com",
                "8365677d0b83eb8a85bb2fa6422ef84f");

//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY))
//                .build();

//        GraphClient graphClient = GraphClient.builder(this)
//                .shopDomain("occultatum.myshopify.com")
//                .accessToken("8365677d0b83eb8a85bb2fa6422ef84f")
//                .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST)
//                .build();
//        ShopRepository.getInstance().init(graphClient);
    }

}
