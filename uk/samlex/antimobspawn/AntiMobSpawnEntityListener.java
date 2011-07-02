/*
 * This file is part of the Bukkit plugin AntiMobSpawn
 * 
 * Copyright (C) 2011 <sam_lex@gmx.com>
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

import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityListener;

/*
 * Part of AntiMobSpawn
 * Made by Sam_Lex, 2011
 */

public class AntiMobSpawnEntityListener extends EntityListener {

	//stores data on the event
	private CreatureSpawnEvent event;
	private final String defaultValue = "default";
	private final String defaultFileValue = "defaultFile";

	//code to be executed on creature spawn 
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event){

		this.event = event;

		//begin the checking
		checker();
	}

	//checks if the mob should be stopped or not 
	public void checker() {

		//checks if the spawn is naturally caused
		if(event.getSpawnReason() == SpawnReason.NATURAL){

			//checks if there has been an exception while creating, writing or reading the properties file
			if(AntiMobSpawnVariables.isUseDefault()){
				defaultSpawn();
				return;
			}else 

				//checks if the world the event is on is in the file or if the user has set just to use defaults
				if(!AntiMobSpawnVariables.getAll().containsKey(event.getLocation().getWorld().getName()) || !AntiMobSpawnVariables.isMulti()) {
					defaultFileSpawn();
					return;
				}else 

					//checks that the world the event is on is in the file, just to be sure
					if(AntiMobSpawnVariables.getAll().containsKey(event.getLocation().getWorld().getName())){
						worldSpawn();
						return;
					}
		}
	}

	//used if there was a problem with the file
	private void defaultSpawn() {

		//switch for determining whether the creature is to be stop or not
		switch(event.getCreatureType()){

		case CHICKEN:
			if(!AntiMobSpawnVariables.getChicken().get(defaultValue)){
				return;
			}else{
				break;
			}

		case COW:
			if(!AntiMobSpawnVariables.getCow().get(defaultValue)){
				return;
			}else{
				break;
			}

		case CREEPER:
			if(!AntiMobSpawnVariables.getCreeper().get(defaultValue)){
				return;
			}else{
				break;
			}

		case GHAST:
			if(!AntiMobSpawnVariables.getGhast().get(defaultValue)){
				return;
			}else{
				break;
			}

		case GIANT:
			if(!AntiMobSpawnVariables.getGiant().get(defaultValue)){
				return;
			}else{
				break;
			}

		case MONSTER:
			if(!AntiMobSpawnVariables.getMonster().get(defaultValue)){
				return;
			}else{
				break;
			}

		case PIG:
			if(!AntiMobSpawnVariables.getPig().get(defaultValue)){
				return;
			}else{
				break;
			}

		case PIG_ZOMBIE:
			if(!AntiMobSpawnVariables.getPig_zombie().get(defaultValue)){
				return;
			}else{
				break;
			}

		case SHEEP:
			if(!AntiMobSpawnVariables.getSheep().get(defaultValue)){
				return;
			}else{
				break;
			}

		case SKELETON:
			if(!AntiMobSpawnVariables.getSkeleton().get(defaultValue)){
				return;
			}else{
				break;
			}

		case SLIME:
			if(!AntiMobSpawnVariables.getSlime().get(defaultValue)){
				return;
			}else{
				break;
			}

		case SPIDER:
			if(!AntiMobSpawnVariables.getSpider().get(defaultValue)){
				return;
			}else{
				break;
			}

		case SQUID:
			if(!AntiMobSpawnVariables.getSquid().get(defaultValue)){
				return;
			}else{
				break;
			}

		case WOLF:
			if(!AntiMobSpawnVariables.getWolf().get(defaultValue)){
				return;
			}else{
				break;
			}

		case ZOMBIE:
			if(!AntiMobSpawnVariables.getZombie().get(defaultValue)){
				return;
			}else{
				break;
			}
		}


		//cancels the event if all is set to true
		if(AntiMobSpawnVariables.getAll().get(defaultValue)){
			event.setCancelled(true);
			return;
		}

		//checks if the mob is spawning on a block that is not to be spawned on
		for (Integer n : AntiMobSpawnVariables.getBlocks().get(defaultValue)) {
			if(event.getLocation().getBlock().getRelative(BlockFace.DOWN).getTypeId() == n){
				event.setCancelled(true);
			}
		}
	}

	//used if the world the event is on is not is the file or multi is set to false
	private void defaultFileSpawn(){

		//switch for determining whether the creature is to be stop or not
		switch(event.getCreatureType()){

		case CHICKEN:
			if(!AntiMobSpawnVariables.getChicken().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case COW:
			if(!AntiMobSpawnVariables.getCow().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case CREEPER:
			if(!AntiMobSpawnVariables.getCreeper().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case GHAST:
			if(!AntiMobSpawnVariables.getGhast().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case GIANT:
			if(!AntiMobSpawnVariables.getGiant().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case MONSTER:
			if(!AntiMobSpawnVariables.getMonster().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case PIG:
			if(!AntiMobSpawnVariables.getPig().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case PIG_ZOMBIE:
			if(!AntiMobSpawnVariables.getPig_zombie().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case SHEEP:
			if(!AntiMobSpawnVariables.getSheep().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case SKELETON:
			if(!AntiMobSpawnVariables.getSkeleton().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case SLIME:
			if(!AntiMobSpawnVariables.getSlime().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case SPIDER:
			if(!AntiMobSpawnVariables.getSpider().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case SQUID:
			if(!AntiMobSpawnVariables.getSquid().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case WOLF:
			if(!AntiMobSpawnVariables.getWolf().get(defaultFileValue)){
				return;
			}else{
				break;
			}

		case ZOMBIE:
			if(!AntiMobSpawnVariables.getZombie().get(defaultFileValue)){
				return;
			}else{
				break;
			}
		}

		//cancels the event if all is set to true
		if(AntiMobSpawnVariables.getAll().get(defaultFileValue)){
			event.setCancelled(true);
			return;
		}

		//checks if the mob is spawning on a block that is not to be spawned on
		for (Integer n : AntiMobSpawnVariables.getBlocks().get(defaultFileValue)) {
			if(event.getLocation().getBlock().getRelative(BlockFace.DOWN).getTypeId() == n){
				event.setCancelled(true);
			}
		}
	}

	//used if the world the event is on is in the file
	private void worldSpawn() {

		//switch for determining whether the creature is to be stop or not
		switch(event.getCreatureType()){

		case CHICKEN:
			if(!AntiMobSpawnVariables.getChicken().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case COW:
			if(!AntiMobSpawnVariables.getCow().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case CREEPER:
			if(!AntiMobSpawnVariables.getCreeper().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case GHAST:
			if(!AntiMobSpawnVariables.getGhast().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case GIANT:
			if(!AntiMobSpawnVariables.getGiant().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case MONSTER:
			if(!AntiMobSpawnVariables.getMonster().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case PIG:
			if(!AntiMobSpawnVariables.getPig().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case PIG_ZOMBIE:
			if(!AntiMobSpawnVariables.getPig_zombie().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case SHEEP:
			if(!AntiMobSpawnVariables.getSheep().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case SKELETON:
			if(!AntiMobSpawnVariables.getSkeleton().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case SLIME:
			if(!AntiMobSpawnVariables.getSlime().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case SPIDER:
			if(!AntiMobSpawnVariables.getSpider().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case SQUID:
			if(!AntiMobSpawnVariables.getSquid().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case WOLF:
			if(!AntiMobSpawnVariables.getWolf().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}

		case ZOMBIE:
			if(!AntiMobSpawnVariables.getZombie().get(event.getLocation().getWorld().getName())){
				return;
			}else{
				break;
			}
		}

		//cancels the event if all is set to true
		if(AntiMobSpawnVariables.getAll().get(event.getLocation().getWorld().getName())){
			event.setCancelled(true);
			return;
		}

		//checks if the mob is spawning on a block that is not to be spawned on
		for (Integer n : AntiMobSpawnVariables.getBlocks().get(event.getLocation().getWorld().getName())) {
			if(event.getLocation().getBlock().getRelative(BlockFace.DOWN).getTypeId() == n){
				event.setCancelled(true);
			}
		}
	}
}
