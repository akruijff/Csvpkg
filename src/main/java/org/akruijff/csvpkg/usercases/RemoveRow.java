package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.entities.*;

import java.util.regex.*;

import static org.akruijff.csvpkg.usercases.util.PatternUtil.*;

public class RemoveRow extends AbstractCommand {
    @Override
    protected boolean isInsufficient(String[] args) {
        return args.length < 3;
    }

    @Override
    protected void doHelp(String[] args) {
        System.err.println("""
                remove <condition column> <pattern> [<flags>]
                                
                Clears the cell in the modification column when the value in the condition column matches the regular
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
        int flags = args.length >= 4 ? flags(args[3]) : 0;
        Pattern pattern = Pattern.compile(args[2], flags);

        int r = sheet.headers().indexOf(args[1]);
        Row[] rows = sheet.rows()
                .filter(row -> !pattern.matcher((CharSequence) row.column(r)).find())
                .toArray(Row[]::new);
        return new Sheet(sheet.headers(), rows);
    }
}
