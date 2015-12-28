/*
 * Copyright (C) 2015 PRo
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
package com.github.naoghuman.cm.configuration.api;

/**
 *
 * @author PRo
 */
public interface IActionConfiguration {
    
    public static final String ACTION__CREATE__CATEGORY = "ACTION__CREATE__CATEGORY"; // NOI18N
    public static final String ACTION__CREATE__MATRIX = "ACTION__CREATE__MATRIX"; // NOI18N
    public static final String ACTION__CREATE__SUBCATEGORY = "ACTION__CREATE__SUBCATEGORY"; // NOI18N
    
    public static final String ACTION__DELETE__CATEGORY = "ACTION__DELETE__CATEGORY"; // NOI18N
    public static final String ACTION__DELETE__MATRIX = "ACTION__DELETE___MATRIX"; // NOI18N
    public static final String ACTION__DELETE__SUBCATEGORY = "ACTION__DELETE__SUBCATEGORY"; // NOI18N
    
    public static final String ACTION__OPEN__MATRIX = "ACTION__OPEN__COMPETENCY_MATRIX"; // NOI18N
    
    public static final String ACTION__REFRESH__CATEGORY = "ACTION__REFRESH__CATEGORY"; // NOI18N
    public static final String ACTION__REFRESH__MATRIX = "ACTION__REFRESH__MATRIX"; // NOI18N
    public static final String ACTION__REFRESH__OVERVIEW_MATRIX = "ACTION__REFRESH__OVERVIEW_MATRIX"; // NOI18N
    
    public static final String ACTION__REMOVE__CATEGORY = "ACTION__REMOVE__CATEGORY"; // NOI18N
    public static final String ACTION__REMOVE__MATRIX = "ACTION__REMOVE__MATRIX"; // NOI18N
    public static final String ACTION__REMOVE__SUBCATEGORY = "ACTION__REMOVE__SUBCATEGORY"; // NOI18N
    
}
