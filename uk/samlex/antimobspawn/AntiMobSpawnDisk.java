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
import java.util.Scanner;

/**
 * AntiMobSpawn properties class
 * @author Sam_Lex
 */

public class AntiMobSpawnDisk extends AntiMobSpawn { 
	
	public static int stopSpawnBlock; 
	
    public static void scanner() throws IOException { 
    	final File properties = new File("AntiMobSpawnBlocks.properties"); 
    	if (properties.createNewFile() == true){ 
    		
    		System.out.println("Properties file not found...creating new properties file");

    		final Writer output = new BufferedWriter(new FileWriter(properties));
    	    output.write("18");
    	    output.close(); 
    	} 
    	
    	final Scanner diskScanner = new Scanner(properties); 
    	stopSpawnBlock = diskScanner.nextInt(); 
    }
    
	public final static int getStopSpawnBlock() { 
		return stopSpawnBlock;
	}
}
