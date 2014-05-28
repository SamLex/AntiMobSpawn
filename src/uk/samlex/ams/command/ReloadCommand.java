package uk.samlex.ams.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import uk.samlex.ams.config.ConfigStore;

public class ReloadCommand extends GenericCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigStore.instance().reloadConfig();
        sender.sendMessage("Reloaded config file");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>(0);
    }
}
