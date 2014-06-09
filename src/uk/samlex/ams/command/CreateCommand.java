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
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.bukkitcommon.command.BukkitCommand;
import uk.samlex.bukkitcommon.config.WorldZone;

public class CreateCommand extends BukkitCommand {

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
        String[] possiblities;
        String part;

        switch (args.length) {
            case 2:
                possiblities = getWorldNames(AntiMobSpawn.instance());
                part = args[1];
                break;
            default:
                return new ArrayList<String>(0);
        }

        return checkPartialArgument(part, possiblities);
    }
}
