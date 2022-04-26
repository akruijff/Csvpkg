package org.akruijff.csvpkg.usercases.util;

import org.akruijff.csvpkg.entities.*;

import java.util.function.*;
import java.util.regex.*;

public record Mapper(Sheet sheet, String conditionColumn, String modificationColumn, Pattern pattern) {
    public Cell[] mapCells(Function<Cell, Cell> action) {
        sheet.headers().indexOf(conditionColumn);
        sheet.headers().indexOf(modificationColumn);
        return sheet.cells().map(cell -> mapCell(action, cell)).toArray(Cell[]::new);
    }

    private Cell mapCell(Function<Cell, Cell> action, Cell cell) {
        return isCellInColumn(cell, modificationColumn) && isMatch(pattern, conditionValue(cell))
                ? action.apply(cell)
                : cell;
    }

    private boolean isCellInColumn(Cell cell, String column) {
        return cell.header().equals(column);
    }

    private boolean isMatch(Pattern pattern, CharSequence s) {
        return pattern.matcher(s).find();
    }

    private CharSequence conditionValue(Cell cell) {
        int row = sheet.lineNumbers().indexOf(cell.lineNumber());
        int column = sheet.headers().indexOf(conditionColumn);
        return (CharSequence) sheet.cellValue(row, column);
    }
}