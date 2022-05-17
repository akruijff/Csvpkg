package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.usercases.util.*;

public class SumColumn extends AbstractCommand {
    @Override
    protected boolean isInsufficient(String[] args) {
        return args.length < 2;
    }

    @Override
    protected void doHelp(String[] args) {
        System.err.println("""
                sum <modification column> <selection column> [<selection column> [...]]
                                
                Sums the numerical value in the modification column for all unique combinations of the selection
                columns.
                """);
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        String[] arr = subset(args, 2);
        return sumColumn(sheet, args[1], arr);
    }

    public static Sheet sumColumn(Sheet sheet, String modificationColumn, String... selectionColumns) {
        return new Reducer(sheet, modificationColumn, Double::sum).reduce(selectionColumns);
    }

}
