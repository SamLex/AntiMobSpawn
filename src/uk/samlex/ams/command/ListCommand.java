package uk.samlex.ams.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.ams.config.WorldZone;

public class ListCommand extends GenericCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<WorldZone> zones;
        ArrayList<String> worldNames = new ArrayList<String>();

        if (args.length > 0) {
            worldNames.add(args[0]);
        } else {
            for (World world : sender.getServer().getWorlds()) {
                worldNames.add(world.getName());
            }
        }

        for (String worldName : worldNames) {
            sender.sendMessage(ChatColor.YELLOW + worldName + ":");
            zones = AntiMobSpawn.instance().getDatabase().find(WorldZone.class).where().ieq("worldName", worldName).findList();

            for (WorldZone zone : zones) {
                sender.sendMessage(ChatColor.GOLD + "    " + zone.getZoneName() + " - point one: " + zone.getPointOneVector() + ", point two: " + zone.getPointTwoVector());
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length < 1)
            return null;

        World[] worlds = sender.getServer().getWorlds().toArray(new World[0]);
        String[] worldNames = new String[worlds.length];

        for (int i = 0; i < worldNames.length; i++) {
            worldNames[i] = worlds[i].getName();
        }

        return checkPartialArgument(args[0], worldNames);
    }
}
