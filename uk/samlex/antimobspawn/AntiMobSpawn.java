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
//TODO Add support for multiple blocks and no-spawn zones in a set cuboid and do a manual code cleanup 
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
	private final AntiMobSpawnEntityListener entityListener = new AntiMobSpawnEntityListener(this);

	@Override
	public void onDisable() {
		System.out.println("AntiMobSpawn Disabled!");
	}

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Highest, this);

		final PluginDescriptionFile pdfFile = this.getDescription();

		System.out.println(pdfFile.getName() + " (version " + pdfFile.getVersion() + ") is enabled!" );

		try {
			AntiMobSpawnDisk.scanner();
		} catch (IOException e) {
			System.out.println("[WARNING] There was a problem while trying to load the properties file for AntiMobSpawn");
		}
	}
}

