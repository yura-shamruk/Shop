package co.monterosa.showstores.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Util {

    public static <I, T extends Collection<I>> T checkNotEmpty(T reference, @Nullable Object errorMessage) {
        if (reference == null) throw new NullPointerException(String.valueOf(errorMessage));
        if (reference.isEmpty()) throw new IllegalArgumentException(String.valueOf(errorMessage));
        return reference;
    }

    public static String checkNotBlank(String reference, @Nullable Object errorMessage) {
        if (reference == null) throw new NullPointerException(String.valueOf(errorMessage));
        if (reference.isEmpty()) throw new IllegalArgumentException(String.valueOf(errorMessage));
        return reference;
    }

    public static <T> T checkNotNull(final T reference, @Nullable final Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }


    @NonNull
    public static <T, R> List<R> mapItems(@NonNull final Collection<T> source, @NonNull final Function<T, R> transformer) {
        checkNotNull(source, "source == null");
        checkNotNull(transformer, "transformer == null");
        List<R> result = new ArrayList<>();
        for (T item : source) {
            result.add(transformer.apply(item));
        }
        return result;
    }

    @Nullable
    public static <T> T firstItem(@Nullable final List<T> source) {
        return source != null && !source.isEmpty() ? source.get(0) : null;
    }

    @Nullable
    public static <T> List<T> filter(@Nullable final List<T> source, @NonNull final Function<T, Boolean> condition) {
        checkNotNull(source, "source == null");
        checkNotNull(condition, "condition == null");
        List<T> result = new ArrayList<>();
        for (T item : source) {
            if (condition.apply(item)) {
                result.add(item);
            }
        }
        return result;
    }

    @Nullable
    public static <T, R> R firstItem(@Nullable final List<T> source, @NonNull final Function<T, R> transformer) {
        checkNotNull(transformer, "transformer == null");
        return source != null && !source.isEmpty() ? transformer.apply(source.get(0)) : null;
    }

    @NonNull
    public static <T> T minItem(@NonNull final Collection<T> source, @NonNull final T defaultValue,
                                @NonNull final Comparator<T> comparator) {
        checkNotNull(source, "source == null");
        checkNotNull(comparator, "comparator == null");
        if (source.isEmpty()) {
            return defaultValue;
        }

        T minItem = null;
        for (T item : source) {
            if (minItem == null) {
                minItem = item;
            } else {
                if (comparator.compare(minItem, item) >= 0) {
                    minItem = item;
                }
            }
        }

        //noinspection ConstantConditions
        return minItem;
    }
}
