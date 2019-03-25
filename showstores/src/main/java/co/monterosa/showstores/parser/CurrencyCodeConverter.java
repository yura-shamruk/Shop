package co.monterosa.showstores.parser;

import android.support.annotation.Nullable;

public class CurrencyCodeConverter {
    public static String getSymbol(@Nullable String currencyCode){
        if(currencyCode == null){
            return "";
        }
        String symbol = "";
        switch (currencyCode){
            case "GBP":{
                symbol = "£";
                break;
            }
            case "USD":{
                symbol = "$";
            }
        }
        return symbol;
    }
}
