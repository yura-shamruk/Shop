package com.example.yurashamruk.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.monterosa.showstores.ui.shop.ShopFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ShopFragment.newInstance())
                    .commitNow();
        }
    }
}
