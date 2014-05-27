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

package uk.samlex.ams.old;

import java.util.List;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.util.Vector;

public class AMSListener implements Listener {

    AntiMobSpawn plugin;

    public AMSListener(AntiMobSpawn plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        String world = e.getLocation().getWorld().getName();

        if (!plugin.getAMSConfig().getWorlds().containsKey(world))
            world = "Default";

        AMSWorldConfig config = plugin.getAMSConfig().getWorlds().get(world);

        // Null check for if no worlds are enabled
        if (config == null)
            return;

        // Checks if the spawn is in any of the zones in the database for the world the spawn is in
        if (config.isZone()) {
            List<AMSZone> zoneList = plugin.getDatabase().find(AMSZone.class).where().ieq("world_name", e.getLocation().getWorld().getName()).findList();
            boolean isInZone = false;
            for (AMSZone temp : zoneList) {
                Vector p1 = new Vector(temp.getVec_p1_x(), temp.getVec_p1_y(), temp.getVec_p1_z());
                Vector p2 = new Vector(temp.getVec_p2_x(), temp.getVec_p2_y(), temp.getVec_p2_z());
                if (inBB(e.getLocation().toVector().toBlockVector(), Vector.getMinimum(p1, p2), Vector.getMaximum(p1, p2))) {
                    isInZone = true;
                    break;
                }
            }
            if (isInZone && config.getZone_type().equalsIgnoreCase("unsafe"))
                return;
            if (!isInZone && config.getZone_type().equalsIgnoreCase("safe"))
                return;
        } else {
            // Checks the current time in the world the spawn occurred in to check set constraints
            if (e.getLocation().getWorld().getTime() <= config.getTime_start())
                return;
            if (e.getLocation().getWorld().getTime() >= config.getTime_end())
                return;

            // Check the height of the spawn against the set height constraints
            if (config.getHeight_limit_type().equalsIgnoreCase("above")) {
                if (e.getLocation().getBlockY() < config.getHeight_limit())
                    return;
            } else if (config.getHeight_limit_type().equalsIgnoreCase("below"))
                if (e.getLocation().getBlockY() > config.getHeight_limit())
                    return;

            // Checks the block the spawn occurred on against the set block constraints
            if (!config.isAll_blocks()) {
                boolean isOnBlock = false;
                for (Integer block : config.getBlocks()) {
                    if (e.getLocation().getBlock().getRelative(BlockFace.DOWN).getTypeId() == block) {
                        isOnBlock = true;
                        break;
                    }
                }
                if (!isOnBlock)
                    return;
            }
        }

        // Checks the creature which is trying to spawn against the set creature constraints
        if (e.getSpawnReason() != SpawnReason.DEFAULT)
            if (!config.getSpawn_map().get(e.getSpawnReason().toString().toLowerCase()))
                return;

        // Special handling for Zombies as to detect Zombie Villagers
        if (config.getCreature_map().containsKey(e.getEntityType().toString().toLowerCase()))
            if (e.getEntityType() == EntityType.ZOMBIE && ((Zombie) e.getEntity()).isVillager() && !config.isZombie_villager())
                return;
            else if (!config.getCreature_map().get(e.getEntityType().toString().toLowerCase()))
                return;

        e.setCancelled(true);
    }

    // Custom method to check if a vector is within a bounding box set by two vectors, min and max
    private boolean inBB(Vector test, Vector min, Vector max) {
        if (min.getBlockX() <= test.getBlockX() && test.getBlockX() <= max.getBlockX())
            if (min.getBlockY() <= test.getBlockY() && test.getBlockY() <= max.getBlockY())
                if (min.getBlockZ() <= test.getBlockZ() && test.getBlockZ() <= max.getBlockZ())
                    return true;
        return false;
    }
}
