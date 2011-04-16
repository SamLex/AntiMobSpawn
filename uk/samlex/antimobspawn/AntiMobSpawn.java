package uk.samlex.antimobspawn;

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
    
    public void onDisable() {
        System.out.println("AntiMobSpawn Disabled!");
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Normal, this);

        final PluginDescriptionFile pdfFile = this.getDescription();

        System.out.println( pdfFile.getName() + " (version " + pdfFile.getVersion() + ") is enabled!" );

        try {
        	AntiMobSpawnProperties.scanner();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}

