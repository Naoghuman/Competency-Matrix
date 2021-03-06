/*
 * Copyright (C) 2016 PRo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.cm.util.api;

/**
 *
 * @author PRo
 */
public interface IFolderHelper {
    
    public static final Long LEVEL_1 = 1L;
    public static final Long LEVEL_2 = 2L;
    public static final Long LEVEL_3 = 3L;
    public static final Long LEVEL_4 = 4L;
    public static final Long LEVEL_ALL = -1L;
    
    public void create(Folder folder);
    public void open(Folder folder);
    
}
