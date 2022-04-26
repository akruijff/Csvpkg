package org.akruijff.csvpkg.entities;

import org.akruijff.csvpkg.exceptions.*;

public class LineNumbers {
    private final int[] lineNumbers;

    public LineNumbers(int... lineNumbers) {
        this.lineNumbers = lineNumbers;
    }

    public int length() {
        return lineNumbers.length;
    }

    public int get(int row) {
        return lineNumbers[row];
    }

    public int indexOf(int lineNumber) {
        for (int i = 0, n = lineNumbers.length; i < n; ++i)
            if (lineNumbers[i] == lineNumber)
                return i;
        throw new RowNotFound(lineNumber);
    }
}
