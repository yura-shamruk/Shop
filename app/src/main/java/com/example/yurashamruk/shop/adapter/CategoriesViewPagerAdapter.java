package com.example.yurashamruk.shop.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.yurashamruk.shop.model.ShopCollection;
import com.example.yurashamruk.shop.ui.shop.CategoryFragment;

import java.util.List;

public class CategoriesViewPagerAdapter extends FragmentStatePagerAdapter {


    private List<ShopCollection> collections;

    public CategoriesViewPagerAdapter(FragmentManager fm, List<ShopCollection> collections) {
        super(fm);
        this.collections = collections;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(collections.get(position));
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        ShopCollection shopCollection = collections.get(position);
        return shopCollection.getName();
    }
}
