package co.monterosa.showstores.parser;

import com.shopify.buy3.Storefront;

import java.util.ArrayList;
import java.util.List;

import co.monterosa.showstores.model.ProductDetails;
import co.monterosa.showstores.util.Util;

public class ProductDetailsParser {
    public static ProductDetails parse(Storefront.Product product) {
        List<Storefront.Image> productImages = new ArrayList<>();

        List<String> images = Util.mapItems(product.getImages().getEdges(), imageEdge -> imageEdge.getNode().getSrc());

        for (final Storefront.ImageEdge imageEdge : product.getImages().getEdges()) {
            productImages.add(imageEdge.getNode());
        }
        List<Storefront.ProductVariant> productVariants = new ArrayList<>();
        for (final Storefront.ProductVariantEdge productVariantEdge : product.getVariants().getEdges()) {
            productVariants.add(productVariantEdge.getNode());
        }
        String id = product.getId().toString();
        return new ProductDetails(id, images);
    }
}
