package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.exceptions.IOError;
import org.akruijff.csvpkg.exceptions.*;
import org.akruijff.csvpkg.usercases.util.*;

import java.io.*;

public class Cat extends AbstractCommand {
    protected boolean isInsufficient(String[] args) {
        return args.length < 2;
    }

    protected void doHelp(String[] args) {
        System.err.println("""
                cat <path to file>
                                
                Cat reads a CSV file and prints it.
                """);
    }

    @Override
    protected Sheet doExecuteNested(Sheet sheet, String[] args) {
        return doPreExecute(args);
    }

    @Override
    protected Sheet doPreExecute(String[] args) {
        try (InputStream in = new FileInputStream(new File(System.getProperty("user.dir"), args[1]))) {
            return IOUtil.read(in, "stdin");
        } catch (FileNotFoundException e) {
            throw new FileNotFound(args[1]);
        } catch (IOException e) {
            throw new IOError(args[1]);
        }
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        return sheet;
    }
}