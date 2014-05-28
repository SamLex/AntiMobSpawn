package uk.samlex.ams.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CreateCommand extends GenericCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
