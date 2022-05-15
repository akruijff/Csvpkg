package org.akruijff.csvpkg;

import org.akruijff.csvpkg.entities.*;

public interface Command {
    void help(String[] args);

    void execute(String... args);

    Sheet executeNested(Sheet sheet, String[] args);
}
