package org.akruijff.csvpkg.exceptions;

public class RowNotFound extends MessageException {
    public RowNotFound(int line) {
        super("Row '" + line + "' not found.");
    }
}
