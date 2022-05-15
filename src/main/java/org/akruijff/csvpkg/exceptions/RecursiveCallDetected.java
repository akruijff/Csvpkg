package org.akruijff.csvpkg.exceptions;

public class RecursiveCallDetected extends MessageException {
    public RecursiveCallDetected(String caller, String calling) {
        super("Recursive call detected. Caller: " + caller + " Executing: " + calling);
    }
}
