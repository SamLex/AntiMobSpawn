/*
 * This file is part of the Bukkit plugin AntiMobSpawn
 * 
 * Copyright (C) 2011 <euan_hunt@hotmail.co.uk>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.samlex.antimobspawn;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.entity.CreatureType; 

/**
 * AntiMobSpawn block listener
 * @author Sam_Lex
 */

public class AntiMobSpawnEntityListener extends EntityListener {

	//plugin variable
	public static AntiMobSpawn plugin;

	//plugin variable is an instance of plugin
	public AntiMobSpawnEntityListener(AntiMobSpawn instance){
		plugin = instance;
	}

	//mob spawn event catcher
	public void onCreatureSpawn(CreatureSpawnEvent event) {

		//checks if it is a neutral mob
		if (event.getCreatureType() != CreatureType.CHICKEN && event.getCreatureType() != CreatureType.COW && event.getCreatureType() != CreatureType.PIG 
				&& event.getCreatureType() != CreatureType.SHEEP && event.getCreatureType() != CreatureType.WOLF){

			//checks if the block it is spawning on is in the list
			for (int block : AntiMobSpawnDisk.getStopSpawnBlocks()) {


				if (event.getLocation().getBlock().getRelative(0,-1,0).getTypeId() == block){

					//if it is, stop the spawn
					event.setCancelled(true); 

					//java.lang.System.out.println("Stopping Mob..."); //debug print
				}
			}
		}
	}
}

