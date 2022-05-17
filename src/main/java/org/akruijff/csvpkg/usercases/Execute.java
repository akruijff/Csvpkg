package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.exceptions.IOError;
import org.akruijff.csvpkg.exceptions.*;
import org.akruijff.csvpkg.usercases.util.*;

import java.io.*;
import java.util.*;

import static org.akruijff.csvpkg.usercases.util.CommandUtil.*;

public class Execute extends AbstractCommand {
    private static final Set<String> set = new HashSet<>();
    private static final Deque<String> stack = new LinkedList<>();

    @Override
    protected boolean isInsufficient(String[] args) {
        return args.length < 2;
    }

    @Override
    protected void doHelp(String[] args) {
        System.err.println("""
                execute <path to script>
                                
                Reads a CSV file and executes it. The first argument in the CSV file must hold a command.
                                
                Supported commands: clear, execute, invert, maps, invert, map, remove, select, and sum.
                """);
    }

    @Override
    protected Sheet doExecute(Sheet sheet, String[] args) {
        return doExecute(sheet, args[1]);
    }

    private Sheet doExecute(Sheet sheet, String path) {
        lock(path);
        try (FileInputStream in = new FileInputStream(new File(System.getProperty("user.dir"), path))) {
            Sheet commands = IOUtil.read(in, path);
            return executeSheet(commands, sheet);
        } catch (FileNotFoundException e) {
            throw new FileNotFound(path);
        } catch (IOException e) {
            throw new IOError(path);
        } finally {
            unlock(path);
        }
    }

    private void lock(String path) {
        if (set.contains(path))
            throw new RecursiveCallDetected(stack.peekFirst(), path);
        set.add(path);
        stack.push(path);
    }

    private Sheet executeSheet(Sheet commands, Sheet sheet) {
        Wrapper<Sheet> wrapper = new Wrapper<>(sheet);
        commands.rows()
                .map(this::getArgs)
                .forEach(args -> wrapper.set(executeLine(wrapper.get(), args)));
        return wrapper.get();
    }

    private String[] getArgs(Row row) {
        return row.cells()
                .map(Object::toString)
                .toArray(String[]::new);
    }

    private Sheet executeLine(Sheet sheet, String[] args) {
        try {
            Command command = createCommand(args);
            return command.executeNested(sheet, args);
        } catch (RecursiveCallDetected e) {
            throw e;
        } catch(Exception e) {
            System.err.println("Failed to execute: " + getLine(args));
            return sheet;
        }
    }

    private Command createCommand(String[] args) {
        String action = args[0];
        return switch (action.toLowerCase()) {
            case "cat" -> new Cat();
            case "clear" -> new ClearCell();
            case "execute" -> new Execute();
            case "invert" -> new InvertCell();
            case "map" -> new MapValue();
            case "remove" -> new RemoveRow();
            case "select" -> new SelectColumns();
            case "sum" -> new SumColumn();
            default -> new ShowErrorCommand("Unsupported command in line: " + getLine(args));
        };
    }

    private void unlock(String path) {
        set.remove(path);
        stack.pop();
    }
}
