package uk.samlex.antimobspawn;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.entity.CreatureType; 

/**
 * AntiMobSpawn block listener
 * @author Sam_Lex
 */

public class AntiMobSpawnEntityListener extends EntityListener {

	public static AntiMobSpawn plugin;

	public AntiMobSpawnEntityListener(AntiMobSpawn instance){
		plugin = instance;
	}

    public void onCreatureSpawn(CreatureSpawnEvent event) {

    	if (event.getCreatureType() != CreatureType.CHICKEN && event.getCreatureType() != CreatureType.COW && event.getCreatureType() != CreatureType.PIG 
    			&& event.getCreatureType() != CreatureType.SHEEP && event.getCreatureType() != CreatureType.WOLF){
 
    		if (event.getLocation().getBlock().getRelative(0,-1,0).getTypeId() == AntiMobSpawnProperties.getStopSpawnBlock()){
    			event.setCancelled(true); 
    			//java.lang.System.out.println("Stopping Mob..."); 
    		}
    	}
    }
}

