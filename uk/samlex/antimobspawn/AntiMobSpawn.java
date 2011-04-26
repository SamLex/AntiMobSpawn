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
//TODO Add support no-spawn zones in a set cuboid
import java.io.IOException; 
import org.bukkit.event.Event; 
import org.bukkit.plugin.PluginDescriptionFile; 
import org.bukkit.plugin.java.JavaPlugin; 
import org.bukkit.plugin.PluginManager; 

/**
 * AntiMobSpawn 
 * @author Sam_Lex
 */

public class AntiMobSpawn extends JavaPlugin {

	//declare entity listener
	private final AntiMobSpawnEntityListener entityListener = new AntiMobSpawnEntityListener(this);

	//variable for prefix
	private static String prefix = "";

	//what to do on plugin disable
	@Override
	public void onDisable() {

		//print goodbye message
		System.out.println(prefix() + " Disabled, GoodBye!");
	}

	//what to do on plugin enable
	@Override
	public void onEnable() {

		//make instance of plugin manager
		PluginManager pm = getServer().getPluginManager();

		//register event
		pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Highest, this);

		//print welcome message
		System.out.println(prefix() + " Enabled and ready to stop those pesky creepers!" );

		//try and run scanner method from other class
		try {

			//call method
			AntiMobSpawnDisk.scanner();
		} catch (IOException e) {

			//print message if error
			System.out.println(prefix() + " [WARNING] There was a problem while trying to load the properties file for AntiMobSpawn");
		}
	}

	//make a prefix class for messages
	public String prefix(){

		//get info from plguin.yml
		PluginDescriptionFile desc = this.getDescription();

		//make and return prefix
		prefix = "[" + desc.getName() + " v" + desc.getVersion() + "]";

		return prefix;
	}

	//getter for prefix
	public static String getPrefix() {
		return prefix;
	}
}

