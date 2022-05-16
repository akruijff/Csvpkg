package org.akruijff.csvpkg.entities;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Column implements Comparable<Column> {
    private final String header;
    private final Object[] cells;

    public Column(String header, Object... cells) {
        this.header = header;
        this.cells = Arrays.copyOf(cells, cells.length);
    }

    public Column(String header, int n, IntFunction<? extends Object> func) {
        this.header = header;
        cells = new Object[n];
        Arrays.setAll(cells, func);
    }

    public String header() {
        return header;
    }

    public Object row(int index) {
        return cells[index];
    }

    public Stream<Object> cells() {
        return Arrays.stream(cells);
    }

    public int length() {
        return cells.length;
    }

    @Override
    public int compareTo(Column other) {
        int diff = this.header.compareTo(other.header);
        if (diff == 0)
            diff = Arrays.compare(this.cells, other.cells, Comparator.comparing(Object::toString));
        return diff;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && equals((Column) obj);
    }

    private boolean equals(Column column) {
        return Objects.equals(header, column.header) && Arrays.equals(cells, column.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(header);
        result = 31 * result + Arrays.hashCode(cells);
        return result;
    }

    @Override
    public String toString() {
        return "" + cells.length + " " + Arrays.toString(cells);
    }
}
