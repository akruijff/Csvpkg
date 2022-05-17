package org.akruijff.csvpkg.usercases.util;

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
    public String toString() {
        return t.toString();
    }
}
