package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.exceptions.*;

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
    protected void doPreExecute(String[] args) {
        try {
            System.setIn(new FileInputStream(new File(System.getProperty("user.dir"), args[1])));
        } catch (FileNotFoundException e) {
            throw new FileNotFound(args[1]);
        }
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        return sheet;
    }
}