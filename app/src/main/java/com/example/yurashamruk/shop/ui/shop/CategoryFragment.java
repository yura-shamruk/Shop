package com.example.yurashamruk.shop.ui.shop;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yurashamruk.shop.R;
import com.example.yurashamruk.shop.model.ShopCollection;

import java.util.List;

import butterknife.ButterKnife;

public class CategoryFragment extends Fragment {

    private static final String CATEGORY_ID = "CATEGORY_ID";

    private CategoryViewModel viewModel;

    public static CategoryFragment newInstance(ShopCollection shopCollection) {
        String title = shopCollection.getName();
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_ID, title);
        categoryFragment.setArguments(args);
        return categoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.category_fragment, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
    }

}
