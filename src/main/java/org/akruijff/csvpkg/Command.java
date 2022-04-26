package org.akruijff.csvpkg;

public interface Command {
    void help(String[] args);

    void execute(String... args);
}
