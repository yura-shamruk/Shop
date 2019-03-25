package co.monterosa.showstores.api;

import android.support.annotation.NonNull;

import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;

import java.io.IOException;

import co.monterosa.showstores.api.map.ProductDetailsMap;
import co.monterosa.showstores.model.ProductDetails;
import co.monterosa.showstores.parser.ProductDetailsParser;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

public class ProductDetailsRequest extends BaseShopRequest {

    public Single<ProductDetails> request(@NonNull GraphClient graphClient, @NonNull String productId) {
        Storefront.QueryRootQuery query = Storefront.query(rootQuery -> getRootQuery(productId, rootQuery));
        return execute(graphClient, query)
                .map(new ProductDetailsMap());
    }


    private void getRootQuery(@NonNull String productId, Storefront.QueryRootQuery rootQuery) {
        rootQuery
                .node(new ID(productId), nodeQuery -> nodeQuery
                        .onProduct(this::getProductQuery)
                );
    }

    private void getProductQuery(Storefront.ProductQuery productQuery) {
        productQuery
                .title()
                .description()
                .images(arg -> arg.first(10), this::getImagesQuery)
                .variants(arg -> arg.first(10), this::getVariantsQuery);
    }

    private void getVariantsQuery(Storefront.ProductVariantConnectionQuery
                                          variantConnectionQuery) {
        variantConnectionQuery
                .edges(variantEdgeQuery -> variantEdgeQuery
                        .node(productVariantQuery -> productVariantQuery
                                .price()
                                .title()
                                .available()
                        )
                );
    }

    private void getImagesQuery(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery
                .edges(imageEdgeQuery -> imageEdgeQuery
                        .node(Storefront.ImageQuery::src)
                );
    }
}
