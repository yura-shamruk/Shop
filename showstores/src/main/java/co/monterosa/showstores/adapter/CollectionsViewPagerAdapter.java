package co.monterosa.showstores.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import co.monterosa.showstores.R;
import co.monterosa.showstores.model.ShopCollection;
import co.monterosa.showstores.ui.shop.CollectionFragment;
import co.monterosa.showstores.ui.shop.NavigationHelper;
import co.monterosa.showstores.util.DispalyUtils;

public class CollectionsViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int HOME_BUTTON_WIDTH = 45;
    private static final int HOME_BUTTON_HEIGHT = 50;

    @NonNull
    private List<ShopCollection> collections;

    @NonNull
    private final Context context;

    @Nullable
    private CollectionFragment.OnProductClickListener onProductClickListener;

    public CollectionsViewPagerAdapter(FragmentManager fm, @NonNull List<ShopCollection> collections,
                                       @NonNull Context context) {
        super(fm);
        this.collections = collections;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        ShopCollection shopCollection = collections.get(position);
        return CollectionFragment.newInstance(shopCollection, onProductClickListener);
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
        View view = LayoutInflater.from(context).inflate(R.layout.home_button_layout, null);

        if (position == 0) {
            setHomeTab(view);
        } else {
            setBasicTab(position, view);
        }
        return view;
    }

    public void setOnProductClickListener(@Nullable CollectionFragment.OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    private void setBasicTab(int position, View v) {
        ShopCollection shopCollection = collections.get(position);
        String name = shopCollection.getName();
        TextView textView = v.findViewById(R.id.textView);
        textView.setText(name);
        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                DispalyUtils.dpToPx(HOME_BUTTON_HEIGHT)));
    }

    private void setHomeTab(View v) {
        v.setLayoutParams(new ViewGroup.LayoutParams(
                DispalyUtils.dpToPx(HOME_BUTTON_WIDTH),
                DispalyUtils.dpToPx(HOME_BUTTON_HEIGHT)));
    }
}
