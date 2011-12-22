/*
 * AntiMobSpawn, a plugin for the Minecraft server modification Bukkit. Provides control of in game creature spawns
 * 
 * Copyright (C) 2011 Euan James Hunter <euanhunter117@gmail.com>
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

import java.util.Iterator;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.Plugin;

public class AntiMobSpawnEntityListener extends EntityListener{

	private Plugin inst;

	/*
	 * Constructor that is passed the instance of the plugin
	 */
	protected AntiMobSpawnEntityListener(Plugin plugin){
		this.inst = plugin;
	}

	/*
	 * Handles the creature spawns. Runs through all the criteria set in the configuration file using a number of different methods*/
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent cse){
		if(this.inst.getConfig().getBoolean("multi")){
			if(this.inst.getConfig().contains(cse.getLocation().getWorld().getName())){
				String w = cse.getLocation().getWorld().getName();
				if(this.inst.getConfig().getBoolean(w+".enabled")){
					if(cse.getLocation().getY()<=this.inst.getConfig().getDouble(w+".height limit")){
						if(cse.getLocation().getWorld().getTime()>=this.inst.getConfig().getInt(w+".time start") && 
								cse.getLocation().getWorld().getTime()<=this.inst.getConfig().getInt(w+".time stop")){
							switch(cse.getSpawnReason()){
							case BED:
								if(this.inst.getConfig().getBoolean(w+".spawn.Bed"))
									checker(w,cse);
								break;
							case CUSTOM:
								if(this.inst.getConfig().getBoolean(w+".spawn.Custom"))
									checker(w,cse);
								break;
							case EGG:
								if(this.inst.getConfig().getBoolean(w+".spawn.Egg"))
									checker(w,cse);
								break;
							case LIGHTNING:
								if(this.inst.getConfig().getBoolean(w+".spawn.Lightning"))
									checker(w,cse);
								break;
							case NATURAL:
								if(this.inst.getConfig().getBoolean(w+".spawn.Natural"))
									checker(w,cse);
								break;
							case SPAWNER:
								if(this.inst.getConfig().getBoolean(w+".spawn.Spawner"))
									checker(w,cse);
								break;
							}
						}
					}
				}
			}else
				defaultWorld(cse);
		}else
			defaultWorld(cse);
	}

	private void defaultWorld(CreatureSpawnEvent cse){
		String w = "Default";
		if(this.inst.getConfig().getBoolean(w+".enabled")){
			if(cse.getLocation().getY()<=this.inst.getConfig().getDouble(w+".height limit")){
				if(cse.getLocation().getWorld().getTime()>=this.inst.getConfig().getInt(w+".time start") && 
						cse.getLocation().getWorld().getTime()<=this.inst.getConfig().getInt(w+".time stop")){
					switch(cse.getSpawnReason()){
					case BED:
						if(this.inst.getConfig().getBoolean(w+".spawn.Bed"))
							checker(w,cse);
						break;
					case CUSTOM:
						if(this.inst.getConfig().getBoolean(w+".spawn.Custom"))
							checker(w,cse);
						break;
					case EGG:
						if(this.inst.getConfig().getBoolean(w+".spawn.Egg"))
							checker(w,cse);
						break;
					case LIGHTNING:
						if(this.inst.getConfig().getBoolean(w+".spawn.Lightning"))
							checker(w,cse);
						break;
					case NATURAL:
						if(this.inst.getConfig().getBoolean(w+".spawn.Natural"))
							checker(w,cse);
						break;
					case SPAWNER:
						if(this.inst.getConfig().getBoolean(w+".spawn.Spawner"))
							checker(w,cse);
						break;
					}
				}
			}
		}
		return;
	}

	private void checker(String w,CreatureSpawnEvent cse){
		if(!this.inst.getConfig().getBoolean(w+".all blocks")){
			if(blockChecker(w,cse)){
				creatureChecker(w, cse);
			}
		}else{
			creatureChecker(w, cse);
		}
	}

	private boolean blockChecker(String w,CreatureSpawnEvent cse){
		@SuppressWarnings("unchecked")
		Iterator<String> i = this.inst.getConfig().getList(w+".blocks").iterator();
		while(i.hasNext()){
			if(cse.getLocation().getBlock().getRelative(0,-1,0).getTypeId()==Integer.parseInt(i.next()))
				return true;
		}
		return false;
	}

	private void creatureChecker(String w,CreatureSpawnEvent cse){
		switch(cse.getCreatureType()){
		case BLAZE:
			if(this.inst.getConfig().getBoolean(w+".creature.blaze"))
				cse.setCancelled(true);
			break;
		case CAVE_SPIDER:
			if(this.inst.getConfig().getBoolean(w+".creature.cave spider"))
				cse.setCancelled(true);
			break;
		case CHICKEN:
			if(this.inst.getConfig().getBoolean(w+".creature.chicken"))
				cse.setCancelled(true);
			break;
		case COW:
			if(this.inst.getConfig().getBoolean(w+".creature.cow"))
				cse.setCancelled(true);
			break;
		case CREEPER:
			if(this.inst.getConfig().getBoolean(w+".creature.creeper"))
				cse.setCancelled(true);
			break;
		case ENDER_DRAGON:
			if(this.inst.getConfig().getBoolean(w+".creature.ender dragon"))
				cse.setCancelled(true);
			break;
		case ENDERMAN:
			if(this.inst.getConfig().getBoolean(w+".creature.enderman"))
				cse.setCancelled(true);
			break;
		case GHAST:
			if(this.inst.getConfig().getBoolean(w+".creature.ghast"))
				cse.setCancelled(true);
			break;
		case GIANT:
			if(this.inst.getConfig().getBoolean(w+".creature.giant"))
				cse.setCancelled(true);
			break;
		case MAGMA_CUBE:
			if(this.inst.getConfig().getBoolean(w+".creature.magma cube"))
				cse.setCancelled(true);
			break;
		case MONSTER:
			if(this.inst.getConfig().getBoolean(w+".creature.monster"))
				cse.setCancelled(true);
			break;
		case MUSHROOM_COW:
			if(this.inst.getConfig().getBoolean(w+".creature.mushroom cow"))
				cse.setCancelled(true);
			break;
		case PIG:
			if(this.inst.getConfig().getBoolean(w+".creature.pig"))
				cse.setCancelled(true);
			break;
		case PIG_ZOMBIE:
			if(this.inst.getConfig().getBoolean(w+".creature.pig zombie"))
				cse.setCancelled(true);
			break;
		case SHEEP:
			if(this.inst.getConfig().getBoolean(w+".creature.sheep"))
				cse.setCancelled(true);
			break;
		case SILVERFISH:
			if(this.inst.getConfig().getBoolean(w+".creature.silverfish"))
				cse.setCancelled(true);
			break;
		case SKELETON:
			if(this.inst.getConfig().getBoolean(w+".creature.skeleton"))
				cse.setCancelled(true);
			break;
		case SLIME:
			if(this.inst.getConfig().getBoolean(w+".creature.slime"))
				cse.setCancelled(true);
			break;
		case SNOWMAN:
			if(this.inst.getConfig().getBoolean(w+".creature.snowman"))
				cse.setCancelled(true);
			break;
		case SPIDER:
			if(this.inst.getConfig().getBoolean(w+".creature.spider"))
				cse.setCancelled(true);
			break;
		case SQUID:
			if(this.inst.getConfig().getBoolean(w+".creature.squid"))
				cse.setCancelled(true);
			break;
		case VILLAGER:
			if(this.inst.getConfig().getBoolean(w+".creature.villager"))
				cse.setCancelled(true);
			break;
		case WOLF:
			if(this.inst.getConfig().getBoolean(w+".creature.wolf"))
				cse.setCancelled(true);
			break;
		case ZOMBIE:
			if(this.inst.getConfig().getBoolean(w+".creature.zombie"))
				cse.setCancelled(true);
			break;
		}

		return;
	}
}
