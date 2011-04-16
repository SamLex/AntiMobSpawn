package uk.samlex.antimobspawn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class AntiMobSpawnProperties extends AntiMobSpawn { 
	public static int stopSpawnBlock; 
	
    public static void scanner() throws IOException { 
    	final File properties = new File("AntiMobSpawnBlocks.properties"); 
    	if (properties.createNewFile() == true){ 
    		
    		java.lang.System.out.println("Properties file not found...creating new properties file"); 

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
