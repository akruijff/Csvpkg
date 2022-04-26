package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.entities.*;

public class Help extends AbstractCommand {
    private Command nested;

    public Help(Command nested) {
        this.nested = nested;
    }

    @Override
    protected boolean isInsufficient(String[] args) {
        return args.length < 2;
    }

    @Override
    public void execute(String... args) {
        nested.help(args);
    }

    @Override
    protected void doHelp(String[] args) {
        nested.help(args);
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        nested.help(args);
        return sheet;
    }
}
