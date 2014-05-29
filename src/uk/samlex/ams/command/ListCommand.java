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
                sender.sendMessage(ChatColor.GOLD + "    " + zone.getZoneName() + " - point one: " + WorldZone.getPointOneVector(zone) + ", point two: " + WorldZone.getPointTwoVector(zone));
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] possiblities;
        String part;
        
        switch (args.length) {
            case 1:
                possiblities = getWorldNames();
                part = args[1];
                break;
            default:
                return new ArrayList<String>(0);
        }

        return checkPartialArgument(part, possiblities);
    }
}
