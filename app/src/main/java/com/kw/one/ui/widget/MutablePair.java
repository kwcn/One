package com.kw.one.ui.widget;

import androidx.annotation.NonNull;
import androidx.core.util.ObjectsCompat;

/**
 * @author Kang Wei
 * @date 2019/8/20
 */
public class MutablePair<F, S> {
    public F first;
    public S second;

    public MutablePair(@NonNull F first, @NonNull S second) {
        this.first = first;
        this.second = second;
    }

    @NonNull
    public static <A, B> MutablePair<A, B> create(@NonNull A a, @NonNull B b) {
        return new MutablePair<A, B>(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MutablePair)) {
            return false;
        }
        MutablePair<?, ?> p = (MutablePair<?, ?>) o;
        return ObjectsCompat.equals(p.first, first) && ObjectsCompat.equals(p.second, second);
    }

    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    @Override
    public String toString() {
        return "MutablePair{" + String.valueOf(first) + " " + String.valueOf(second) + "}";
    }
}