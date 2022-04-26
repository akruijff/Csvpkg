package org.akruijff.csvpkg.entities;

import java.util.*;
import java.util.function.*;

public class Row implements Comparable<Row> {
    private final int lineNumber;
    private final Object[] cells;

    public Row(int lineNumber, Object... cells) {
        this.lineNumber = lineNumber;
        this.cells = Arrays.copyOf(cells, cells.length);
    }

    public Row(int lineNumber, int n, IntFunction<? extends Object> func) {
        this.lineNumber = lineNumber;
        cells = new Object[n];
        Arrays.setAll(cells, func);
    }

    public int lineNumber() {
        return lineNumber;
    }

    public Object column(int index) {
        return cells[index];
    }

    public Object[] cells() {
        return cells;
    }

    public int length() {
        return cells.length;
    }

    @Override
    public int compareTo(Row other) {
        int diff = this.lineNumber - other.lineNumber;
        if (diff == 0)
            diff = Arrays.compare(this.cells, other.cells, Comparator.comparing(Object::toString));
        return diff;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && equals((Row) obj);
    }

    private boolean equals(Row column) {
        return Objects.equals(lineNumber, column.lineNumber) && Arrays.equals(cells, column.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lineNumber);
        result = 31 * result + Arrays.hashCode(cells);
        return result;
    }

    @Override
    public String toString() {
        return "" + cells.length + " " + Arrays.toString(cells);
    }
}
