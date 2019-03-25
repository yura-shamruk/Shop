package co.monterosa.showstores.util;

import android.support.annotation.Nullable;

public class DataWrapper<T>{

    @Nullable
    private T data;

    @Nullable
    private String error; //or A message String, Or whatever

    public DataWrapper(@Nullable T data, @Nullable String error) {
        this.data = data;
        this.error = error;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    @Nullable
    public String getError() {
        return error;
    }

    public void setError(@Nullable String error) {
        this.error = error;
    }
}
