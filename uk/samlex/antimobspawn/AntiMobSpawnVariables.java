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

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;

/*
 * Part of AntiMobSpawn 
 * Made by Sam_Lex, 2011
 */

public class AntiMobSpawnVariables {

	//logger for console printing
	private static final Logger log = Logger.getLogger("Minecraft"); 

	//stores the prefix to be put at the start of every message
	private static String prefix = "";

	//is true if there was an exception and so the plugin should use the default values
	private static boolean useDefault = false;

	//is false if the plugin should use the default settings for all worlds
	private static boolean multi = false;

	//object for data folder
	private static File dir;

	//object for properties file
	private static File props;

	//used to check for old file
	private static final File oldFile = new File("AntiMobSpawnBlocks.properties");

	//scanner for disk operations
	private static Scanner diskScanner;

	//default delimiter
	private static Pattern oldDel;

	//set delimiter
	private static final String newDel = "\\s*'\\s*";

	//writer to write to disk
	private static PrintWriter output;

	//get the instance of calendar, for data and time operations
	private static final Calendar cal = Calendar.getInstance();

	//data format for time
	private static final SimpleDateFormat timef = new SimpleDateFormat("h:mm a");

	//data format for day, month, year
	private static final SimpleDateFormat datef = new SimpleDateFormat("EEEE, d MMMM yyyy");

	//stores a list of all the worlds on the server
	private static List<World> worlds;

	//hashmaps for storing values for each world
	private static final Map<String, Boolean> all = new HashMap<String, Boolean>();

	private static final Map<String, ArrayList<Integer>> blocks = new HashMap<String, ArrayList<Integer>>();

	private static final Map<String, Boolean> chicken = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> cow = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> creeper = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> ghast = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> giant = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> monster = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> pig = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> pig_zombie = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> sheep = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> skeleton = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> slime = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> spider = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> squid = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> wolf = new HashMap<String, Boolean>();

	private static final Map<String, Boolean> zombie = new HashMap<String, Boolean>();

	//creates the prefix
	protected static void prefix(PluginDescriptionFile desc){
		prefix = "[" + desc.getFullName() + "]";
		return;
	}

	//prints the message given to it
	protected static void print(String message) {
		log.info(prefix + message);
		return;
	}

	//fills the maps with default values
	protected static void fillMaps(){

		final String value = "default";
		final ArrayList<Integer> block = new ArrayList<Integer>();
		block.add(18);
		block.add(46);

		all.put(value, false);
		blocks.put(value, block);
		chicken.put(value, false);
		cow.put(value, false);
		creeper.put(value, true);
		ghast.put(value, true);
		giant.put(value, true);
		monster.put(value, true);
		pig.put(value, false);
		pig_zombie.put(value, true);
		sheep.put(value, false);
		skeleton.put(value, true);
		slime.put(value, true);
		spider.put(value, true);
		squid.put(value, false);
		wolf.put(value, false);
		zombie.put(value, true);

		return;
	}

	//getters
	protected static boolean isUseDefault() {
		return useDefault;
	}

	protected static boolean isMulti() {
		return multi;
	}

	protected static String getPrefix() {
		return prefix;
	}

	protected static File getDir() {
		return dir;
	}

	protected static File getProps() {
		return props;
	}

	protected static File getOldFile() {
		return oldFile;
	}

	protected static Scanner getDiskScanner() {
		return diskScanner;
	}

	protected static Pattern getOldDel() {
		return oldDel;
	}

	protected static String getNewDel() {
		return newDel;
	}

	protected static PrintWriter getOutput() {
		return output;
	}

	protected static Calendar getCal() {
		return cal;
	}

	protected static SimpleDateFormat getTimef() {
		return timef;
	}

	protected static SimpleDateFormat getDatef() {
		return datef;
	}

	protected static List<World> getWorlds() {
		return worlds;
	}

	protected static Map<String, Boolean> getAll() {
		return all;
	}

	protected static Map<String, ArrayList<Integer>> getBlocks() {
		return blocks;
	}

	protected static Map<String, Boolean> getChicken() {
		return chicken;
	}

	protected static Map<String, Boolean> getCow() {
		return cow;
	}

	protected static Map<String, Boolean> getCreeper() {
		return creeper;
	}

	protected static Map<String, Boolean> getGhast() {
		return ghast;
	}

	protected static Map<String, Boolean> getGiant() {
		return giant;
	}

	protected static Map<String, Boolean> getMonster() {
		return monster;
	}

	protected static Map<String, Boolean> getPig() {
		return pig;
	}

	protected static Map<String, Boolean> getPig_zombie() {
		return pig_zombie;
	}

	protected static Map<String, Boolean> getSheep() {
		return sheep;
	}

	protected static Map<String, Boolean> getSkeleton() {
		return skeleton;
	}

	protected static Map<String, Boolean> getSlime() {
		return slime;
	}

	protected static Map<String, Boolean> getSpider() {
		return spider;
	}

	protected static Map<String, Boolean> getSquid() {
		return squid;
	}

	protected static Map<String, Boolean> getWolf() {
		return wolf;
	}

	protected static Map<String, Boolean> getZombie() {
		return zombie;
	}

	//setters
	protected static void setUseDefault(boolean useDefault) {
		AntiMobSpawnVariables.useDefault = useDefault;
		return;
	}

	protected static void setMulti(boolean multi) {
		AntiMobSpawnVariables.multi = multi;
		return;
	}

	protected static void setPrefix(String prefix) {
		AntiMobSpawnVariables.prefix = prefix;
		return;
	}

	protected static void setDir(File dir) {
		AntiMobSpawnVariables.dir = dir;
		return;
	}

	protected static void setProps(File props) {
		AntiMobSpawnVariables.props = props;
		return;
	}

	protected static void setDiskScanner(Scanner diskScanner) {
		AntiMobSpawnVariables.diskScanner = diskScanner;
		return;
	}

	protected static void setOldDel(Pattern oldDel) {
		AntiMobSpawnVariables.oldDel = oldDel;
		return;
	}

	protected static void setOutput(PrintWriter output) {
		AntiMobSpawnVariables.output = output;
		return;
	}

	protected static void setWorlds(List<World> worlds) {
		AntiMobSpawnVariables.worlds = worlds;
		return;
	}
}
