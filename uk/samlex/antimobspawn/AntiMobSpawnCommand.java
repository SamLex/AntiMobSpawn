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

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AntiMobSpawnCommand implements CommandExecutor{

	private Plugin inst;

	/*
	 * Constructor that is passed the instance of the plugin
	 */
	protected AntiMobSpawnCommand(Plugin plugin){
		this.inst = plugin;
	}

	/*
	 * Reloads the configuration file from the disk. Also makes the command only usable from the server console or another non-player source
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			this.print("Reloading values from disk");
			this.inst.reloadConfig();
			return true;
		}else if(sender.isOp()){
			sender.sendMessage(ChatColor.AQUA + "Please use this command from the sever console");
			return false;
		}else{
			sender.sendMessage(ChatColor.RED + "You are not allowed to use this command");
			return false;
		}
	}

	/*
	 * Provides an easy function to do the printing 
	 */
	private void print(String message){
		Logger.getLogger("Minecraft").info('[' + inst.getDescription().getFullName() + "] " + message);
		return;
	}
}
