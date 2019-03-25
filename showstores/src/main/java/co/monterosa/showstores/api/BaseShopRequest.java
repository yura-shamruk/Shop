package co.monterosa.showstores.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.Storefront;

import co.monterosa.showstores.model.ProductDetails;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;

public class BaseShopRequest {

    private static final String TAG = "BaseShopRequest";
    protected static final String DATA_NULL = "data == null";

    protected void onEmptyData(SingleEmitter emitter) {
        Log.e(TAG, DATA_NULL);
        emitter.onError(new Throwable(DATA_NULL));
    }

    protected Single<GraphResponse<Storefront.QueryRoot>> execute(GraphClient graphClient, Storefront.QueryRootQuery query) {
        return Single.create(emitter -> graphClient
                .queryGraph(query)
                .enqueue(new GraphCall.Callback<Storefront.QueryRoot>() {

                    @Override
                    public void onResponse(@NonNull GraphResponse<Storefront.QueryRoot> response) {
                        emitter.onSuccess(response);
                    }

                    @Override
                    public void onFailure(@NonNull GraphError error) {
                        emitter.onError(new Throwable(error.getMessage()));
                    }
                }));
    }
}
