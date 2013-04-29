/*
 * AntiMobSpawn is a plugin for the Minecraft Server Mod Bukkit. AntiMobSpawn gives you ultimate control over the creatures that spawn in your Minecraft worlds
 * Copyright (C) 2011-2013 Euan J Hunter (SamLex) (Sam_Lex)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.samlex.ams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class AMSConfig {

    private AntiMobSpawn plugin;
    private FileConfiguration config;

    private boolean multi;

    private HashMap<String, AMSWorldConfig> worlds;

    public AMSConfig(AntiMobSpawn plugin) {
        this.plugin = plugin;
        setupConfig();
    }

    private void setupConfig() {
        config = plugin.getConfig();

        if (config.contains("multi"))
            config.options().copyDefaults(true);

        multi = config.getBoolean("multi");
        worlds = new HashMap<>();

        setupFile();
        internalConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();

        config = plugin.getConfig();
        multi = config.getBoolean("multi");
        worlds = new HashMap<>();

        internalConfig();
    }

    // Populates the internal worlds config and hashmaps
    private void internalConfig() {
        if (config.getBoolean("Default.enabled")) {
            AMSWorldConfig default_world = new AMSWorldConfig("Default");

            default_world.setZone(false);
            default_world.setZone_type("safe");
            default_world.setHeight_limit(config.getInt("Default.height limit"));
            default_world.setHeight_limit_type(config.getString("Default.height limit type"));
            default_world.setTime_start(config.getInt("Default.time start"));
            default_world.setTime_end(config.getInt("Default.time end"));
            default_world.setAll_blocks(config.getBoolean("Default.all blocks"));
            default_world.setBlocks(config.getIntegerList("Default.blocks"));

            default_world.getSpawn_map().put("breeding", config.getBoolean("Default.spawn.Breeding"));
            default_world.getSpawn_map().put("build_irongolem", config.getBoolean("Default.spawn.Build Iron Golem"));
            default_world.getSpawn_map().put("build_snowman", config.getBoolean("Default.spawn.Build Snowman"));
            default_world.getSpawn_map().put("build_wither", config.getBoolean("Default.spawn.Build Wither"));
            default_world.getSpawn_map().put("chunk_gen", config.getBoolean("Default.spawn.Chunk Generation"));
            default_world.getSpawn_map().put("custom", config.getBoolean("Default.spawn.Custom"));
            default_world.getSpawn_map().put("egg", config.getBoolean("Default.spawn.Egg"));
            default_world.getSpawn_map().put("jockey", config.getBoolean("Default.spawn.Jockey"));
            default_world.getSpawn_map().put("lightning", config.getBoolean("Default.spawn.Lightning"));
            default_world.getSpawn_map().put("natural", config.getBoolean("Default.spawn.Natural"));
            default_world.getSpawn_map().put("slime_split", config.getBoolean("Default.spawn.Slime Split"));
            default_world.getSpawn_map().put("spawner", config.getBoolean("Default.spawn.Spawner"));
            default_world.getSpawn_map().put("spawner_egg", config.getBoolean("Default.spawn.Spawn Egg"));
            default_world.getSpawn_map().put("village_defense", config.getBoolean("Default.spawn.Village Defense"));
            default_world.getSpawn_map().put("village_invasion", config.getBoolean("Default.spawn.Village Invasion"));

            default_world.getCreature_map().put("bat", config.getBoolean("Default.creature.bat"));
            default_world.getCreature_map().put("blaze", config.getBoolean("Default.creature.blaze"));
            default_world.getCreature_map().put("cave_spider", config.getBoolean("Default.creature.cave spider"));
            default_world.getCreature_map().put("chicken", config.getBoolean("Default.creature.chicken"));
            default_world.getCreature_map().put("cow", config.getBoolean("Default.creature.cow"));
            default_world.getCreature_map().put("creeper", config.getBoolean("Default.creature.creeper"));
            default_world.getCreature_map().put("ender_dragon", config.getBoolean("Default.creature.ender dragon"));
            default_world.getCreature_map().put("enderman", config.getBoolean("Default.creature.enderman"));
            default_world.getCreature_map().put("ghast", config.getBoolean("Default.creature.ghast"));
            default_world.getCreature_map().put("giant", config.getBoolean("Default.creature.giant"));
            default_world.getCreature_map().put("iron_golem", config.getBoolean("Default.creature.iron golem"));
            default_world.getCreature_map().put("magma_cube", config.getBoolean("Default.creature.magma cube"));
            default_world.getCreature_map().put("mushroom_cube", config.getBoolean("Default.creature.mushroom cube"));
            default_world.getCreature_map().put("ocelot", config.getBoolean("Default.creature.ocelot"));
            default_world.getCreature_map().put("pig", config.getBoolean("Default.creature.pig"));
            default_world.getCreature_map().put("pig zombie", config.getBoolean("Default.creature.pig zombie"));
            default_world.getCreature_map().put("sheep", config.getBoolean("Default.creature.sheep"));
            default_world.getCreature_map().put("silverfish", config.getBoolean("Default.creature.silverfish"));
            default_world.getCreature_map().put("skeleton", config.getBoolean("Default.creature.skeleton"));
            default_world.getCreature_map().put("slime", config.getBoolean("Default.creature.slime"));
            default_world.getCreature_map().put("snowman", config.getBoolean("Default.creature.snowman"));
            default_world.getCreature_map().put("spider", config.getBoolean("Default.creature.spider"));
            default_world.getCreature_map().put("squid", config.getBoolean("Default.creature.squid"));
            default_world.getCreature_map().put("villager", config.getBoolean("Default.creature.villager"));
            default_world.getCreature_map().put("witch", config.getBoolean("Default.creature.witch"));
            default_world.getCreature_map().put("wither", config.getBoolean("Default.creature.wither"));
            default_world.getCreature_map().put("wolf", config.getBoolean("Default.creature.wolf"));
            default_world.getCreature_map().put("zombie", config.getBoolean("Default.creature.zombie"));

            default_world.setZombie_villager(config.getBoolean("Default.creature.zombie villager"));

            worlds.put("Default", default_world);
        }
        if (multi) {
            Iterator<World> iter = plugin.getServer().getWorlds().iterator();
            while (iter.hasNext()) {
                String world = iter.next().getName();
                if (config.getBoolean(world + ".enabled")) {
                    AMSWorldConfig temp_world = new AMSWorldConfig(world);

                    temp_world.setZone(config.getBoolean(world + ".zone"));
                    temp_world.setZone_type(config.getString(world + ".zone type"));
                    temp_world.setHeight_limit(config.getInt(world + ".height limit"));
                    temp_world.setHeight_limit_type(config.getString(world + ".height limit type"));
                    temp_world.setTime_start(config.getInt(world + ".time start"));
                    temp_world.setTime_end(config.getInt(world + ".time end"));
                    temp_world.setAll_blocks(config.getBoolean(world + ".all blocks"));
                    temp_world.setBlocks(config.getIntegerList(world + ".blocks"));

                    temp_world.getSpawn_map().put("breeding", config.getBoolean(world + ".spawn.Breeding"));
                    temp_world.getSpawn_map().put("build_irongolem", config.getBoolean(world + ".spawn.Build Iron Golem"));
                    temp_world.getSpawn_map().put("build_snowman", config.getBoolean(world + ".spawn.Build Snowman"));
                    temp_world.getSpawn_map().put("build_wither", config.getBoolean(world + ".spawn.Build Wither"));
                    temp_world.getSpawn_map().put("chunk_gen", config.getBoolean(world + ".spawn.Chunk Generation"));
                    temp_world.getSpawn_map().put("custom", config.getBoolean(world + ".spawn.Custom"));
                    temp_world.getSpawn_map().put("egg", config.getBoolean(world + ".spawn.Egg"));
                    temp_world.getSpawn_map().put("jockey", config.getBoolean(world + ".spawn.Jockey"));
                    temp_world.getSpawn_map().put("lightning", config.getBoolean(world + ".spawn.Lightning"));
                    temp_world.getSpawn_map().put("natural", config.getBoolean(world + ".spawn.Natural"));
                    temp_world.getSpawn_map().put("slime_split", config.getBoolean(world + ".spawn.Slime Split"));
                    temp_world.getSpawn_map().put("spawner", config.getBoolean(world + ".spawn.Spawner"));
                    temp_world.getSpawn_map().put("spawner_egg", config.getBoolean(world + ".spawn.Spawn Egg"));
                    temp_world.getSpawn_map().put("village_defense", config.getBoolean(world + ".spawn.Village Defense"));
                    temp_world.getSpawn_map().put("village_invasion", config.getBoolean(world + ".spawn.Village Invasion"));

                    temp_world.getCreature_map().put("bat", config.getBoolean(world + ".creature.bat"));
                    temp_world.getCreature_map().put("blaze", config.getBoolean(world + ".creature.blaze"));
                    temp_world.getCreature_map().put("cave_spider", config.getBoolean(world + ".creature.cave spider"));
                    temp_world.getCreature_map().put("chicken", config.getBoolean(world + ".creature.chicken"));
                    temp_world.getCreature_map().put("cow", config.getBoolean(world + ".creature.cow"));
                    temp_world.getCreature_map().put("creeper", config.getBoolean(world + ".creature.creeper"));
                    temp_world.getCreature_map().put("ender_dragon", config.getBoolean(world + ".creature.ender dragon"));
                    temp_world.getCreature_map().put("enderman", config.getBoolean(world + ".creature.enderman"));
                    temp_world.getCreature_map().put("ghast", config.getBoolean(world + ".creature.ghast"));
                    temp_world.getCreature_map().put("giant", config.getBoolean(world + ".creature.giant"));
                    temp_world.getCreature_map().put("iron_golem", config.getBoolean(world + ".creature.iron golem"));
                    temp_world.getCreature_map().put("magma_cube", config.getBoolean(world + ".creature.magma cube"));
                    temp_world.getCreature_map().put("mushroom_cube", config.getBoolean(world + ".creature.mushroom cube"));
                    temp_world.getCreature_map().put("ocelot", config.getBoolean(world + ".creature.ocelot"));
                    temp_world.getCreature_map().put("pig", config.getBoolean(world + ".creature.pig"));
                    temp_world.getCreature_map().put("pig zombie", config.getBoolean(world + ".creature.pig zombie"));
                    temp_world.getCreature_map().put("sheep", config.getBoolean(world + ".creature.sheep"));
                    temp_world.getCreature_map().put("silverfish", config.getBoolean(world + ".creature.silverfish"));
                    temp_world.getCreature_map().put("skeleton", config.getBoolean(world + ".creature.skeleton"));
                    temp_world.getCreature_map().put("slime", config.getBoolean(world + ".creature.slime"));
                    temp_world.getCreature_map().put("snowman", config.getBoolean(world + ".creature.snowman"));
                    temp_world.getCreature_map().put("spider", config.getBoolean(world + ".creature.spider"));
                    temp_world.getCreature_map().put("squid", config.getBoolean(world + ".creature.squid"));
                    temp_world.getCreature_map().put("villager", config.getBoolean(world + ".creature.villager"));
                    temp_world.getCreature_map().put("witch", config.getBoolean(world + ".creature.witch"));
                    temp_world.getCreature_map().put("wither", config.getBoolean(world + ".creature.wither"));
                    temp_world.getCreature_map().put("wolf", config.getBoolean(world + ".creature.wolf"));
                    temp_world.getCreature_map().put("zombie", config.getBoolean(world + ".creature.zombie"));

                    temp_world.setZombie_villager(config.getBoolean(world + ".creature.zombie villager"));

                    worlds.put(world, temp_world);
                }
            }
        }
    }

    private void setupFile() {
        if (multi) {
            Iterator<World> iter = plugin.getServer().getWorlds().iterator();
            while (iter.hasNext()) {
                String world = iter.next().getName();
                String[] block = { "18", "46" };

                if (!config.contains(world + ".enabled"))
                    config.set(world + ".enabled", true);
                if (!config.contains(world + ".zone"))
                    config.set(world + ".zone", true);
                if (!config.contains(world + ".zone type"))
                    config.set(world + ".zone type", "safe");
                if (!config.contains(world + ".height limit"))
                    config.set(world + ".height limit", "62");
                if (!config.contains(world + ".height limit type"))
                    config.set(world + ".height limit type", "above");
                if (!config.contains(world + ".time start"))
                    config.set(world + ".time start", "0");
                if (!config.contains(world + ".time end"))
                    config.set(world + ".time end", "24000");
                if (!config.contains(world + ".all blocks"))
                    config.set(world + ".all blocks", false);
                if (!config.contains(world + ".blocks"))
                    config.set(world + ".blocks", Arrays.asList(block));

                if (!config.contains(world + ".spawn.Breeding"))
                    config.set(world + ".spawn.Breeding", false);
                if (!config.contains(world + ".spawn.Build Iron Golem"))
                    config.set(world + ".spawn.Build Iron Golem", false);
                if (!config.contains(world + ".spawn.Build Wither"))
                    config.set(world + ".spawn.Build Wither", false);
                if (!config.contains(world + ".spawn.Chunk Generation"))
                    config.set(world + ".spawn.Chunk Generation", true);
                if (!config.contains(world + ".spawn.Custom"))
                    config.set(world + ".spawn.Custom", false);
                if (!config.contains(world + ".spawn.Egg"))
                    config.set(world + ".spawn.Egg", true);
                if (!config.contains(world + ".spawn.Jockey"))
                    config.set(world + ".spawn.Jockey", true);
                if (!config.contains(world + ".spawn.Lightning"))
                    config.set(world + ".spawn.Lightning", true);
                if (!config.contains(world + ".spawn.Natural"))
                    config.set(world + ".spawn.Natural", true);
                if (!config.contains(world + ".spawn.Slime Split"))
                    config.set(world + ".spawn.Slime Split", true);
                if (!config.contains(world + ".spawn.Spawner"))
                    config.set(world + ".spawn.Spawner", true);
                if (!config.contains(world + ".spawn.Spawn Egg"))
                    config.set(world + ".spawn.Spawn Egg", true);
                if (!config.contains(world + ".spawn.Village Defense"))
                    config.set(world + ".spawn.Village Defense", false);
                if (!config.contains(world + ".spawn.Village Invasion"))
                    config.set(world + ".spawn.Village Invasion", true);

                if (!config.contains(world + ".creature.bat"))
                    config.set(world + ".creature.bat", true);
                if (!config.contains(world + ".creature.blaze"))
                    config.set(world + ".creature.blaze", true);
                if (!config.contains(world + ".creature.cave spider"))
                    config.set(world + ".creature.cave spider", true);
                if (!config.contains(world + ".creature.chicken"))
                    config.set(world + ".creature.chicken", false);
                if (!config.contains(world + ".creature.creeper"))
                    config.set(world + ".creature.creeper", true);
                if (!config.contains(world + ".creature.ender dragon"))
                    config.set(world + ".creature.ender dragon", true);
                if (!config.contains(world + ".creature.enderman"))
                    config.set(world + ".creature.enderman", true);
                if (!config.contains(world + ".creature.ghast"))
                    config.set(world + ".creature.ghast", true);
                if (!config.contains(world + ".creature.giant"))
                    config.set(world + ".creature.giant", true);
                if (!config.contains(world + ".creature.iron golem"))
                    config.set(world + ".creature.iron golem", false);
                if (!config.contains(world + ".creature.magma cube"))
                    config.set(world + ".creature.magma cube", true);
                if (!config.contains(world + ".creature.mushroom cow"))
                    config.set(world + ".creature.mushroom cow", false);
                if (!config.contains(world + ".creature.ocelot"))
                    config.set(world + ".creature.ocelot", false);
                if (!config.contains(world + ".creature.pig"))
                    config.set(world + ".creature.pig", false);
                if (!config.contains(world + ".creature.pig zombie"))
                    config.set(world + ".creature.pig zombie", true);
                if (!config.contains(world + ".creature.sheep"))
                    config.set(world + ".creature.sheep", false);
                if (!config.contains(world + ".creature.silverfish"))
                    config.set(world + ".creature.silverfish", false);
                if (!config.contains(world + ".creature.skeleton"))
                    config.set(world + ".creature.skeleton", true);
                if (!config.contains(world + ".creature.slime"))
                    config.set(world + ".creature.slime", true);
                if (!config.contains(world + ".creature.snowman"))
                    config.set(world + ".creature.snowman", false);
                if (!config.contains(world + ".creature.spider"))
                    config.set(world + ".creature.spider", true);
                if (!config.contains(world + ".creature.squid"))
                    config.set(world + ".creature.squid", false);
                if (!config.contains(world + ".creature.villager"))
                    config.set(world + ".creature.villager", false);
                if (!config.contains(world + ".creature.witch"))
                    config.set(world + ".creature.witch", false);
                if (!config.contains(world + ".creature.wither"))
                    config.set(world + ".creature.wither", true);
                if (!config.contains(world + ".creature.wolf"))
                    config.set(world + ".creature.wolf", false);
                if (!config.contains(world + ".creature.zombie"))
                    config.set(world + ".creature.zombie", true);
                if (!config.contains(world + ".creature.zombie villager"))
                    config.set(world + ".creature.zombie villager", true);
            }
        }
        plugin.saveConfig();
    }

    public HashMap<String, AMSWorldConfig> getWorlds() {
        return worlds;
    }
}
