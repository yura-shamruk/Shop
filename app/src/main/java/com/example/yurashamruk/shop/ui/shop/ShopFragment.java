package com.example.yurashamruk.shop.ui.shop;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yurashamruk.shop.R;
import com.example.yurashamruk.shop.adapter.CategoriesViewPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopFragment extends Fragment {

    private ShopViewModel mViewModel;

    @BindView(R.id.categoriesPageViewer)
    ViewPager categoriesPageViewer;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

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
        mViewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        mViewModel.getCategories().observe(this, this::updateViews);
    }

    private void updateViews(List<String> categories) {
        categoriesPageViewerAdapter = new CategoriesViewPagerAdapter(getFragmentManager(), categories);
        categoriesPageViewer.setAdapter(categoriesPageViewerAdapter);
        tabLayout.setupWithViewPager(categoriesPageViewer);
    }

}
