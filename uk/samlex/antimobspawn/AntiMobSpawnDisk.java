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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * AntiMobSpawn properties class
 * @author Sam_Lex
 */

public class AntiMobSpawnDisk { 

	//declare and make list for string values of blocks monsters can't spawn on
	private static ArrayList<Integer> stopSpawnBlocks = new ArrayList<Integer>();

	//declare scanner method
	public static void scanner() throws IOException { 

		//declare file object
		final File properties = new File("AntiMobSpawnBlocks.properties"); 

		//make new file if one doesn't exist
		if (properties.createNewFile() == true){ 

			//print message when making file
			System.out.println(AntiMobSpawn.getPrefix() + " Properties file not found...creating new properties file"); //need to make prefix static

			//open output to file
			final Writer output = new BufferedWriter(new FileWriter(properties));

			//output '18' into file and 'close' file
			output.write("0\n18");
			output.close(); 
		} 

		//declare scanner object
		final Scanner diskScanner = new Scanner(properties); 

		//while there is another number in file, keep scanning
		while(diskScanner.hasNextInt()){

			stopSpawnBlocks.add(diskScanner.nextInt());
		}

		//close file
		diskScanner.close();
		//System.out.println(getStopSpawnBlocks()); //debug print
	}

	//getter for list of blocks
	public final static ArrayList<Integer> getStopSpawnBlocks() { 
		return stopSpawnBlocks;
	}
}
