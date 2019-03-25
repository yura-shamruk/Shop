package co.monterosa.showstores.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class ShopCollection {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private List<ShopProduct> products;

    public ShopCollection(@NonNull String id, @NonNull String name,
                          @NonNull List<ShopProduct> products) {
        this.id = id;
        this.name = name;
        this.products = products;
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

    @NonNull
    public List<ShopProduct> getProducts() {
        return products;
    }
}
