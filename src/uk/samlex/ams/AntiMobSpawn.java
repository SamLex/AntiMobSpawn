package uk.samlex.ams;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

import uk.samlex.ams.config.WorldZone;
import uk.samlex.ams.config.ConfigStore;
import uk.samlex.ams.event.EntitySpawnHandler;

public class AntiMobSpawn extends JavaPlugin {

    private static AntiMobSpawn INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        new ConfigStore();

        getServer().getPluginManager().registerEvents(new EntitySpawnHandler(), instance());
    }
    
    public static AntiMobSpawn instance() {
        return INSTANCE;
    }

    public void checkDatabase() {
        try {
            getDatabase().find(WorldZone.class).findRowCount();
        } catch (PersistenceException pe) {
            getLogger().info("Installing database due to first time use");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> dbClasses = new ArrayList<Class<?>>(1);
        dbClasses.add(WorldZone.class);
        return dbClasses;
    }
}
