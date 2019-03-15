package com.example.yurashamruk.shop.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.yurashamruk.shop.model.ShopCollection;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShopRepository {

    private static final String TAG = "ShopRepository";
    private static final String GRAPH_CLIENT_ERROR_MESSAGE = "graphClient == null. You have to call init() method first";

    private static ShopRepository instance;

    @Nullable
    private GraphClient graphClient;

    public static synchronized ShopRepository getInstance() {
        if (instance == null) {
            instance = new ShopRepository();
        }
        return instance;
    }

    /////////////// Api methods start ->

    public void getCategories(@NonNull Callback<List<String>> callback) {
        List<String> categories = ShopApiMock.getInstance().getCategories();
        callback.onResponse(categories);
    }

    public void getShopName(@NonNull Callback<String> callback) {
        if (graphClient == null) {
            Log.e(TAG, GRAPH_CLIENT_ERROR_MESSAGE);
            return;
        }
        Storefront.QueryRootQuery query = Storefront.query(rootQuery ->
                rootQuery.shop(Storefront.ShopQuery::name)
        );

        QueryGraphCall call = graphClient.queryGraph(query);

        call.enqueue(new GraphCall.Callback<Storefront.QueryRoot>() {

            @Override
            public void onResponse(@NonNull GraphResponse<Storefront.QueryRoot> response) {
                String name = response.data().getShop().getName();
                Log.e(TAG, "Shop name: " + name);
                callback.onResponse(name);
            }

            @Override
            public void onFailure(@NonNull GraphError error) {
                Log.e(TAG, "Failed to execute query", error);
                callback.onError(error.getMessage());
            }
        });
    }

    @Nullable
    public GraphClient getGraphClient() {
        return graphClient;
    }

    public void getProductDetails(Callback callback) {

    }

    public void getCollections(Callback<List<ShopCollection>> callback) {
        if (graphClient == null) {
            Log.e(TAG, GRAPH_CLIENT_ERROR_MESSAGE);
            return;
        }
        int numberOfFirstCollections = 15;
        int numberOfFirstProducts = 15;

        Storefront.ProductEdgeQueryDefinition productDetailsQuery = productEdgeQuery -> productEdgeQuery
                .node(productQuery -> productQuery
                        .title()
                        .productType()
                        .description()
                );

        Storefront.CollectionEdgeQueryDefinition collectionEdgeQueryDefinition = collectionEdgeQuery -> collectionEdgeQuery
                .node(collectionQuery -> collectionQuery
                        .title()
                        .products(arg -> arg.first(numberOfFirstProducts), productConnectionQuery -> {
                            productConnectionQuery.edges(productDetailsQuery);
                        })
                );

        Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition = collectionConnectionQuery ->
                collectionConnectionQuery.edges(collectionEdgeQueryDefinition);

        Storefront.ShopQueryDefinition collectionsQuery = shopQuery ->
                shopQuery.collections(arg -> arg.first(numberOfFirstCollections), collectionConnectionQueryDefinition);

        Storefront.QueryRootQuery query = Storefront.query(rootQuery -> rootQuery.shop(collectionsQuery));


        graphClient.queryGraph(query).enqueue(new GraphCall.Callback<Storefront.QueryRoot>() {
            @Override
            public void onResponse(@NonNull GraphResponse<Storefront.QueryRoot> response) {

                List<ShopCollection> shopCollections = new ArrayList<>();
                Storefront.Shop shop = response.data().getShop();
                Storefront.CollectionConnection collectionConnection = shop.getCollections();

                for (Storefront.CollectionEdge collectionEdge : collectionConnection.getEdges()) {
                    Storefront.Collection node = collectionEdge.getNode();
                    String id = node.getId().toString();
                    String title = node.getTitle();
                    ShopCollection shopCollection = new ShopCollection(id, title);
                    shopCollections.add(shopCollection);

                    List<Storefront.Product> products = new ArrayList<>();
                    for (Storefront.ProductEdge productEdge : node.getProducts().getEdges()) {
                        products.add(productEdge.getNode());
                    }
                    callback.onResponse(shopCollections);
                }
            }

            @Override
            public void onFailure(@NonNull GraphError error) {
                    callback.onError(error.getMessage());
            }
        });
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

    public void init(GraphClient graphClient) {
        this.graphClient = graphClient;
    }

    public interface Callback<T> {
        void onResponse(T response);

        void onError(String errorMessage);
    }
}
