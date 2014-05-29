package uk.samlex.ams.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.ams.config.WorldZone;

public class CreateCommand extends GenericCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1)
            return false;

        String zoneName = args[0];
        String worldName;

        if (args.length > 1) {
            worldName = args[1];
        } else if (sender instanceof Player) {
            worldName = ((Player) sender).getWorld().getName();
        } else {
            sender.sendMessage(ChatColor.RED + "A world name is required from the console");
            return false;
        }

        WorldZone zone = AntiMobSpawn.instance().getDatabase().find(WorldZone.class).where().ieq("worldName", worldName).ieq("zoneName", zoneName).findUnique();

        if (zone == null) {
            zone = new WorldZone();
            zone.setWorldName(worldName);
            zone.setZoneName(zoneName);
            WorldZone.setPointOneVector(zone, new Vector());
            WorldZone.setPointTwoVector(zone, new Vector());

            AntiMobSpawn.instance().getDatabase().save(zone);
            sender.sendMessage(ChatColor.GREEN + "Created zone " + zoneName + " on " + worldName);
        } else {
            sender.sendMessage(ChatColor.RED + "A zone with this name already exists on this world");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length < 2)
            return new ArrayList<String>(0);

        World[] worlds = sender.getServer().getWorlds().toArray(new World[0]);
        String[] worldNames = new String[worlds.length];

        for (int i = 0; i < worldNames.length; i++) {
            worldNames[i] = worlds[i].getName();
        }

        return checkPartialArgument(args[1], worldNames);
    }
}
