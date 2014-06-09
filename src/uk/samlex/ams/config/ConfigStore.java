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

package uk.samlex.ams.config;

import java.util.HashMap;

import org.bukkit.World;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.bukkitcommon.config.Config;

public class ConfigStore extends Config {

    private static ConfigStore INSTANCE;

    public static ConfigStore instance() {
        return INSTANCE;
    }

    private boolean debug = false;

    private HashMap<String, WorldConfig> worldConfigMap;

    public ConfigStore() {
        super(AntiMobSpawn.instance());

        INSTANCE = this;

        AntiMobSpawn.instance().checkDatabase();

        debug = getConfigBoolean("", "debug", debug);
        worldConfigMap = new HashMap<String, WorldConfig>();

        for (World world : AntiMobSpawn.instance().getServer().getWorlds()) {
            worldConfigMap.put(world.getName(), new WorldConfig(world.getName()));
        }

        AntiMobSpawn.instance().saveConfig();
    }

    public HashMap<String, WorldConfig> getWorldConfigMap() {
        return worldConfigMap;
    }

    public boolean isDebug() {
        return debug;
    }

    public void reloadConfig() {
        AntiMobSpawn.instance().reloadConfig();
        super.reloadConfig(AntiMobSpawn.instance());

        debug = getConfigBoolean("", "debug", debug);
        worldConfigMap = new HashMap<String, WorldConfig>();

        for (World world : AntiMobSpawn.instance().getServer().getWorlds()) {
            worldConfigMap.put(world.getName(), new WorldConfig(world.getName()));
        }

        AntiMobSpawn.instance().saveConfig();
    }
}
