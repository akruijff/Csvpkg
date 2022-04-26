package org.akruijff.csvpkg.exceptions;

public class FileNotFound extends MyRuntimeException {
    public FileNotFound(String f) {
        super("File not found: " + f);
    }
}
