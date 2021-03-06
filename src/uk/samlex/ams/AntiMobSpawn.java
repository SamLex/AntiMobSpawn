/*
 * AntiMobSpawn, a plugin for the Minecraft server modification Bukkit. Provides control over
 * in game creature spawns
 * 
 * Copyright (C) 2014 Euan James Hunter <euanhunter117@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

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
