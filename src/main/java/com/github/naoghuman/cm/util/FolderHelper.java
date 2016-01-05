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
package com.github.naoghuman.cm.util;

import com.github.naoghuman.cm.util.api.IFolderHelper;
import de.pro.lib.logger.api.LoggerFacade;
import java.io.File;

/**
 *
 * @author PRo
 */
public class FolderHelper implements IFolderHelper {
    
    private static final String USER_DIR_PATH = 
            System.getProperty("user.dir") + File.separator // NOI18N
            + "matrix" + File.separator; // NOI18N
    
    public static IFolderHelper instance = null;
    
    public static IFolderHelper getDefault() {
        if (instance == null) {
            instance = new FolderHelper();
        }
        
        return instance;
    }
    
    private FolderHelper() {
        
    }

    @Override
    public void createFolder(String matrixFolder, String categoryFolder, String subCategoryFolder) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create Folders"); // NOI18N
        
        final StringBuilder pathWithFolder = new StringBuilder();
        pathWithFolder.append(USER_DIR_PATH);
        pathWithFolder.append(matrixFolder);
        
        if (categoryFolder != null) {
            pathWithFolder.append(File.separator );
            pathWithFolder.append(categoryFolder);
        }
        
        if (subCategoryFolder != null) {
            pathWithFolder.append(File.separator );
            pathWithFolder.append(subCategoryFolder);
        }
        
        final File file = new File(pathWithFolder.toString());
        file.mkdirs();
    }
}
