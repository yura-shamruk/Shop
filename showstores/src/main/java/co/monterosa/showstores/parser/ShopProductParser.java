package co.monterosa.showstores.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.Nullable;

import java.math.BigDecimal;
import java.util.List;

import co.monterosa.showstores.model.ShopProduct;
import co.monterosa.showstores.util.Util;

public class ShopProductParser {
    private static final String TAG = "ShopProductParser";

    public static ShopProduct parse(Storefront.Product product){
        String id = product.getId().toString();
        String title = product.getTitle();
        String image = getImage(product);
        BigDecimal price = getPrice(product);
        String productType = product.getProductType();
        return new ShopProduct(id, title, productType, image, price);
    }

    @Nullable
    private static BigDecimal getPrice(Storefront.Product product) {
        List<Storefront.ProductVariantEdge> edges = product.getVariants().getEdges();
        if(edges.isEmpty()){
            return null;
        }
        Storefront.ProductVariantEdge firstEdge = edges.get(0);
        return firstEdge.getNode().getPrice();
    }

    @NonNull
    private static String getImage(Storefront.Product product) {
        Storefront.ImageConnection imageConnection = product.getImages();
        if(imageConnection == null){
            Log.d(TAG, "image is null");
            return "";
        }
        List<Storefront.ImageEdge> imagesEdges = imageConnection.getEdges();
        Storefront.ImageEdge firstImageEdge = Util.firstItem(imagesEdges);
        String image;
        if(firstImageEdge == null) {
            return "";
        }
        Storefront.Image firstImageNode = firstImageEdge.getNode();
        image = firstImageNode.getSrc();
        return image;
    }


}
