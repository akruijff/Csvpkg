package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;

import java.util.*;

public class SelectColumns extends AbstractCommand {
    @Override
    protected boolean isInsufficient(String[] args) {
        return args.length < 2;
    }

    @Override
    protected void doHelp(String[] args) {
        System.err.println("""
                select <column> [<column> [...]]
                                
                Selects only the the specified columns and removes all other columns.
                """);
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        Column[] columns = sheet.columns()
                .filter(c -> Arrays.stream(args).anyMatch(s -> s.equals(c.header())))
                .toArray(Column[]::new);
        return new Sheet(sheet.lineNumbers(), columns);
    }
}
