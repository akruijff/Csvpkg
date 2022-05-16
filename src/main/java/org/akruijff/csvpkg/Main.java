package org.akruijff.csvpkg;

import org.akruijff.csvpkg.exceptions.*;
import org.akruijff.csvpkg.usercases.*;

public class Main {
    public static final String DefaultAction = "";
    public static void main(String[] args) {
        try {
            Command action = createAction(
                    args.length > 0 ? args[0] : DefaultAction,
                    args.length > 1 ? args[1] : DefaultAction);
            action.execute(args);
        } catch (MessageException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("An unknown error occurred.");
        }
    }

    private static Command createAction(String action, String secondAction) {
        return switch (action.toLowerCase()) {
            case "cat" -> new Cat();
            case "clear" -> new ClearCell();
            case "execute" -> new Execute();
            case "help", "man" -> new Help(createAction(secondAction, DefaultAction));
            case "invert" -> new InvertCell();
            case "map" -> new MapValue();
            case "remove" -> new RemoveRow();
            case "select" -> new SelectColumns();
            case "sum" -> new SumColumn();
            default -> new Help(new GenericHelp());
        };
    }
}
