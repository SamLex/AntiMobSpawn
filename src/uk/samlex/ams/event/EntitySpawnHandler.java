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

package uk.samlex.ams.event;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.samlex.ams.AntiMobSpawn;
import uk.samlex.ams.config.ConfigStore;
import uk.samlex.ams.config.WorldConfig;
import uk.samlex.bukkitcommon.config.WorldZone;

public class EntitySpawnHandler implements Listener {

    private void commonConditions(CreatureSpawnEvent cse, WorldConfig worldConfig) {
        if (cse.getLocation().getWorld().getTime() < worldConfig.getSafeTimeStart() || cse.getLocation().getWorld().getTime() > worldConfig.getSafeTimeEnd()) {
            return;
        }

        if (!worldConfig.isAllSpawnReasons()) {
            if (!worldConfig.getSpawnReasonMap().containsKey(cse.getSpawnReason()) || !worldConfig.getSpawnReasonMap().get(cse.getSpawnReason())) {
                return;
            }
        }

        if (!worldConfig.isAllCreatures()) {
            if (!worldConfig.getEntityMap().containsKey(cse.getEntityType()) || !worldConfig.getEntityMap().get(cse.getEntityType())) {
                return;
            }
        }

        if (!worldConfig.isAllBlocks()) {
            Material mat = cse.getLocation().subtract(0, 1, 0).getBlock().getType();

            if (!worldConfig.getBlockMap().containsKey(mat) || !worldConfig.getBlockMap().get(mat)) {
                return;
            }
        }

        cse.setCancelled(true);

        if (ConfigStore.instance().isDebug()) {
            AntiMobSpawn.instance().getLogger().info(String.format("Stopped spawn of %s at [%s], with reason %s, on block %s", cse.getEntityType().toString(), cse.getLocation().toVector().toString(), cse.getSpawnReason().toString(), cse.getLocation().subtract(0, 1, 0).getBlock().getType().toString()));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntitySpawn(CreatureSpawnEvent cse) {
        WorldConfig worldConfig = ConfigStore.instance().getWorldConfigMap().get(cse.getLocation().getWorld().getName());

        if (worldConfig == null)
            return;

        switch (worldConfig.getWorldMode()) {
            case GLOBAL:
                worldModeGlobal(cse, worldConfig);
                break;
            case SAFE:
                worldModeZone(cse, worldConfig, true);
                break;
            case UNSAFE:
                worldModeZone(cse, worldConfig, false);
                break;
            case NONE:
                return;
        }
    }

    private void worldModeGlobal(CreatureSpawnEvent cse, WorldConfig worldConfig) {
        switch (worldConfig.getHeightLimitMode()) {
            case ABOVE:
                if (cse.getLocation().getY() < worldConfig.getHeightLimit()) {
                    return;
                }
                break;
            case BELOW:
                if (cse.getLocation().getY() > worldConfig.getHeightLimit()) {
                    return;
                }
                break;
        }

        commonConditions(cse, worldConfig);
    }

    private void worldModeZone(CreatureSpawnEvent cse, WorldConfig worldConfig, boolean safe) {
        List<WorldZone> zoneList = AntiMobSpawn.instance().getDatabase().find(WorldZone.class).where().ieq("worldName", cse.getLocation().getWorld().getName()).findList();
        WorldZone zone = null;

        for (WorldZone test : zoneList) {
            if (WorldZone.getBoundingBox(test).withinBoundingBox(cse.getLocation().toVector())) {
                zone = test;
                break;
            }
        }

        if (safe) {
            if (zone == null) {
                return;
            }
        } else {
            if (zone != null) {
                return;
            }
        }

        commonConditions(cse, worldConfig);
    }
}
