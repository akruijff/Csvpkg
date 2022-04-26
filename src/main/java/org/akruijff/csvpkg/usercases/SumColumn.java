package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;

import java.util.*;

public class SumColumn extends AbstractCommand {
    @Override
    protected boolean isInsufficient(String[] args) {
        return args.length < 2;
    }

    @Override
    protected void doHelp(String[] args) {
        System.err.println("""
                sum <modification column> <selection column> [<selection column> [...]]
                                
                Sums the numerical value in the modification column for all unique combinations of the selection
                columns.
                """);
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        String[] arr = subset(args, 2);
        return sumColumn(sheet, args[1], arr);
    }

    public static Sheet sumColumn(Sheet sheet, String modificationColumn, String... selectionColumns) {
        return new Summer(sheet, modificationColumn, selectionColumns).sumRows();
    }

    private static final class Summer {
        private final Sheet sheet;
        private final String modificationColumn;
        private final String[] selectionColumns;

        private Summer(Sheet sheet, String modificationColumn, String... selectionColumns) {
            this.sheet = sheet;
            this.modificationColumn = modificationColumn;
            this.selectionColumns = selectionColumns;
        }

        public Sheet sumRows() {
            List<Row> list = rowsWithoutNumbers();
            list.addAll(mappedRows());
            return new Sheet(sheet.headers(), list.toArray(Row[]::new));
        }

        private List<Row> rowsWithoutNumbers() {
            int index = sheet.headers().indexOf(modificationColumn);
            List<Row> list = new ArrayList<>();
            sheet.rows().forEach(row -> {
                try {
                    toDouble(row.column(index));
                } catch (Throwable e) {
                    list.add(row);
                }
            });
            return list;
        }

        private List<Row> mappedRows() {
            Map<Key, Row> firstRows = firstRows();
            Map<Key, Double> sums = sums();
            return mapRows(firstRows, sums);
        }

        private Map<Key, Row> firstRows() {
            Map<Key, Row> firstRows = new HashMap<>();
            sheet.rows().forEach(row -> {
                Key key = createKey(sheet, row, selectionColumns);
                if (!firstRows.containsKey(key))
                    firstRows.put(key, row);
            });
            return firstRows;
        }

        private Map<Key, Double> sums() {
            int index = sheet.headers().indexOf(modificationColumn);
            Map<Key, Double> map = new HashMap<>();
            sheet.rows().forEach(row -> {
                try {
                    Key key = createKey(sheet, row, selectionColumns);
                    map.put(key, !map.containsKey(key)
                            ? toDouble(row.column(index))
                            : toDouble(row.column(index)) + map.get(key));
                } catch (Throwable e) {
                    // There is no need to catch throwable since they are created in y()
                }
            });
            return map;
        }

        private List<Row> mapRows(Map<Key, Row> firstRows, Map<Key, Double> sums) {
            int index = sheet.headers().indexOf(modificationColumn);
            List<Row> mapped = firstRows.entrySet().stream().map(e -> {
                Key k = e.getKey();
                Row r = e.getValue();
                Object[] cells = r.cells();
                cells[index] = sums.get(k);
                return new Row(r.lineNumber(), cells);
            }).toList();
            return mapped;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Summer) obj;
            return Objects.equals(this.sheet, that.sheet) &&
                    Objects.equals(this.modificationColumn, that.modificationColumn) &&
                    Objects.equals(this.selectionColumns, that.selectionColumns);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sheet, modificationColumn, selectionColumns);
        }

        @Override
        public String toString() {
            return "Summer[" +
                    "sheet=" + sheet + ", " +
                    "modificationColumn=" + modificationColumn + ", " +
                    "selectionColumns=" + selectionColumns + ']';
        }
    }

    private static Key createKey(Sheet sheet, Row row, String... selectionColumns) {
        Object[] arr = Arrays.stream(selectionColumns)
                .map(c -> sheet.headers().indexOf(c))
                .map(row::column)
                .toArray();
        return new Key(arr);
    }

    private static Double toDouble(Object t) {
        if (t == null)
            throw new UnsupportedOperationException();
        if (t instanceof Double)
            return (Double) t;
        else if (t instanceof Integer)
            return ((Integer) t).doubleValue();
        throw new UnsupportedOperationException();
    }
}
