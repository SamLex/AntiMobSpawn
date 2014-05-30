package uk.samlex.ams.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.ams.config.WorldZone;
import uk.samlex.ams.util.BoundingBoxPreviewType;


public class PreviewCommand extends GenericCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            return false;
        }

        String worldName;
        String zoneName = args[0];
        String typeString = args[1].toUpperCase();
        String matString = args[2].toUpperCase();
        String replaceString = args[3].toLowerCase();

        if (args.length > 4) {
            worldName = args[4];
        } else if (sender instanceof Player) {
            worldName = ((Player) sender).getWorld().getName();
        } else {
            sender.sendMessage(ChatColor.RED + "A world name is required from the console");
            return false;
        }

        BoundingBoxPreviewType type;
        Material mat;
        boolean replace;

        try {
            type = BoundingBoxPreviewType.valueOf(typeString);
            mat = Material.valueOf(matString);
            replace = Boolean.parseBoolean(replaceString);
        } catch (IllegalArgumentException iae) {
            return false;
        }

        WorldZone zone = AntiMobSpawn.instance().getDatabase().find(WorldZone.class).where().ieq("worldName", worldName).ieq("zoneName", zoneName).findUnique();
        World world = sender.getServer().getWorld(worldName);
        
        if (zone == null) {
            sender.sendMessage(ChatColor.RED + "A zone with this name does not exist on this world");
            return true;
        }

        
        WorldZone.getBoundingBox(zone).previewBoundingBox(type, world, mat, replace);
        
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] possiblities;
        String part;

        switch (args.length) {
            case 2:
                BoundingBoxPreviewType[] types = BoundingBoxPreviewType.values();
                possiblities = new String[types.length];
                part = args[1];

                for (int i = 0; i < possiblities.length; i++) {
                    possiblities[i] = types[i].toString();
                }

                break;
            case 3:
                Material[] mats = Material.values();
                possiblities = new String[mats.length];
                part = args[2];

                for (int i = 0; i < possiblities.length; i++) {
                    possiblities[i] = mats[i].toString().toLowerCase();
                }

                break;
            case 4:
                possiblities = new String[] { "false", "true" };
                part = args[3];
                break;
            case 5:
                possiblities = getWorldNames();
                part = args[4];
                break;
            case 1:
                if (sender instanceof Player) {
                    possiblities = getZoneNames(((Player) sender).getWorld().getName());
                    part = args[0];
                    break;
                }
            default:
                return new ArrayList<String>(0);
        }

        return checkPartialArgument(part, possiblities);
    }
}
