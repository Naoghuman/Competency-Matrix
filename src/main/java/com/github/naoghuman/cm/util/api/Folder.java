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

import java.util.Map;
import javafx.collections.FXCollections;

/**
 *
 * @author PRo
 */
public class Folder {
    
    private final Map<EPath, Long> folderIds = FXCollections.observableHashMap();
    
    public Long getId(EPath folder) {
        Long folderId = folderIds.get(folder);
        if (
                folder.equals(EPath.MATRIX)
                && folderId == null
        ) {
            throw new IllegalArgumentException("matrixId can't be NULL"); // NOI18N
        }
        
        return folderIds.get(folder);
    }
    
    public void register(EPath folder, long folderId) {
        folderIds.put(folder, folderId);
    }
    
    public enum EPath {
        
        CATEGORY,
        LEVEL,
        MATRIX,
        SUBCATEGORY;
        
    }
    
}
