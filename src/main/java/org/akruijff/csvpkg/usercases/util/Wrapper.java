package org.akruijff.csvpkg.usercases.util;

import java.util.*;

public final class Wrapper<T> {
    private T t;

    public Wrapper(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public void set(T sheet) {
        this.t = sheet;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && equals((Wrapper<?>) obj);
    }

    private boolean equals(Wrapper<?> that) {
        return Objects.equals(t, that.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t);
    }

    @Override
    public String toString() {
        return t.toString();
    }
}
