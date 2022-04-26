package org.akruijff.csvpkg.exceptions;

public class RowNotFound extends MyRuntimeException {
    public RowNotFound(int line) {
        super("Row '" + line + "' not found.");
    }
}
