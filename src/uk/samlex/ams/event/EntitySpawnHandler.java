package uk.samlex.ams.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntitySpawnHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntitySpawn(CreatureSpawnEvent cse) {
        
    }
}
