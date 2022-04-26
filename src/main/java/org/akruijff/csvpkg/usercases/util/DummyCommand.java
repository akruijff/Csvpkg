package org.akruijff.csvpkg.usercases.util;

import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.usercases.*;

public class DummyCommand extends AbstractCommand {
    @Override
    protected boolean isInsufficient(String[] args) {
        return false;
    }

    @Override
    protected void doHelp(String[] args) {
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        return sheet;
    }
}
