package co.monterosa.showstores.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.shopify.buy3.GraphClient;
import com.shopify.buy3.HttpCachePolicy;

import java.util.List;

import co.monterosa.showstores.model.ProductDetails;
import co.monterosa.showstores.model.ShopCollection;
import co.monterosa.showstores.model.ShopDetails;
import io.reactivex.Single;

public class ShopRepository {

    private static final String TAG = "ShopRepository";
    private static final String GRAPH_CLIENT_ERROR_MESSAGE = "graphClient == null. You have to call init() method first";

    private static ShopRepository instance;

    @Nullable
    private List<ShopCollection> shopCollections;

    @Nullable
    private ShopDetails shopDetails;

    @Nullable
    private GraphClient graphClient;


    public static synchronized ShopRepository getInstance() {
        if (instance == null) {
            instance = new ShopRepository();
        }
        return instance;
    }

    /////////////// Api methods start ->


    public Single<ProductDetails> getProductDetails(@NonNull String productId) {
        if (graphClient == null) {
            return Single.error(new RuntimeException(GRAPH_CLIENT_ERROR_MESSAGE));
        }
        return new ProductDetailsRequest().request(graphClient, productId);
    }


    public Single<List<ShopCollection>> getCollections() {
        if (graphClient == null) {
            return Single.error(new RuntimeException(GRAPH_CLIENT_ERROR_MESSAGE));
        }
        return new CollectionsRequest().request(graphClient)
                .doOnSuccess(collectionsDetails -> {
                    getInstance().setShopDetails(collectionsDetails.getShopDetails());
                    getInstance().setCollections(collectionsDetails.getCollections());
                })
                .map(CollectionsRequest.CollectionsDetails::getCollections);

    }


    /////////////// <- Api methods end

    /**
     * Needs to be called before ShopRepository usage
     */
    public void init(@NonNull Context applicationContext, @NonNull String shopDomain,
                     @NonNull String apiKey) {

        graphClient = GraphClient.builder(applicationContext)
                .shopDomain(shopDomain)
                .accessToken(apiKey)
                .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST)
                .build();

    }

    @Nullable
    public ShopCollection getCollection(@Nullable String collectionId) {
        if (collectionId == null || shopCollections == null) {
            return null;
        }
        for (ShopCollection shopCollection : shopCollections) {
            if (shopCollection.getId().equals(collectionId)) {
                return shopCollection;
            }
        }
        return null;
    }

    @Nullable
    public ShopDetails getShopDetails() {
        return shopDetails;
    }

    public void setShopDetails(@Nullable ShopDetails shopDetails) {
        this.shopDetails = shopDetails;
    }

    public void setCollections(List<ShopCollection> shopCollections) {
        this.shopCollections = shopCollections;
    }


    public void init(GraphClient graphClient) {
        this.graphClient = graphClient;
    }

}
