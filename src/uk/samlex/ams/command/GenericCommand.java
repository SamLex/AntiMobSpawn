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

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.Vector;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.ams.config.WorldZone;

public abstract class GenericCommand implements CommandExecutor, TabCompleter {

    protected static String blockVectorString(Vector blockVector) {
        return String.format("%d,%d,%d", blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ());
    }

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

    protected static String[] getZoneNames(String worldName) {
        WorldZone[] zones = AntiMobSpawn.instance().getDatabase().find(WorldZone.class).where().ieq("worldName", worldName).findList().toArray(new WorldZone[0]);
        String[] zoneNames = new String[zones.length];

        for (int i = 0; i < zoneNames.length; i++) {
            zoneNames[i] = zones[i].getZoneName();
        }

        return zoneNames;
    }
}
