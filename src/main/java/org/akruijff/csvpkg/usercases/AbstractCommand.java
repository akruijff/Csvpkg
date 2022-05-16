package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.usercases.util.*;

import static org.akruijff.csvpkg.usercases.util.CommandUtil.*;

public abstract class AbstractCommand implements Command {
    protected static String[] subset(String[] arr, int index) {
        return subset(arr, index, arr.length - index);
    }

    protected static String[] subset(String[] arr, int index, int length) {
        String[] dst = new String[length];
        System.arraycopy(arr, index, dst, 0, length);
        return dst;
    }

    @Override
    public void help(String[] args) {
        doHelp(args);
    }

    @Override
    public final void execute(String... args) {
        if (isInsufficient(args))
            doHelp(args);
        else
            executeHelper(args);
    }

    @Override
    public final Sheet executeNested(Sheet sheet, String[] args) {
        return isInsufficient(args)
                ? new ShowErrorCommand("Too few arguments in line: " + getLine(args)).doExecute(sheet, args)
                : doExecuteNested(sheet, args);
    }

    protected abstract boolean isInsufficient(String[] args);

    protected abstract void doHelp(String[] args);

    private void executeHelper(String[] args) {
        Sheet loadedSheet = doPreExecute(args);
        Sheet manipulatedSheet = doExecute(loadedSheet, args);
        doPostExecute(manipulatedSheet, args);
    }

    protected Sheet doExecuteNested(Sheet sheet, String[] args) {
        return doExecute(sheet, args);
    }

    protected Sheet doPreExecute(String[] args) {
        return IOUtil.read(System.in, "stdin");
    }

    protected abstract Sheet doExecute(Sheet sheet, String[] args);

    protected void doPostExecute(Sheet sheet, String[] args) {
        IOUtil.write(sheet);
    }
}
