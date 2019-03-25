package co.monterosa.showstores.ui.shop;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class NavigationHelper {
    public static void openProductDetailsFragment(@NonNull String productId,
                                                  @NonNull FragmentManager fragmentManager,
                                                  @IdRes int rootLayout) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ProductDetailsFragment childFragment = ProductDetailsFragment.newInstance(productId);
        transaction.replace(rootLayout, childFragment, ProductDetailsFragment.TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
