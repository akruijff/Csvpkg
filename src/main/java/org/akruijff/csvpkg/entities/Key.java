package org.akruijff.csvpkg.entities;

import java.util.*;

public class Key {
    private final Object[] arr;

    public Key(Object... arr) {
        this.arr = arr;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && equals((Key) obj);
    }

    private boolean equals(Key other) {
        return Arrays.equals(this.arr, other.arr);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

    @Override
    public String toString() {
        return "#" + arr.length + " = " + Arrays.toString(arr);
    }
}
