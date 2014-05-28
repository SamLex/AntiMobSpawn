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

package uk.samlex.ams.config;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.util.Vector;

import uk.samlex.ams.util.BoundingBox;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "antimobspawn_world_zone")
public class WorldZone {

    @Id
    private int id;

    @NotNull
    private String worldName;

    @NotNull
    private String zoneName;

    @NotNull
    private Vector pointOneVector;

    @NotNull
    private Vector pointTwoVector;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Vector getPointOneVector() {
        return pointOneVector;
    }

    public void setPointOneVector(Vector pointOneVector) {
        this.pointOneVector = pointOneVector;
    }

    public Vector getPointTwoVector() {
        return pointTwoVector;
    }

    public void setPointTwoVector(Vector pointTwoVector) {
        this.pointTwoVector = pointTwoVector;
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(pointOneVector, pointOneVector);
    }
}
