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

import uk.samlex.bukkitcommon.zone.command.CreateCommand;
import uk.samlex.bukkitcommon.zone.command.ListCommand;
import uk.samlex.bukkitcommon.zone.command.PreviewCommand;
import uk.samlex.bukkitcommon.command.ReloadCommand;
import uk.samlex.bukkitcommon.zone.command.RemoveCommand;
import uk.samlex.bukkitcommon.zone.command.SetCommand;
import uk.samlex.ams.config.ConfigStore;
import uk.samlex.ams.event.EntitySpawnHandler;
import uk.samlex.bukkitcommon.BukkitPlugin;
import uk.samlex.bukkitcommon.command.BukkitCommand;
import uk.samlex.bukkitcommon.zone.config.WorldZone;

// TODO: comment
public class AntiMobSpawn extends BukkitPlugin {

    private static AntiMobSpawn INSTANCE;

    public AntiMobSpawn() {
        INSTANCE = this;
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

    @Override
    public void onEnable() {
        new ConfigStore();

        getServer().getPluginManager().registerEvents(new EntitySpawnHandler(), instance());

        BukkitCommand createCommand = new CreateCommand(instance());
        getCommand("ams-create").setExecutor(createCommand);
        getCommand("ams-create").setTabCompleter(createCommand);

        BukkitCommand listCommand = new ListCommand(instance());
        getCommand("ams-list").setExecutor(listCommand);
        getCommand("ams-list").setTabCompleter(listCommand);

        BukkitCommand previewCommand = new PreviewCommand(instance());
        getCommand("ams-preview").setExecutor(previewCommand);
        getCommand("ams-preview").setTabCompleter(previewCommand);

        BukkitCommand reloadCommand = new ReloadCommand(instance());
        getCommand("ams-reload").setExecutor(reloadCommand);
        getCommand("ams-reload").setTabCompleter(reloadCommand);

        BukkitCommand removeCommand = new RemoveCommand(instance());
        getCommand("ams-remove").setExecutor(removeCommand);
        getCommand("ams-remove").setTabCompleter(removeCommand);

        BukkitCommand setCommand = new SetCommand(instance());
        getCommand("ams-set").setExecutor(setCommand);
        getCommand("ams-set").setTabCompleter(setCommand);
    }

    @Override
    public void reloadConfig() {
        ConfigStore.instance().reloadConfig();
    }
}
