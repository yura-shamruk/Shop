package com.example.yurashamruk.shop.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yurashamruk.shop.R;
import com.example.yurashamruk.shop.model.ShopCollection;
import com.example.yurashamruk.shop.ui.shop.CategoryFragment;
import com.example.yurashamruk.shop.util.DispalyUtils;

import java.util.List;

public class CategoriesViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int HOME_BUTTON_WIDTH = 45;
    public static final int HOME_BUTTON_HEIGHT = 50;


    private List<ShopCollection> collections;
    private final Context context;

    public CategoriesViewPagerAdapter(FragmentManager fm, List<ShopCollection> collections, Context context) {
        super(fm);
        this.collections = collections;
        this.context = context;
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


    public View getTabView(int position) {
        ShopCollection shopCollection = collections.get(position);
        String name = shopCollection.getName();

        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.home_button_layout, null);
        TextView tv = v.findViewById(R.id.textView);


        tv.setText(name);
        if (position == 0) {
            v.setLayoutParams(new ViewGroup.LayoutParams(
                    DispalyUtils.dpToPx(HOME_BUTTON_WIDTH),
                    DispalyUtils.dpToPx(HOME_BUTTON_HEIGHT)));
            ImageView imageView = v.findViewById(R.id.imageView);
            imageView.setVisibility(View.VISIBLE);
            tv.setText("");
        } else {
            v.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    DispalyUtils.dpToPx(HOME_BUTTON_HEIGHT)));
        }
        return v;
    }
}
