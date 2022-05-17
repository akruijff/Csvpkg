package org.akruijff.csvpkg.usercases.util;

import org.akruijff.csvpkg.entities.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class Reducer {
    private final Sheet sheet;
    private final int modificationIndex;
    private final BiFunction<Double, Double, Double> func;

    public Reducer(Sheet sheet, String modificationColumn, BiFunction<Double, Double, Double> func) {
        this.sheet = sheet;
        this.modificationIndex = sheet.headers().indexOf(modificationColumn);
        this.func = func;
    }

    public Sheet reduce(String... columns) {
        checkColumns(columns);
        List<Row> list = rowsWithoutNumbers();
        list.addAll(reduceRowsBy(columns));
        return new Sheet(sheet.headers(), list.toArray(Row[]::new));
    }

    private void checkColumns(String... columns) {
        Arrays.stream(columns).forEach(c -> sheet.headers().indexOf(c));
    }

    private List<Row> rowsWithoutNumbers() {
        return sheet.rows()
                .filter(row -> !convertible(row))
                .collect(Collectors.toList());
    }

    private List<Row> reduceRowsBy(String... columns) {
        return sheet.rows()
                .filter(this::convertible)
                .collect(Collectors.groupingBy(columns(columns)))
                .values().stream().map(this::reduce)
                .collect(Collectors.toList());
    }

    private boolean convertible(Row row) {
        try {
            toDouble(row.column(modificationIndex));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    private Function<Row, Key> columns(String... columns) {
        return row -> createKey(sheet, row, columns);
    }

    private static Key createKey(Sheet sheet, Row row, String... selectionColumns) {
        Object[] arr = Arrays.stream(selectionColumns)
                .map(c -> sheet.headers().indexOf(c))
                .map(row::column)
                .toArray();
        return new Key(arr);
    }

    private Row reduce(List<Row> list) {
        Row firstRow = list.get(0);
        int lineNumber = firstRow.lineNumber();
        Object[] cells = firstRow.cells().toArray();
        cells[modificationIndex] = reduceToDouble(list, row -> toDouble(row.column(modificationIndex)));
        return new Row(lineNumber, cells);
    }

    private Double reduceToDouble(List<Row> list, Function<Row, Double> getCell) {
        return list.stream().reduce(
                (double) 0,
                (d, row) -> func.apply(d, getCell.apply(row)),
                func::apply);
    }

    private static Double toDouble(Object t) {
        return t instanceof Double
                ? (Double) t
                : ((Number) t).doubleValue();
    }
}
