package co.monterosa.showstores.model;

import android.support.annotation.NonNull;

import java.util.List;

import co.monterosa.showstores.api.BaseResponse;

public class ProductDetails extends BaseResponse {

    @NonNull
    private String id;

    @NonNull
    private List<String> images;

    public ProductDetails(@NonNull String id, @NonNull List<String> images) {
        this.id = id;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    @NonNull
    public List<String> getImages() {
        return images;
    }
}
