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

package uk.samlex.ams.config;

public enum WorldMode {

    NONE {
        @Override
        public String toString() {
            return "none";
        }
    },
    GLOBAL {
        @Override
        public String toString() {
            return "global";
        }
    },
    SAFE {
        @Override
        public String toString() {
            return "safe";
        }
    },
    UNSAFE {
        @Override
        public String toString() {
            return "unsafe";
        }
    }
}
