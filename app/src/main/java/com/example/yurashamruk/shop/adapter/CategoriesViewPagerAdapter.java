package com.example.yurashamruk.shop.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.yurashamruk.shop.ui.shop.CategoryFragment;

import java.util.List;

public class CategoriesViewPagerAdapter extends FragmentStatePagerAdapter {


    private List<String> categories;

    public CategoriesViewPagerAdapter(FragmentManager fm, List<String> categories) {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(categories.get(position));
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position);
    }
}
