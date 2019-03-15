package com.example.yurashamruk.shop.ui.shop;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yurashamruk.shop.R;
import com.example.yurashamruk.shop.adapter.CategoriesViewPagerAdapter;
import com.example.yurashamruk.shop.api.ShopRepository;
import com.example.yurashamruk.shop.model.ShopCollection;
import com.example.yurashamruk.shop.util.DataWrapper;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopFragment extends Fragment {

    private ShopViewModel viewModel;

    @BindView(R.id.categoriesPageViewer)
    ViewPager categoriesPageViewer;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindDrawable(R.drawable.ic_home_button)
    Drawable homeButtonIcon;

    private CategoriesViewPagerAdapter categoriesPageViewerAdapter;

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.shop_fragment, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        viewModel.getCollections().observe(this, this::updateViews);

        shopProgress();


        viewModel.getShopName().observe(this, this::onShopNameResponse);


    }

    private void updateViews(DataWrapper<List<ShopCollection>> listDataWrapper) {
        if (listDataWrapper.getError() != null) {
            return;
        }

        List<ShopCollection> collections = listDataWrapper.getData();
        categoriesPageViewerAdapter = new CategoriesViewPagerAdapter(getFragmentManager(), collections,getContext());
        categoriesPageViewer.setAdapter(categoriesPageViewerAdapter);
        tabLayout.setupWithViewPager(categoriesPageViewer);

//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ImageView homeButton = (ImageView) inflater.inflate(R.layout.home_button_layout, null);
        View homeButton = LayoutInflater.from(getContext()).inflate(R.layout.home_button_layout, null);
//        TabLayout.Tab tab = tabLayout.getTabAt(0);
//        tabLayout.addTab(tab, 0, true);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(categoriesPageViewerAdapter.getTabView(i));
        }
    }

    private void onShopNameResponse(DataWrapper<String> shopNameDataWrapper) {
        if (shopNameDataWrapper.getError() != null) {
            Toast.makeText(getContext(), shopNameDataWrapper.getError(), Toast.LENGTH_SHORT).show();
            return;
        }
        String shopName = shopNameDataWrapper.getData();
        Toast.makeText(getContext(), shopName, Toast.LENGTH_SHORT).show();
    }


    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void shopProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

}
