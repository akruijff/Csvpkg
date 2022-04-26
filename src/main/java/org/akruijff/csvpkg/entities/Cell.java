package org.akruijff.csvpkg.entities;

import java.util.*;

import static java.lang.String.*;

public class Cell implements Comparable<Cell> {
    private final String header;
    private final int lineNumber;
    private final Object value;

    public Cell(String header, int lineNumber, Object value) {
        this.header = header;
        this.lineNumber = lineNumber;
        this.value = value;
    }

    public int lineNumber() {
        return lineNumber;
    }

    public String header() {
        return header;
    }

    public Object value() {
        return value;
    }

    @Override
    public int compareTo(Cell other) {
        return value.toString().compareTo(other.value.toString());
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && equals((Cell) obj);
    }

    private boolean equals(Cell cell) {
        return lineNumber == cell.lineNumber && Objects.equals(header, cell.header) && Objects.equals(value, cell.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, lineNumber, value);
    }

    @Override
    public String toString() {
        return valueOf(value);
    }
}
