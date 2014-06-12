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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.bukkitcommon.command.BukkitCommand;
import uk.samlex.bukkitcommon.config.WorldZone;

public class ListCommand extends BukkitCommand {

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
                sender.sendMessage(ChatColor.GOLD + "    " + zone.getZoneName() + " - point one: " + blockVectorString(WorldZone.getPointOneVector(zone)) + ", point two: " + blockVectorString(WorldZone.getPointTwoVector(zone)));
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
                possiblities = getWorldNames(AntiMobSpawn.instance().getServer());
                part = args[0];
                break;
            default:
                return new ArrayList<String>(0);
        }

        return checkPartialArgument(part, possiblities);
    }
}
