package co.monterosa.showstores.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.Storefront;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.monterosa.showstores.model.ShopCollection;
import co.monterosa.showstores.model.ShopDetails;
import co.monterosa.showstores.model.ShopProduct;
import co.monterosa.showstores.parser.ShopProductParser;
import io.reactivex.Single;

public class CollectionsRequest extends BaseShopRequest {


    public Single<CollectionsDetails> request(@NonNull GraphClient graphClient){
        return Single.create(emitter -> {
            Storefront.QueryRootQuery query = Storefront.query(rootQuery -> rootQuery
                    .shop(this::getCollectionsQuery)
            );

            graphClient.queryGraph(query).enqueue(new GraphCall.Callback<Storefront.QueryRoot>() {
                @Override
                public void onResponse(@NonNull GraphResponse<Storefront.QueryRoot> response) {
                    List<ShopCollection> shopCollections = new ArrayList<>();
                    Storefront.QueryRoot data = response.data();
                    if (data == null) {
                        onEmptyData(emitter);
                        return;
                    }
                    Storefront.Shop shop = data.getShop();
                    Storefront.CollectionConnection collectionConnection = shop.getCollections();

                    for (Storefront.CollectionEdge collectionEdge : collectionConnection.getEdges()) {
                        Storefront.Collection node = collectionEdge.getNode();
                        String id = node.getId().toString();
                        String title = node.getTitle();

                        List<ShopProduct> products = new ArrayList<>();
                        for (Storefront.ProductEdge productEdge : node.getProducts().getEdges()) {
                            Storefront.Product productEdgeNode = productEdge.getNode();
                            products.add(ShopProductParser.parse(productEdgeNode));
                        }

                        ShopCollection shopCollection = new ShopCollection(id, title, products);
                        shopCollections.add(shopCollection);
                    }

                    String currencyCode = shop.getPaymentSettings().getCurrencyCode().toString();
                    ShopDetails shopDetails = new ShopDetails(currencyCode);
                    CollectionsDetails  collectionsDetails = new CollectionsDetails(shopDetails, shopCollections);
                    emitter.onSuccess(collectionsDetails);
                }

                @Override
                public void onFailure(@NonNull GraphError error) {
                    emitter.onError(new Throwable(error));
                }
            });
        });


    }

    private void getCollectionsQuery(Storefront.ShopQuery shopQuery) {
        int numberOfFirstCollections = 15;
        shopQuery
                .paymentSettings(Storefront.PaymentSettingsQuery::currencyCode)
                .collections(arg -> arg.first(numberOfFirstCollections), this::getCollectionsEdgesQuery);
    }

    private void getCollectionsEdgesQuery(Storefront.CollectionConnectionQuery collectionConnectionQuery) {
        collectionConnectionQuery
                .edges(collectionEdgeQuery -> collectionEdgeQuery
                        .node(this::getCollectionsDetailsQuery)
                );
    }

    private void getCollectionsDetailsQuery(Storefront.CollectionQuery collectionQuery) {
        int numberOfFirstProducts = 15;
        collectionQuery
                .title()
                .products(arg -> arg.first(numberOfFirstProducts), productConnectionQuery -> productConnectionQuery
                        .edges(productEdgeQuery -> productEdgeQuery
                                .node(this::getProductPreviewQuery)
                        )
                );
    }

    private void getProductPreviewQuery(Storefront.ProductQuery productQuery) {
        productQuery
                .title()
                .productType()
                .description()
                .images(arg -> arg.first(1), this::getImagesQuery)
                .variants(args -> args.first(1), this::getPriceQuery);
    }

    private void getPriceQuery(Storefront.ProductVariantConnectionQuery variantConnection) {
        variantConnection
                .edges(variantEdge -> variantEdge
                        .node(Storefront.ProductVariantQuery::price)
                );
    }

    private void getImagesQuery(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery.edges(imageEdgeQuery ->
                imageEdgeQuery.node(Storefront.ImageQuery::src)
        );
    }

    public class CollectionsDetails{
        @NonNull
        private ShopDetails shopDetails;

        @NonNull
        private List<ShopCollection> collections;

        public CollectionsDetails(@NonNull ShopDetails shopDetails, @NonNull List<ShopCollection> collections) {
            this.shopDetails = shopDetails;
            this.collections = collections;
        }

        @NonNull
        public ShopDetails getShopDetails() {
            return shopDetails;
        }

        @NonNull
        public List<ShopCollection> getCollections() {
            return collections;
        }
    }

}
