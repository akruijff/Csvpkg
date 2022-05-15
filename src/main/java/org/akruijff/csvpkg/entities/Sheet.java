package org.akruijff.csvpkg.entities;

import java.util.*;
import java.util.stream.*;

public class Sheet {
    private final Headers headers;
    private final LineNumbers lineNumbers;
    private final Object[] cells;

    public Sheet() {
        headers = new Headers();
        lineNumbers = new LineNumbers();
        cells = new Object[0];
    }

    public Sheet(Cell... cells) {
        this.headers = new Headers(Arrays.stream(cells).map(Cell::header).distinct().toArray(String[]::new));
        this.lineNumbers = new LineNumbers(Arrays.stream(cells).mapToInt(Cell::lineNumber).distinct().toArray());
        this.cells = new Object[headers.length() * lineNumbers.length()];
        Arrays.stream(cells).forEach(c -> this.cells[index(c.lineNumber() - 1, c.header())] = c.value());
    }

    public Sheet(Headers headers, Row... rows) {
        this.headers = headers;
        this.lineNumbers = new LineNumbers(Arrays.stream(rows).mapToInt(Row::lineNumber).toArray());

        int n = Arrays.stream(rows)
                .mapToInt(Row::length)
                .max().orElse(headers.length());
        int m = n * rows.length;
        this.cells = new Object[m];

        for (int i = 0; i < m; ++i) {
            int r = i / n;
            int c = i % n;
            this.cells[i] = rows[r].column(c);
        }
    }

    public Sheet(LineNumbers lineNumbers, Column... columns) {
        this.headers = new Headers(Arrays.stream(columns).map(Column::header).toArray(String[]::new));
        this.lineNumbers = lineNumbers;

        int n = columns.length;
        int m = n * Arrays.stream(columns)
                .mapToInt(Column::length)
                .max().orElse(lineNumbers.length());
        this.cells = new Object[m];

        for (int i = 0; i < m; ++i) {
            int c = i % n;
            int r = i / n;
            cells[i] = columns[c].row(r);
        }
    }

    public Headers headers() {
        return headers;
    }

    public LineNumbers lineNumbers() {
        return lineNumbers;
    }

    public Stream<Cell> cells() {
        int n = lineNumbers.length() == 0 ? headers.length() : cells.length / lineNumbers.length();
        int m = lineNumbers.length();
        return IntStream.range(0, n * m)
                .mapToObj(i -> new Cell(headers.get(columnIndex(i)), lineNumbers.get(rowIndex(i)), cells[i]));
    }

    private int columnIndex(int i) {
        int n = headers.length();
        return i % n;
    }

    private int rowIndex(int i) {
        int n = headers.length();
        return i / n;
    }

    public Stream<Column> columns() {
        int n = lineNumbers.length() == 0 ? headers.length() : cells.length / lineNumbers.length();
        int m = lineNumbers.length();
        return IntStream.range(0, n)
                .mapToObj(c -> new Column(headers.get(c), m, r -> cells[index(r, c)]));
    }

    public Stream<Row> rows() {
        int n = lineNumbers.length() == 0 ? headers.length() : cells.length / lineNumbers.length();
        return IntStream.range(0, lineNumbers.length())
                .mapToObj(r -> new Row(lineNumbers.get(r), n, c -> cells[index(r, c)]));
    }

    public Object cellValue(int row, int column) {
        int index = index(row, column);
        return cells[index];
    }

    public Object cellValue(int row, String column) {
        int index = index(row, headers.indexOf(column));
        return cells[index];
    }

    private int index(int row, int column) {
        int n = lineNumbers.length() == 0 ? headers.length() : cells.length / lineNumbers.length();
        return row * n + column;
    }

    private int index(int row, String column) {
        int n = lineNumbers.length() == 0 ? headers.length() : cells.length / lineNumbers.length();
        return row * n + headers.indexOf(column);
    }

    @Override
    public String toString() {
        return "" + headers.length() + " x " + lineNumbers.length();
    }
}
