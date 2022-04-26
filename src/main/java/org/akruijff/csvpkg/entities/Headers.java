package org.akruijff.csvpkg.entities;

import org.akruijff.csvpkg.exceptions.*;

import java.util.*;

public class Headers {
    private final String[] headers;

    public Headers(String... headers) {
        this.headers = Arrays.copyOf(headers, headers.length);
    }

    public int length() {
        return headers.length;
    }

    public String get(int column) {
        return headers[column];
    }

    public int indexOf(String column) {
        for (int i = 0, n = headers.length; i < n; ++i)
            if (headers[i].equals(column))
                return i;
        throw new ColumnNotFound(column);
    }

    @Override
    public String toString() {
        return "" + headers.length;
    }
}
