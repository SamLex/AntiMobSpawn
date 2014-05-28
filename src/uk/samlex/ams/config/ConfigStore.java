package uk.samlex.ams.config;

import java.util.HashMap;
import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import uk.samlex.ams.AntiMobSpawn;

public class ConfigStore {

    private static ConfigStore INSTANCE;

    private FileConfiguration configFile;

    private HashMap<String, WorldConfig> worldConfigMap;

    public ConfigStore() {
        INSTANCE = this;

        AntiMobSpawn.instance().checkDatabase();

        configFile = AntiMobSpawn.instance().getConfig();
        worldConfigMap = new HashMap<String, WorldConfig>();

        for (World world : AntiMobSpawn.instance().getServer().getWorlds()) {
            worldConfigMap.put(world.getName(), new WorldConfig(world.getName()));
        }

        AntiMobSpawn.instance().saveConfig();
    }

    public static ConfigStore instance() {
        return INSTANCE;
    }

    public HashMap<String, WorldConfig> getWorldConfigMap() {
        return worldConfigMap;
    }

    public FileConfiguration getConfigFile() {
        return configFile;
    }

    protected boolean getConfigBoolean(String world, String path, boolean def) {
        String completePath = world + "." + path;
        if (!configSet(completePath, def)) {
            return configFile.getBoolean(completePath);
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

    protected int getConfigInt(String world, String path, int def) {
        String completePath = world + "." + path;
        if (!configSet(completePath, def)) {
            return configFile.getInt(completePath);
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
            iae.printStackTrace();
            return null;
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

    private boolean configSet(String completePath, Object def) {
        if (!configFile.contains(completePath)) {
            configFile.set(completePath, def);
            return true;
        } else {
            return false;
        }
    }
}
