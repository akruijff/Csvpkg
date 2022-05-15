package org.akruijff.csvpkg.exceptions;

public class ColumnNotFound extends MessageException {
    public ColumnNotFound(String column) {
        super("Column '" + column + "' not found.");
    }
}
