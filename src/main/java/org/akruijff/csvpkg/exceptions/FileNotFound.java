package org.akruijff.csvpkg.exceptions;

public class FileNotFound extends MessageException {
    public FileNotFound(String f) {
        super("File not found: " + f);
    }
}
