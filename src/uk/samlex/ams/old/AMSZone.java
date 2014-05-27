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

package uk.samlex.ams.old;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "ams_zone")
public class AMSZone {

    @Id
    private int id;

    @NotNull
    private String world_name;

    @NotNull
    private String zone_name;

    @NotNull
    private int vec_p1_x;

    @NotNull
    private int vec_p1_y;

    @NotNull
    private int vec_p1_z;

    @NotNull
    private int vec_p2_x;

    @NotNull
    private int vec_p2_y;

    @NotNull
    private int vec_p2_z;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorld_name() {
        return world_name;
    }

    public void setWorld_name(String world_name) {
        this.world_name = world_name;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public int getVec_p1_x() {
        return vec_p1_x;
    }

    public void setVec_p1_x(int vec_p1_x) {
        this.vec_p1_x = vec_p1_x;
    }

    public int getVec_p1_y() {
        return vec_p1_y;
    }

    public void setVec_p1_y(int vec_p1_y) {
        this.vec_p1_y = vec_p1_y;
    }

    public int getVec_p1_z() {
        return vec_p1_z;
    }

    public void setVec_p1_z(int vec_p1_z) {
        this.vec_p1_z = vec_p1_z;
    }

    public int getVec_p2_x() {
        return vec_p2_x;
    }

    public void setVec_p2_x(int vec_p2_x) {
        this.vec_p2_x = vec_p2_x;
    }

    public int getVec_p2_y() {
        return vec_p2_y;
    }

    public void setVec_p2_y(int vec_p2_y) {
        this.vec_p2_y = vec_p2_y;
    }

    public int getVec_p2_z() {
        return vec_p2_z;
    }

    public void setVec_p2_z(int vec_p2_z) {
        this.vec_p2_z = vec_p2_z;
    }
}
