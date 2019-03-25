package co.monterosa.showstores.api.map;

import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.Storefront;

import java.io.IOException;

import co.monterosa.showstores.model.ProductDetails;
import co.monterosa.showstores.model.ShopProduct;
import co.monterosa.showstores.parser.ProductDetailsParser;
import io.reactivex.functions.Function;

public class ProductDetailsMap extends BaseProduct implements Function<GraphResponse<Storefront.QueryRoot>, ProductDetails> {

    @Override
    public ProductDetails apply(GraphResponse<Storefront.QueryRoot> queryRootGraphResponse) throws Exception {
        Storefront.QueryRoot data = queryRootGraphResponse.data();
        if (data == null) {
            throw new IOException(DATA_NULL);
        }
        Storefront.Product product = (Storefront.Product) data.getNode();
        return ProductDetailsParser.parse(product);
    }
}
