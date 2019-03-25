package co.monterosa.showstores.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;

public class ShopProduct {

    @NonNull
    private final String id;

    @NonNull
    private final String title;

    @NonNull
    private String productType;

    @Nullable
    private final String image;

    @Nullable
    private final BigDecimal price;


    public ShopProduct(@NonNull String id, @NonNull String title, @NonNull String productType,
                       @Nullable String image, @Nullable BigDecimal price) {
        this.id = id;
        this.title = title;
        this.productType = productType;
        this.image = image;
        this.price = price;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    @Nullable
    public BigDecimal getPrice() {
        return price;
    }

    @NonNull
    public String getProductType() {
        return productType;
    }
}
