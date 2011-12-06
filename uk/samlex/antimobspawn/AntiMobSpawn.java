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
//TODO Let the testing begin
package uk.samlex.antimobspawn;

import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiMobSpawn extends JavaPlugin{

	private AntiMobSpawnEntityListener amsel;
	private AntiMobSpawnCommand amsc;

	/*
	 * Runs when the plugin is enabled. Instances of the entity listener and the command handler are created
	 * and then registered. A ready message is then printed
	 */
	@Override
	public void onEnable(){
		amsel = new AntiMobSpawnEntityListener(this);
		amsc = new AntiMobSpawnCommand(this);

		this.config();

		this.getServer().getPluginManager().registerEvent(Event.Type.CREATURE_SPAWN, amsel, Event.Priority.High, this);
		this.getCommand("ams").setExecutor(amsc);

		this.print("Enabled. Ready to stop those pesky creepers");
	}

	/*
	 * Runs just as the plugin is disabled. Saves any changes to the configuration file to disk and then prints a goodbye message
	 */
	@Override
	public void onDisable(){
		this.saveConfig();
		this.print("Disabled. Goodbye");
		amsel = null;
		amsc = null;
	}

	/*
	 * Provides an easy function to do the printing 
	 */
	private void print(String message){
		Logger.getLogger("Minecraft").info('[' + getDescription().getFullName() + "] " + message);
		return;
	}

	/*
	 * Checks that all the worlds loaded are in the configuration file and if not they are added. The configuration file is then saved to disk
	 */
	private void config(){
		this.getConfig().options().copyDefaults(true);

		Iterator<World> iter = this.getServer().getWorlds().iterator();
		while(iter.hasNext()){
			String w = iter.next().getName();
			String[] block = {"18","46"};
			if(!this.getConfig().contains(w+".all blocks"))
				this.getConfig().set(w+".all blocks", false);
			if(!this.getConfig().contains(w+".blocks"))
				this.getConfig().set(w+".blocks", Arrays.asList(block));
			if(!this.getConfig().contains(w+".spawn.Bed"))
				this.getConfig().set(w+".spawn.Bed", true);
			if(!this.getConfig().contains(w+".spawn.Custom"))
				this.getConfig().set(w+".spawn.Custom", false);
			if(!this.getConfig().contains(w+".spawn.Egg"))
				this.getConfig().set(w+".spawn.Egg", true);
			if(!this.getConfig().contains(w+".spawn.Lightning"))
				this.getConfig().set(w+".spawn.Lightning", true);
			if(!this.getConfig().contains(w+".spawn.Natural"))
				this.getConfig().set(w+".spawn.Natural", true);
			if(!this.getConfig().contains(w+".spawn.Spawner"))
				this.getConfig().set(w+".spawn.Spawner", true);
			if(!this.getConfig().contains(w+".creature.blaze"))
				this.getConfig().set(w+".creature.blaze", true);
			if(!this.getConfig().contains(w+".creature.cave spider"))
				this.getConfig().set(w+".creature.cave spider", true);
			if(!this.getConfig().contains(w+".creature.chicken"))
				this.getConfig().set(w+".creature.chicken", false);
			if(!this.getConfig().contains(w+".creature.cow"))
				this.getConfig().set(w+".creature.cow", false);
			if(!this.getConfig().contains(w+".creature.creeper"))
				this.getConfig().set(w+".creature.creeper", true);
			if(!this.getConfig().contains(w+".creature.ender dragon"))
				this.getConfig().set(w+".creature.ender dragon", true);
			if(!this.getConfig().contains(w+".creature.enderman"))
				this.getConfig().set(w+".creature.enderman", true);
			if(!this.getConfig().contains(w+".creature.ghast"))
				this.getConfig().set(w+".creature.ghast", true);
			if(!this.getConfig().contains(w+".creature.giant"))
				this.getConfig().set(w+".creature.giant", true);
			if(!this.getConfig().contains(w+".creature.monster"))
				this.getConfig().set(w+".creature.monster", true);
			if(!this.getConfig().contains(w+".creature.muchroom cow"))
				this.getConfig().set(w+".creature.muchroom cow", false);
			if(!this.getConfig().contains(w+".creature.pig"))
				this.getConfig().set(w+".creature.pig", false);
			if(!this.getConfig().contains(w+".creature.pig zombie"))
				this.getConfig().set(w+".creature.pig zombie", true);
			if(!this.getConfig().contains(w+".creature.sheep"))
				this.getConfig().set(w+".creature.sheep", false);
			if(!this.getConfig().contains(w+".creature.silverfish"))
				this.getConfig().set(w+".creature.silverfish", false);
			if(!this.getConfig().contains(w+".creature.skeleton"))
				this.getConfig().set(w+".creature.skeleton", true);
			if(!this.getConfig().contains(w+".creature.slime"))
				this.getConfig().set(w+".creature.slime", true);
			if(!this.getConfig().contains(w+".creature.spider"))
				this.getConfig().set(w+".creature.spider", true);
			if(!this.getConfig().contains(w+".creature.squid"))
				this.getConfig().set(w+".creature.squid", false);
			if(!this.getConfig().contains(w+".creature.villager"))
				this.getConfig().set(w+".creature.villager", false);
			if(!this.getConfig().contains(w+".creature.wolf"))
				this.getConfig().set(w+".creature.wolf", false);
			if(!this.getConfig().contains(w+".creature.zombie"))
				this.getConfig().set(w+".creature.zombie", true);
			w=null;
			block=null;
		}
		iter=null;
		this.saveConfig();
		return;
	}
}
