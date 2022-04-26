package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.entities.*;

import static org.akruijff.csvpkg.usercases.util.IOUtil.*;

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

    public void execute(String... args) {
        if (isInsufficient(args))
            doHelp(args);
        else
            executeHelper(args);
    }

    protected abstract boolean isInsufficient(String[] args);

    protected abstract void doHelp(String[] args);

    private void executeHelper(String[] args) {
        doPreExecute(args);
        Sheet sheet = read();
        sheet = doExecute(sheet, args);
        sheet = doPostExecute(sheet, args);
        write(sheet);
    }

    protected void doPreExecute(String[] args) {
    }

    protected abstract Sheet doExecute(Sheet sheet, String[] args);

    protected Sheet doPostExecute(Sheet sheet, String[] args) {
        return sheet;
    }
}
