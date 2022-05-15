package org.akruijff.csvpkg.exceptions;

public class IOError extends MessageException {
    public IOError(String label) {
        super("Input/output error: " + label);
    }
}
