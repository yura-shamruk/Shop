package co.monterosa.showstores.ui.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shopify.buy3.GraphResponse;

import java.util.List;

import co.monterosa.showstores.R;
import co.monterosa.showstores.adapter.CollectionsViewPagerAdapter;
import co.monterosa.showstores.api.ShopRepository;
import co.monterosa.showstores.model.ShopCollection;
import co.monterosa.showstores.util.RxUtil;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShopFragment extends Fragment {

    private ViewPager categoriesPageViewer;

    private TabLayout tabLayout;

    private ProgressBar progressBar;

    private CollectionsViewPagerAdapter collectionsPageViewerAdapter;

    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_fragment, container, false);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        compositeDisposable.add(ShopRepository.getInstance().getCollections()
                .compose(RxUtil.io())
                .subscribe(this::onCollectionsResponse, this::onCollectionsResponseError));
        shopProgress();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    private void onCollectionsResponseError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void bindView(@NonNull View view) {
        categoriesPageViewer = view.findViewById(R.id.categoriesPageViewer);
        tabLayout = view.findViewById(R.id.tabLayout);
        progressBar = view.findViewById(R.id.progressBar);
        View homeButton = view.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this::onHomeButtonClick);
    }

    private void onCollectionsResponse(List<ShopCollection> listDataWrapper) {
        hideProgress();

        setPageViewer(listDataWrapper);

        customizeTabLayout();
    }

    private void setPageViewer(List<ShopCollection> listDataWrapper) {
        List<ShopCollection> collections = listDataWrapper;
        if(getContext() == null || collections == null){
            return;
        }
        collectionsPageViewerAdapter = new CollectionsViewPagerAdapter(getFragmentManager(), collections, getContext());
        collectionsPageViewerAdapter.setOnProductClickListener(this::onProductClick);
        categoriesPageViewer.setAdapter(collectionsPageViewerAdapter);
        tabLayout.setupWithViewPager(categoriesPageViewer);
    }

    private void onProductClick(@NonNull String productId) {
        if (getFragmentManager() != null) {
            NavigationHelper.openProductDetailsFragment(productId, getFragmentManager(), R.id.shopMainLayout);
        }
    }

    private void customizeTabLayout() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(collectionsPageViewerAdapter.getTabView(i));
            }
        }
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void shopProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void onHomeButtonClick(View view){
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        if (tab != null) {
            tab.select();
        }
    }

}
