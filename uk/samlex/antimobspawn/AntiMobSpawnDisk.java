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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.bukkit.World;

/*
 * Part of AntiMobSpawn 
 * Made by Sam_Lex, 2011
 */

public class AntiMobSpawnDisk {

	//local variables to minimise calls to AntiMobSpawnVariables
	private static PrintWriter output;
	private static Scanner diskScanner;
	private static String value = "defaultFile";

	//constructor for AntiMobSpawnDisk which runs all the operations of the class
	protected AntiMobSpawnDisk(AntiMobSpawn plugin){

		//checks for and creates data folder and properties file and deletes the old file if it is present
		files(plugin);

		//reads from the file if there was not an exception in the writing of the file
		if(!AntiMobSpawnVariables.isUseDefault()){
			reader();
		}

		//prints debugging information
		//		debug();
	}

	//checks for and creates data folder and properties file and deletes the old file if it is present
	private static void files(AntiMobSpawn plugin){

		//sets the data folder object
		AntiMobSpawnVariables.setDir(plugin.getDataFolder());

		//sets the file object
		AntiMobSpawnVariables.setProps(new File(AntiMobSpawnVariables.getDir(), "AntiMobSpawn.properties"));

		//checks for the old file and deletes it if it is present
		if(AntiMobSpawnVariables.getOldFile().exists()){

			AntiMobSpawnVariables.getOldFile().delete();

			AntiMobSpawnVariables.print("Old properties file detected, deleting old file");
		}

		//checks for the data folder and makes it if it is not there
		if(!AntiMobSpawnVariables.getDir().exists()){

			AntiMobSpawnVariables.getDir().mkdir();
		}

		//tries to create a new file
		try{
			if(AntiMobSpawnVariables.getProps().createNewFile()){

				AntiMobSpawnVariables.print("Properties file not detected, creating properties file");

				//writes to the file if there was no an exception in the making of the file
				if(!AntiMobSpawnVariables.isUseDefault()){
					writer();
				}
			}
		}catch(IOException ioe){

			AntiMobSpawnVariables.print("Exception while creating properties file, plugin will now continue by using default values");

			AntiMobSpawnVariables.setUseDefault(true);
		}

		return;
	}

	//writes to the file if there was not an exception in the making of the file
	private static void writer(){

		//tries to write to the file
		try{
			AntiMobSpawnVariables.setOutput(new PrintWriter(new BufferedWriter(new FileWriter(AntiMobSpawnVariables.getProps()))));

			output = AntiMobSpawnVariables.getOutput();

			//output data into the buffer and then flush and close the file
			output.println("#Properties File for the Bukkit plugin, AntiMobSpawn");
			output.println("#Generated at " + AntiMobSpawnVariables.getTimef().format(AntiMobSpawnVariables.getCal().getTime()) + " on " + 
					AntiMobSpawnVariables.getDatef().format(AntiMobSpawnVariables.getCal().getTime()));
			output.println("#All selected values MUST be within apostrophes ('')");
			output.println();
			output.println("#Setting to enable multi-world support. If false, all worlds will follow the default world settings");
			output.println("multi: 'true'");
			output.println();
			output.println("#The default world");
			output.println("Default:");
			settings();

			for (World world : AntiMobSpawnVariables.getWorlds()) {
				output.println(world.getName() + ":");
				settings();
			}

			output.flush();
			output.close();

		}catch(IOException ioe){

			AntiMobSpawnVariables.print("Exception while writing to the properties file, plugin will now continue by using default values");

			AntiMobSpawnVariables.setUseDefault(true);
		}

		return;
	}

	//reads from the file if there was not an exception in the writing of the file
	private static void reader(){

		try{

			//sets the file scanner object
			AntiMobSpawnVariables.setDiskScanner(new Scanner(AntiMobSpawnVariables.getProps()));

			diskScanner = AntiMobSpawnVariables.getDiskScanner();

			AntiMobSpawnVariables.setOldDel(diskScanner.delimiter());

			lnMove(5);

			newDel();

			AntiMobSpawnVariables.setMulti(diskScanner.nextBoolean());

			diskScanner.useDelimiter(AntiMobSpawnVariables.getOldDel());

			lnMove(3);

			for(int i = 0; i < (AntiMobSpawnVariables.getWorlds().size() + 1); i++){
				fileReader();
			}

		}catch(FileNotFoundException fnfe){

			AntiMobSpawnVariables.print("Properties file can not be found, plugin will now continue using default vaules");

		}catch(InputMismatchException ime){

			AntiMobSpawnVariables.print("The file format is incorrect. Please correct this for the next run. The plugin will continue with defaults");

//			ime.printStackTrace();

			AntiMobSpawnVariables.setUseDefault(true);

		}catch(NoSuchElementException nsee){}

		diskScanner.close();

		return;
	}

