package org.akruijff.csvpkg.entities;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SheetTest {
    @Nested
    @DisplayName ("A sheet can be created with")
    public class Creation {
        @Test
        @DisplayName ("the default constructor")
        public void emptySheet() {
            Sheet sheet = new Sheet();
            assertEquals(0, sheet.headers().length());
            assertEquals(0, sheet.lineNumbers().length());
        }

        @Test
        @DisplayName ("a single cell")
        public void singleCell() {
            Sheet sheet = new Sheet(new Cell("A", 1, "a"));
            assertEquals(1, sheet.headers().length());
            assertEquals(1, sheet.lineNumbers().length());
            assertEquals("a", sheet.cellValue(0, 0));
            assertEquals("a", sheet.cellValue(0, "A"));
        }

        @Test
        @DisplayName ("multiple cells")
        public void multipleCell() {
            Sheet sheet = new Sheet(
                    new Cell("A", 1, "a1"),
                    new Cell("A", 2, "a2"),
                    new Cell("B", 1, "b1"),
                    new Cell("B", 2, "b2"));
            assertEquals(2, sheet.headers().length());
            assertEquals(2, sheet.lineNumbers().length());
            assertEquals("a1", sheet.cellValue(0, 0));
            assertEquals("a2", sheet.cellValue(1, 0));
            assertEquals("b1", sheet.cellValue(0, 1));
            assertEquals("b2", sheet.cellValue(1, 1));
            assertEquals("a1", sheet.cellValue(0, "A"));
            assertEquals("a2", sheet.cellValue(1, "A"));
            assertEquals("b1", sheet.cellValue(0, "B"));
            assertEquals("b2", sheet.cellValue(1, "B"));
        }

        @Test
        @DisplayName ("a single header")
        public void singleHeader() {
            Headers headers = new Headers("A");
            Row[] rows = new Row[0];
            Sheet sheet = new Sheet(headers, rows);
            assertEquals(1, sheet.headers().length());
            assertEquals("A", sheet.headers().get(0));
            assertEquals(0, sheet.lineNumbers().length());
        }

        @Test
        @DisplayName ("multiple headers")
        public void multipleHeaders() {
            Headers headers = new Headers("A", "B", "C");
            Row[] rows = new Row[0];
            Sheet sheet = new Sheet(headers, rows);
            assertEquals(3, sheet.headers().length());
            assertEquals("A", sheet.headers().get(0));
            assertEquals("B", sheet.headers().get(1));
            assertEquals("C", sheet.headers().get(2));
            assertEquals("A", sheet.headers().get(0));
            assertEquals("B", sheet.headers().get(1));
            assertEquals("C", sheet.headers().get(2));
        }

        @Test
        @DisplayName ("a single cell in a row")
        public void singleCellInOneRow() {
            Headers headers = new Headers("A");
            Row row1 = new Row(1, "a");
            Sheet sheet = new Sheet(headers, row1);
            assertEquals(1, sheet.headers().length());
            assertEquals(1, sheet.lineNumbers().length());
            assertEquals("a", sheet.cellValue(0, 0));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals("A", sheet.headers().get(0));
        }

        @Test
        @DisplayName ("multiple cells in a row")
        public void multipleCellsInOneRow() {
            Headers headers = new Headers("A", "B", "C");
            Row row1 = new Row(1, "a", "b", "c");
            Sheet sheet = new Sheet(headers, row1);
            assertEquals(3, sheet.headers().length());
            assertEquals(1, sheet.lineNumbers().length());
            assertEquals("a", sheet.cellValue(0, "A"));
            assertEquals("b", sheet.cellValue(0, "B"));
            assertEquals("c", sheet.cellValue(0, "C"));
            assertEquals("a", sheet.cellValue(0, 0));
            assertEquals("b", sheet.cellValue(0, 1));
            assertEquals("c", sheet.cellValue(0, 2));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals("A", sheet.headers().get(0));
        }

        @Test
        @DisplayName ("multiple rows")
        public void multipleRows() {
            Headers headers = new Headers("A");
            Row row1 = new Row(1, "a");
            Row row2 = new Row(2, "b");
            Row row3 = new Row(3, "c");
            Sheet sheet = new Sheet(headers, row1, row2, row3);
            assertEquals(1, sheet.headers().length());
            assertEquals(3, sheet.lineNumbers().length());
            assertEquals("a", sheet.cellValue(0, "A"));
            assertEquals("b", sheet.cellValue(1, "A"));
            assertEquals("c", sheet.cellValue(2, "A"));
            assertEquals("a", sheet.cellValue(0, 0));
            assertEquals("b", sheet.cellValue(1, 0));
            assertEquals("c", sheet.cellValue(2, 0));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals(2, sheet.lineNumbers().get(1));
            assertEquals(3, sheet.lineNumbers().get(2));
            assertEquals("A", sheet.headers().get(0));
        }

        @Test
        @DisplayName ("a single line number")
        public void singleLineNumber() {
            LineNumbers lineNumbers = new LineNumbers(1);
            Column[] columns = new Column[0];
            Sheet sheet = new Sheet(lineNumbers, columns);
            assertEquals(0, sheet.headers().length());
            assertEquals(1, sheet.lineNumbers().length());
            assertEquals(1, lineNumbers.get(0));
        }

        @Test
        @DisplayName ("multiple line number")
        public void multipleLineNumbers() {
            LineNumbers lineNumbers = new LineNumbers(1, 2, 3);
            Column[] columns = new Column[0];
            Sheet sheet = new Sheet(lineNumbers, columns);
            assertEquals(0, sheet.headers().length());
            assertEquals(3, sheet.lineNumbers().length());
            assertEquals(1, lineNumbers.get(0));
            assertEquals(2, lineNumbers.get(1));
            assertEquals(3, lineNumbers.get(2));
        }

        @Test
        @DisplayName ("a single cell in a column")
        public void singleCellInOneColumn() {
            LineNumbers lineNumbers = new LineNumbers(1);
            Column column1 = new Column("A", "a");
            Sheet sheet = new Sheet(lineNumbers, column1);
            assertEquals(1, sheet.headers().length());
            assertEquals(1, sheet.lineNumbers().length());
            assertEquals("a", sheet.cellValue(0, 0));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals("A", sheet.headers().get(0));
        }

        @Test
        @DisplayName ("multiple cells in a column")
        public void multipleCellsInOneColumn() {
            LineNumbers lineNumbers = new LineNumbers(1, 2, 3);
            Column column1 = new Column("A", "a", "b", "c");
            Sheet sheet = new Sheet(lineNumbers, column1);
            assertEquals(1, sheet.headers().length());
            assertEquals(3, sheet.lineNumbers().length());
            assertEquals("a", sheet.cellValue(0, "A"));
            assertEquals("b", sheet.cellValue(1, "A"));
            assertEquals("c", sheet.cellValue(2, "A"));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals("A", sheet.headers().get(0));
        }

        @Test
        @DisplayName ("multiple column")
        public void multipleColumns() {
            LineNumbers lineNumbers = new LineNumbers(1);
            Column column1 = new Column("A", "a");
            Column column2 = new Column("B", "b");
            Column column3 = new Column("C", "c");
            Sheet sheet = new Sheet(lineNumbers, column1, column2, column3);
            assertEquals(3, sheet.headers().length());
            assertEquals(1, sheet.lineNumbers().length());
            assertEquals("a", sheet.cellValue(0, "A"));
            assertEquals("b", sheet.cellValue(0, "B"));
            assertEquals("c", sheet.cellValue(0, "C"));
            assertEquals("a", sheet.cellValue(0, 0));
            assertEquals("b", sheet.cellValue(0, 1));
            assertEquals("c", sheet.cellValue(0, 2));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals("A", sheet.headers().get(0));
            assertEquals("B", sheet.headers().get(1));
            assertEquals("C", sheet.headers().get(2));
        }
    }

    @Nested
    @DisplayName ("A sheet can be accessed with")
    public class Access {
        @DisplayName ("a cell stream")
        @Test
        public void cellStream() {
            Cell[] expected = new Cell[6];
            expected[0] = new Cell("A", 1, "a1");
            expected[1] = new Cell("A", 2, "a2");
            expected[2] = new Cell("A", 3, "a3");
            expected[3] = new Cell("B", 1, "b1");
            expected[4] = new Cell("B", 2, "b2");
            expected[5] = new Cell("B", 3, "b3");

            Sheet sheet = new Sheet(expected);
            Cell[] actual = sheet.cells().toArray(Cell[]::new);

            Arrays.sort(expected);
            Arrays.sort(actual);
            assertArrayEquals(expected, actual);
        }

        @DisplayName ("a column stream")
        @Test
        public void columnStream() {
            Column[] expected = new Column[3];
            expected[0] = new Column("A", "a1", "a2");
            expected[1] = new Column("B", "b1", "b2");
            expected[2] = new Column("C", "c1", "c2");
            LineNumbers lineNumbers = new LineNumbers(1, 2);

            Sheet sheet = new Sheet(lineNumbers, expected);
            Column[] actual = sheet.columns().toArray(Column[]::new);

            Arrays.sort(expected);
            Arrays.sort(actual);
            assertArrayEquals(expected, actual);
        }

        @DisplayName ("a row stream")
        @Test
        public void rowStream() {
            Row[] expected = new Row[3];
            expected[0] = new Row(1, "a1", "b1");
            expected[1] = new Row(2, "a2", "b2");
            expected[2] = new Row(3, "a3", "b3");
            Headers headers = new Headers("A", "B");

            Sheet sheet = new Sheet(headers, expected);
            Row[] actual = sheet.rows().toArray(Row[]::new);

            Arrays.sort(expected);
            Arrays.sort(actual);
            assertArrayEquals(expected, actual);
        }

        @ParameterizedTest
        @DisplayName ("a row and column indexes")
        @CsvSource ({
                "0, 0, a1", "0, 1, b1", "0, 2, c1",
                "1, 0, a2", "1, 1, b2", "1, 2, c2",
                "2, 0, a3", "2, 1, b3", "2, 2, c3"})
        public void cellByIndex(int row, int column, String value) {
            Headers headers = new Headers("A", "B", "C");
            Row row1 = new Row(1, "a1", "b1", "c1");
            Row row2 = new Row(2, "a2", "b2", "c2");
            Row row3 = new Row(3, "a3", "b3", "c3");
            Sheet sheet = new Sheet(headers, row1, row2, row3);
            assertEquals(value, sheet.cellValue(row, column));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals(2, sheet.lineNumbers().get(1));
            assertEquals(3, sheet.lineNumbers().get(2));
        }

        @ParameterizedTest
        @DisplayName ("a row index and column header")
        @CsvSource ({
                "0, A, a1", "0, B, b1", "0, C, c1",
                "1, A, a2", "1, B, b2", "1, C, c2",
                "2, A, a3", "2, B, b3", "2, C, c3"})
        public void cellByHeader(int row, String column, String value) {
            Headers headers = new Headers("A", "B", "C");
            Row row1 = new Row(1, "a1", "b1", "c1");
            Row row2 = new Row(2, "a2", "b2", "c2");
            Row row3 = new Row(3, "a3", "b3", "c3");
            Sheet sheet = new Sheet(headers, row1, row2, row3);
            assertEquals(value, sheet.cellValue(row, column));
            assertEquals(1, sheet.lineNumbers().get(0));
            assertEquals(2, sheet.lineNumbers().get(1));
            assertEquals(3, sheet.lineNumbers().get(2));
        }
    }
}
