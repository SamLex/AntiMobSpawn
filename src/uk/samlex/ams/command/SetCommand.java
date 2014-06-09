/*
 * AntiMobSpawn, a plugin for the Minecraft server modification Bukkit. Provides control over
 * in game creature spawns
 * 
 * Copyright (C) 2014 Euan James Hunter <euanhunter117@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package uk.samlex.ams.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.bukkitcommon.command.BukkitCommand;
import uk.samlex.bukkitcommon.config.WorldZone;

public class SetCommand extends BukkitCommand {

    private static final Material[] lookAtIgnore = { Material.AIR, Material.WATER, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        String worldName;
        String zoneName = args[0];
        String point = args[1];
        Vector pointVector;

        if (args.length > 5) {
            worldName = args[5];
        } else if (sender instanceof Player) {
            worldName = ((Player) sender).getWorld().getName();
        } else {
            sender.sendMessage(ChatColor.RED + "A world name is required from the console");
            return false;
        }

        if (args.length > 4) {
            try {
                int x, y, z;
                x = Integer.parseInt(args[2]);
                y = Integer.parseInt(args[3]);
                z = Integer.parseInt(args[4]);
                pointVector = new Vector(x, y, z);
            } catch (IllegalArgumentException iae) {
                return false;
            }
        } else if (sender instanceof Player) {
            Block lookingAt = playerLookingAt(((Player) sender));
            if (lookingAt == null) {
                return false;
            }
            pointVector = lookingAt.getLocation().toVector();
        } else {
            sender.sendMessage(ChatColor.RED + "Coordinates are required from the console");
            return false;
        }

        WorldZone zone = AntiMobSpawn.instance().getDatabase().find(WorldZone.class).where().ieq("worldName", worldName).ieq("zoneName", zoneName).findUnique();

        if (zone == null) {
            sender.sendMessage(ChatColor.RED + "A zone with this name does not exist on this world");
            return true;
        } else {
            switch (point) {
                case "one":
                    WorldZone.setPointOneVector(zone, pointVector);
                    break;
                case "two":
                    WorldZone.setPointTwoVector(zone, pointVector);
                    break;
                default:
                    return false;
            }

            sender.sendMessage(ChatColor.GREEN + "Set point " + point + " of " + zoneName + " on " + worldName + " to " + blockVectorString(pointVector));
            AntiMobSpawn.instance().getDatabase().save(zone);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] possiblities;
        String part;

        switch (args.length) {
            case 2:
                possiblities = new String[] { "one", "two" };
                part = args[1];
                break;
            case 6:
                possiblities = getWorldNames(AntiMobSpawn.instance());
                part = args[5];
                break;
            case 1:
                if (sender instanceof Player) {
                    possiblities = getZoneNames(((Player) sender).getWorld().getName(), AntiMobSpawn.instance());
                    part = args[0];
                    break;
                }
            case 3:
            case 4:
            case 5:
            default:
                return new ArrayList<String>(0);
        }

        return checkPartialArgument(part, possiblities);
    }

    private Block playerLookingAt(Player player) {
        BlockIterator looker = new BlockIterator(player, 5);
        List<Material> lookAtIgnoreList = Arrays.asList(lookAtIgnore);

        while (looker.hasNext()) {
            Block block = looker.next();
            if (lookAtIgnoreList.contains(block.getType())) {
                continue;
            }
            return block;
        }
        return null;
    }
}