	private static void settings(){

		//prints a default template for each world to the file
		output.println("#Select whether to block spawns on all block types");
		output.println("\tall blocks: 'false'");
		output.println("#List of blocks to stop spawns on. Each number must be within it's own apostrophes ('') with a single ending apostrophe");
		output.println("\tblocks: '18' '46''");
		output.println("#Select which creatures the plugin should stop from spawning. True to stop them, false not to");
		output.println("\tchicken: 'false'");
		output.println("\tcow: 'false'");
		output.println("\tcreeper: 'true'");
		output.println("\tghast: 'true'");
		output.println("\tgiant: 'true'");
		output.println("\tmonster: 'true'");
		output.println("\tpig: 'false'");
		output.println("\tpig zombie: 'true'");
		output.println("\tsheep: 'false'");
		output.println("\tskeleton: 'true'");
		output.println("\tslime: 'true'");
		output.println("\tspider: 'true'");
		output.println("\tsquid: 'false'");
		output.println("\twolf: 'false'");
		output.println("\tzombie: 'true'");
		output.println();

		return;
	}

	//method to advance scanner given number of lines
	private static void lnMove(Integer n){
		for(int i = 0;i < n; i++){
			diskScanner.nextLine();
		}

		return;
	}

	//sets the delimiter and moves the scanner
	private static void newDel(){
		diskScanner.useDelimiter(AntiMobSpawnVariables.getNewDel());
		diskScanner.next();

		return;
	}

	//reads a large part of the properties file
	private static void fileReader() throws NoSuchElementException{

		Pattern oldDel = AntiMobSpawnVariables.getOldDel();

		lnMove(2);

		newDel();

		AntiMobSpawnVariables.getAll().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(2);

		newDel();

		ArrayList<Integer> block = new ArrayList<Integer>();

		while(diskScanner.hasNextInt()){
			block.add(diskScanner.nextInt());
			diskScanner.next();
		}

		AntiMobSpawnVariables.getBlocks().put(value, block);

		diskScanner.useDelimiter(oldDel);

		lnMove(2);

		newDel();

		AntiMobSpawnVariables.getChicken().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getCow().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getCreeper().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getGhast().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getGiant().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getMonster().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getPig().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getPig_zombie().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getSheep().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getSkeleton().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getSlime().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getSpider().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getSquid().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getWolf().put(value, diskScanner.nextBoolean());

		diskScanner.useDelimiter(oldDel);

		lnMove(1);

		newDel();

		AntiMobSpawnVariables.getZombie().put(value, diskScanner.nextBoolean());

		lnMove(2);

		diskScanner.useDelimiter(":");

		value = diskScanner.next();

		diskScanner.useDelimiter(oldDel);

		return;
	}

//	private static void debug(){
//		String[] worlds = {"default", "defaultFile", "Test World", "Test World_nether"};
//		System.out.println("multi:" + AntiMobSpawnVariables.isMulti());
//		System.out.println("default:" + AntiMobSpawnVariables.isUseDefault());
//		for(int i = 0; i < worlds.length; i++){
//			System.out.println(worlds[i] + " all:" + AntiMobSpawnVariables.getAll().get(worlds[i]));
//			System.out.println(worlds[i] + " blocks:" + AntiMobSpawnVariables.getBlocks().get(worlds[i]));
//			System.out.println(worlds[i] + " chicken:" + AntiMobSpawnVariables.getChicken().get(worlds[i]));
//			System.out.println(worlds[i] + " cow:" + AntiMobSpawnVariables.getCow().get(worlds[i]));
//			System.out.println(worlds[i] + " creeper:" + AntiMobSpawnVariables.getCreeper().get(worlds[i]));
//			System.out.println(worlds[i] + " ghast:" + AntiMobSpawnVariables.getGhast().get(worlds[i]));
//			System.out.println(worlds[i] + " giant:" + AntiMobSpawnVariables.getGiant().get(worlds[i]));
//			System.out.println(worlds[i] + " monster:" + AntiMobSpawnVariables.getMonster().get(worlds[i]));
//			System.out.println(worlds[i] + " pig:" + AntiMobSpawnVariables.getPig().get(worlds[i]));
//			System.out.println(worlds[i] + " pig zombie:" + AntiMobSpawnVariables.getPig_zombie().get(worlds[i]));
//			System.out.println(worlds[i] + " sheep:" + AntiMobSpawnVariables.getSheep().get(worlds[i]));
//			System.out.println(worlds[i] + " skeleton:" + AntiMobSpawnVariables.getSkeleton().get(worlds[i]));
//			System.out.println(worlds[i] + " slime:" + AntiMobSpawnVariables.getSlime().get(worlds[i]));
//			System.out.println(worlds[i] + " spider:" + AntiMobSpawnVariables.getSpider().get(worlds[i]));
//			System.out.println(worlds[i] + " squid:" + AntiMobSpawnVariables.getSquid().get(worlds[i]));
//			System.out.println(worlds[i] + " wolf:" + AntiMobSpawnVariables.getWolf().get(worlds[i]));
//			System.out.println(worlds[i] + " zombie:" + AntiMobSpawnVariables.getZombie().get(worlds[i]));
//			System.out.println();
//
//		}
//		return;
//	}
}
