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

import java.util.List;
import java.util.HashMap;

public class AMSWorldConfig {

    private String world_name;

    private boolean zone;
    private String zone_type;
    private int height_limit;
    private String height_limit_type;
    private int time_start;
    private int time_end;
    private boolean all_blocks;
    private List<Integer> blocks;

    private HashMap<String, Boolean> spawn_map;
    private HashMap<String, Boolean> creature_map;
    private boolean zombie_villager;

    public AMSWorldConfig(String world_name) {
        this.world_name = world_name;

        spawn_map = new HashMap<>();
        spawn_map.put("breeding", false);
        spawn_map.put("build_irongolem", false);
        spawn_map.put("build_snowman", false);
        spawn_map.put("build_wither", false);
        spawn_map.put("chunk_gen", true);
        spawn_map.put("custom", false);
        spawn_map.put("egg", true);
        spawn_map.put("jockey", true);
        spawn_map.put("lightning", true);
        spawn_map.put("natural", true);
        spawn_map.put("slime_split", true);
        spawn_map.put("spawner", true);
        spawn_map.put("spawn_egg", true);
        spawn_map.put("village_defense", false);
        spawn_map.put("village_invasion", true);

        creature_map = new HashMap<>();
        creature_map.put("bat", true);
        creature_map.put("blaze", true);
        creature_map.put("cave_spider", true);
        creature_map.put("chicken", false);
        creature_map.put("cow", false);
        creature_map.put("creeper", true);
        creature_map.put("ender_dragon", true);
        creature_map.put("enderman", true);
        creature_map.put("ghast", true);
        creature_map.put("giant", true);
        creature_map.put("iron_golem", false);
        creature_map.put("magma_cube", true);
        creature_map.put("mushroom_cow", false);
        creature_map.put("ocelot", false);
        creature_map.put("pig", false);
        creature_map.put("pig_zombie", true);
        creature_map.put("sheep", false);
        creature_map.put("silverfish", false);
        creature_map.put("skeleton", true);
        creature_map.put("slime", true);
        creature_map.put("snowman", false);
        creature_map.put("spider", true);
        creature_map.put("squid", false);
        creature_map.put("villager", false);
        creature_map.put("witch", false);
        creature_map.put("wither", true);
        creature_map.put("wolf", false);
        creature_map.put("zombie", true);
    }

    public void addBlock(int block) {
        blocks.add(block);
    }

    public String getWorld_name() {
        return world_name;
    }

    public void setWorld_name(String world_name) {
        this.world_name = world_name;
    }

    public int getHeight_limit() {
        return height_limit;
    }

    public void setHeight_limit(int height_limit) {
        this.height_limit = height_limit;
    }

    public String getHeight_limit_type() {
        return height_limit_type;
    }

    public void setHeight_limit_type(String height_limit_type) {
        this.height_limit_type = height_limit_type;
    }

    public int getTime_start() {
        return time_start;
    }

    public void setTime_start(int time_start) {
        this.time_start = time_start;
    }

    public int getTime_end() {
        return time_end;
    }

    public void setTime_end(int time_end) {
        this.time_end = time_end;
    }

    public boolean isAll_blocks() {
        return all_blocks;
    }

    public void setAll_blocks(boolean all_blocks) {
        this.all_blocks = all_blocks;
    }

    public List<Integer> getBlocks() {
        return blocks;
    }

    public HashMap<String, Boolean> getSpawn_map() {
        return spawn_map;
    }

    public HashMap<String, Boolean> getCreature_map() {
        return creature_map;
    }

    public void setBlocks(List<Integer> blocks) {
        this.blocks = blocks;
    }

    public boolean isZombie_villager() {
        return zombie_villager;
    }

    public void setZombie_villager(boolean zombie_villager) {
        this.zombie_villager = zombie_villager;
    }

    public boolean isZone() {
        return zone;
    }

    public void setZone(boolean zone) {
        this.zone = zone;
    }

    public String getZone_type() {
        return zone_type;
    }

    public void setZone_type(String zone_type) {
        this.zone_type = zone_type;
    }
}
