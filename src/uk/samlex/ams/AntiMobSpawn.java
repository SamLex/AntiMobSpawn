/*
 * AntiMobSpawn is a plugin for the Minecraft Server Mod Bukkit. AntiMobSpawn gives you ultimate control over the creatures that spawn in your Minecraft worlds
 * Copyright (C) 2011-2013 Euan J Hunter (SamLex) (Sam_Lex)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.samlex.ams;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiMobSpawn extends JavaPlugin {

    private AMSConfig config;

    @Override
    public void onEnable() {
        config = new AMSConfig(this);
        setupDB();

        getServer().getPluginManager().registerEvents(new AMSListener(this), this);
        getCommand("ams").setExecutor(new AMSCommandHandler(this));

        getLogger().info("Enabled. Hello");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled. Goodbye");
    }

    // Installs the plugin database if it has not already been installed
    private void setupDB() {
        try {
            getDatabase().find(AMSZone.class).findRowCount();
        } catch (PersistenceException e) {
            getLogger().info("Installing database due to first time use");
            installDDL();
        }
    }

    // Adds persistent class AMSZone to the database
    @Override
    public List<Class<?>> getDatabaseClasses() {
        final ArrayList<Class<?>> dbClasses = new ArrayList<>(1);
        dbClasses.add(AMSZone.class);
        return dbClasses;
    }

    public AMSConfig getAMSConfig() {
        return config;
    }
}
