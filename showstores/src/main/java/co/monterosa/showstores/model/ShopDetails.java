package co.monterosa.showstores.model;

import android.support.annotation.NonNull;

import co.monterosa.showstores.parser.CurrencyCodeConverter;

public class ShopDetails {


    @NonNull
    private String currency;

    public ShopDetails(@NonNull String currency) {
        this.currency = currency;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    @NonNull
    public String getCurrencySymbol(){
        return CurrencyCodeConverter.getSymbol(currency);
    }
}
