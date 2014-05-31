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

package uk.samlex.ams.util;

import java.util.Arrays;

public class EnumSort {

    public static <T extends Enum<T>> T[] sortEnumArrayAlphabetically(T[] e, Class<T> enumClass) {
        String[] names = new String[e.length];

        for (int i = 0; i < names.length; i++) {
            names[i] = e[i].toString();
        }

        Arrays.sort(names);

        for (int i = 0; i < e.length; i++) {
            e[i] = Enum.valueOf(enumClass, names[i]);
        }

        return e;
    }
}
