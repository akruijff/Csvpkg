package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.usercases.util.*;

import java.util.regex.*;

import static java.util.regex.Pattern.*;
import static org.akruijff.csvpkg.usercases.util.PatternUtil.*;

public class MapValue extends AbstractCommand {
    @Override
    protected boolean isInsufficient(String[] args) {
        return args.length < 5;
    }

    @Override
    protected void doHelp(String[] args) {
        System.err.println("""
                map <condition column> <modification column> <replacement literal> <regular expression> [<flags>]
                                
                Map replaces the value at the column with the replacement literal when it matches the regular 
                expression. The following flags are available to modify the regular expression:
                - d unix newlines (\\n instead of \\r\\n)
                - i ignore case
                - x comments (whitespace is ignored and everything from # until the end of the line)
                - m multiline (expressions ^ and $ match just after of before, respectively, a line terminator or the
                    end of the input sequence. The default is to only match at the beginning of the end of the entire
                    input sequence.
                - s dot all (a dot will match any character including the line terminator)
                - u unicode
                - U unicode character class        
                """);
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        int flags = args.length >= 6 ? flags(args[5]) : 0;
        Pattern pattern = compile(args[4], flags);
        return map(sheet, args[1], args[2], args[3], pattern);
    }

    private Sheet map(Sheet sheet, String conditionColumn, String modificationColumn, String replacement, Pattern pattern) {
        Cell[] cells = new Mapper(sheet, conditionColumn, modificationColumn, pattern)
                .mapCells(cell -> new Cell(cell.header(), cell.lineNumber(), replacement));
        return new Sheet(cells);
    }
}
