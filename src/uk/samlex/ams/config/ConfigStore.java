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
import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import uk.samlex.ams.AntiMobSpawn;

public class ConfigStore {

    private static ConfigStore INSTANCE;

    public static ConfigStore instance() {
        return INSTANCE;
    }

    private FileConfiguration configFile;
    private boolean debug = false;

    private HashMap<String, WorldConfig> worldConfigMap;

    public ConfigStore() {
        INSTANCE = this;

        AntiMobSpawn.instance().checkDatabase();

        configFile = AntiMobSpawn.instance().getConfig();
        debug = getConfigBoolean("", "debug", debug);
        worldConfigMap = new HashMap<String, WorldConfig>();

        for (World world : AntiMobSpawn.instance().getServer().getWorlds()) {
            worldConfigMap.put(world.getName(), new WorldConfig(world.getName()));
        }

        AntiMobSpawn.instance().saveConfig();
    }

    private boolean configSet(String completePath, Object def) {
        if (!configFile.contains(completePath)) {
            configFile.set(completePath, def);
            return true;
        } else {
            return false;
        }
    }

    protected boolean getConfigBoolean(String world, String path, boolean def) {
        String completePath = world + "." + path;
        if (!configSet(completePath, def)) {
            return configFile.getBoolean(completePath);
        } else {
            return def;
        }
    }

    protected Enum<?> getConfigEnum(String world, String path, Enum<?> def) {
        String completePath = world + "." + path;
        try {
            if (!configSet(completePath, def.toString())) {
                String e = getConfigString(world, path, def.toString()).toUpperCase();
                if (def instanceof HeightLimitMode) {
                    return HeightLimitMode.valueOf(e);
                } else if (def instanceof WorldMode) {
                    return WorldMode.valueOf(e);
                } else {
                    return null;
                }
            } else {
                return def;
            }
        } catch (IllegalArgumentException iae) {
            AntiMobSpawn.instance().getLogger().severe("Unknown string detected in " + completePath + ". Please check your config file.");
            return null;
        }
    }

    public FileConfiguration getConfigFile() {
        return configFile;
    }

    protected int getConfigInt(String world, String path, int def) {
        String completePath = world + "." + path;
        if (!configSet(completePath, def)) {
            return configFile.getInt(completePath);
        } else {
            return def;
        }
    }

    protected String getConfigString(String world, String path, String def) {
        String completePath = world + "." + path;
        if (!configSet(completePath, def)) {
            return configFile.getString(completePath);
        } else {
            return def;
        }
    }

    protected List<String> getConfigStringList(String world, String path, List<String> def) {
        String completePath = world + "." + path;
        if (!configSet(completePath, def)) {
            return configFile.getStringList(completePath);
        } else {
            return def;
        }
    }

    public HashMap<String, WorldConfig> getWorldConfigMap() {
        return worldConfigMap;
    }

    public boolean isDebug() {
        return debug;
    }

    public void reloadConfig() {
        AntiMobSpawn.instance().reloadConfig();

        configFile = AntiMobSpawn.instance().getConfig();
        debug = getConfigBoolean("", "debug", debug);
        worldConfigMap = new HashMap<String, WorldConfig>();

        for (World world : AntiMobSpawn.instance().getServer().getWorlds()) {
            worldConfigMap.put(world.getName(), new WorldConfig(world.getName()));
        }

        AntiMobSpawn.instance().saveConfig();
    }
}
