package co.monterosa.showstores.ui.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import co.monterosa.showstores.R;
import co.monterosa.showstores.adapter.ProductsViewPagerAdapter;
import co.monterosa.showstores.api.ShopRepository;
import co.monterosa.showstores.model.ShopCollection;
import co.monterosa.showstores.model.ShopDetails;
import co.monterosa.showstores.model.ShopProduct;

public class CollectionFragment extends Fragment {

    private static final String CATEGORY_ID = "COLLECTION_ID";

    private String collectionId;
    private RecyclerView productsRecyclerView;
    private ProductsViewPagerAdapter adapter;

    @Nullable
    private OnProductClickListener onProductClickListener;

    public static CollectionFragment newInstance(@NonNull ShopCollection shopCollection,
                                                 @Nullable OnProductClickListener onProductClickListener) {
        String id = shopCollection.getId();
        CollectionFragment collectionFragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_ID, id);
        collectionFragment.setArguments(args);
        collectionFragment.setOnProductClickListener(onProductClickListener);
        return collectionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            collectionId = getArguments().getString(CATEGORY_ID);
        }
        ShopCollection collection = ShopRepository.getInstance().getCollection(collectionId);
        ShopDetails shopDetails = ShopRepository.getInstance().getShopDetails();
        if (collection != null && shopDetails != null) {
            setViews(collection, shopDetails);
        }
    }

    public void setOnProductClickListener(@Nullable OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    private void setViews(@NonNull ShopCollection collection, ShopDetails shopDetails) {
        if(getContext() == null){
            return;
        }
        int numberOfColumns = 2;
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new ProductsViewPagerAdapter(getContext(), collection.getProducts(),shopDetails.getCurrencySymbol());
        adapter.setClickListener(this::openProductFragment);
        productsRecyclerView.setAdapter(adapter);
    }

    private void openProductFragment(ShopProduct shopProduct) {
        Toast.makeText(getContext(), "item: " + shopProduct.getId(), Toast.LENGTH_SHORT).show();
//        NavigationHelper.openProductDetailsFragment(shopProduct.getId(), getChildFragmentManager(), R.id.shopMainLayout);
        if (onProductClickListener != null) {
            onProductClickListener.onClick(shopProduct.getId());
        }
    }


    private void bindView(@NonNull View view) {
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView);
    }

    public interface OnProductClickListener{
        void onClick(String productId);
    }
}
