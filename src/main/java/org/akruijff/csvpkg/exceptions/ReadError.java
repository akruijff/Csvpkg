package org.akruijff.csvpkg.exceptions;

public class ReadError extends MyRuntimeException {
    public ReadError() {
        super("Read error");
    }
}
