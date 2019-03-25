package co.monterosa.showstores.ui.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.monterosa.showstores.R;
import co.monterosa.showstores.api.ShopRepository;
import co.monterosa.showstores.model.ProductDetails;
import co.monterosa.showstores.util.RxUtil;
import co.monterosa.showstores.util.Util;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailsFragment extends Fragment {

    public static final String TAG = "ProductDetailsFragment";
    private static final String PRODUCT_ID = "PRODUCT_ID";

    @Nullable
    private String productId;

    private ImageView imageView;

    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static ProductDetailsFragment newInstance(@NonNull String productId) {
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(PRODUCT_ID, productId);
        productDetailsFragment.setArguments(args);
        return productDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.product_details_fragment, container, false);
        bindView(inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString(PRODUCT_ID);
        }
        if(productId != null) {
            compositeDisposable.add(ShopRepository.getInstance().getProductDetails(productId)
                    .compose(RxUtil.io())
                    .subscribe(this::onProductDetailsResponse, this::onCollectionsResponseError));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    private void onCollectionsResponseError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onProductDetailsResponse(ProductDetails productDetailsDataWrapper) {
        setViews(productDetailsDataWrapper);
    }

    private void setViews(@Nullable ProductDetails productDetails) {
        if (productDetails != null) {
            List<String> images = productDetails.getImages();
            String imageURL = Util.firstItem(images);
            Picasso.with(getContext())
                    .load(imageURL)
                    .into(imageView);
        }
    }

    private void bindView(@NonNull View view) {
        imageView = view.findViewById(R.id.imageView);
    }
}
