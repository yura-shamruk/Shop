package com.example.yurashamruk.shop.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ShopCollection {

    @NonNull
    private String id;

    @NonNull
    private String name;

    public ShopCollection(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
