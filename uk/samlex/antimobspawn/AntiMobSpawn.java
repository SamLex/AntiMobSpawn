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

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * AntiMobSpawn 
 * Made by Sam_Lex, 2011
 */

public class AntiMobSpawn extends JavaPlugin {

	//code to be executed when enabled
	@Override
	public void onEnable() {

		//constructs the prefix
		AntiMobSpawnVariables.prefix(this.getDescription());

		//gets a list of all the worlds on the server
		AntiMobSpawnVariables.setWorlds(this.getServer().getWorlds());

		//puts default values into the Hashmaps
		AntiMobSpawnVariables.fillMaps();

		//runs disk related code
		new AntiMobSpawnDisk(this);

		//creates an instance of the entity listener
		AntiMobSpawnEntityListener entityListener = new AntiMobSpawnEntityListener();

		//gets the plugin's plugin manager
		PluginManager pm = getServer().getPluginManager();

		//register the event to get passed to this plugin
		pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.High, this);

		//prints welcome message
		AntiMobSpawnVariables.print("Enabled and ready to stop those pesky creepers!");
	}

	//code to be executed when disabled
	@Override
	public void onDisable() {

		//prints goodbye message
		AntiMobSpawnVariables.print("Disabled, Goodbye!");
	}


}
