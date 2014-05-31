package uk.samlex.ams;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

import uk.samlex.ams.command.CreateCommand;
import uk.samlex.ams.command.GenericCommand;
import uk.samlex.ams.command.ListCommand;
import uk.samlex.ams.command.PreviewCommand;
import uk.samlex.ams.command.ReloadCommand;
import uk.samlex.ams.command.RemoveCommand;
import uk.samlex.ams.command.SetCommand;
import uk.samlex.ams.config.ConfigStore;
import uk.samlex.ams.config.WorldZone;
import uk.samlex.ams.event.EntitySpawnHandler;

// TODO: license readme, description and testing
public class AntiMobSpawn extends JavaPlugin {

    private static AntiMobSpawn INSTANCE;

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

    @Override
    public void onEnable() {
        INSTANCE = this;

        new ConfigStore();

        getServer().getPluginManager().registerEvents(new EntitySpawnHandler(), instance());

        GenericCommand createCommand = new CreateCommand();
        getCommand("ams-create").setExecutor(createCommand);
        getCommand("ams-create").setTabCompleter(createCommand);

        GenericCommand listCommand = new ListCommand();
        getCommand("ams-list").setExecutor(listCommand);
        getCommand("ams-list").setTabCompleter(listCommand);

        GenericCommand previewCommand = new PreviewCommand();
        getCommand("ams-preview").setExecutor(previewCommand);
        getCommand("ams-preview").setTabCompleter(previewCommand);

        GenericCommand reloadCommand = new ReloadCommand();
        getCommand("ams-reload").setExecutor(reloadCommand);
        getCommand("ams-reload").setTabCompleter(reloadCommand);

        GenericCommand removeCommand = new RemoveCommand();
        getCommand("ams-remove").setExecutor(removeCommand);
        getCommand("ams-remove").setTabCompleter(removeCommand);

        GenericCommand setCommand = new SetCommand();
        getCommand("ams-set").setExecutor(setCommand);
        getCommand("ams-set").setTabCompleter(setCommand);
    }
}
