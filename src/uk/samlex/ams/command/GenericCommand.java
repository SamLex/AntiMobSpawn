package uk.samlex.ams.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import uk.samlex.ams.AntiMobSpawn;

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

    protected static String[] getWorldNames() {
        World[] worlds = AntiMobSpawn.instance().getServer().getWorlds().toArray(new World[0]);
        String[] worldNames = new String[worlds.length];

        for (int i = 0; i < worldNames.length; i++) {
            worldNames[i] = worlds[i].getName();
        }

        return worldNames;
    }
}
