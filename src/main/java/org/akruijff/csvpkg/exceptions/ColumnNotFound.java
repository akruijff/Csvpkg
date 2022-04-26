package org.akruijff.csvpkg.exceptions;

public class ColumnNotFound extends MyRuntimeException {
    public ColumnNotFound(String column) {
        super("Column '" + column + "' not found.");
    }
}
