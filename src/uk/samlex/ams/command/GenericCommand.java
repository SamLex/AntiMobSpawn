package uk.samlex.ams.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public abstract class GenericCommand implements CommandExecutor, TabCompleter {

    protected static List<String> checkPartialArgument(String partialArgument, String[] possiblities) {
        ArrayList<String> morePossible = new ArrayList<>();
        for (String s : possiblities) {
            if (partialArgument.length() < s.length())
                if (partialArgument.startsWith(s.substring(0, partialArgument.length())))
                    morePossible.add(s);
        }
        return morePossible;
    }
}
