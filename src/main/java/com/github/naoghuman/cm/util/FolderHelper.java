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

import com.github.naoghuman.cm.model.matrix.category.CategoryModel;
import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.cm.model.matrix.category.subcategory.SubCategoryModel;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.IFolderHelper;
import com.github.naoghuman.cm.util.api.UtilFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
    
    private String convertCategoryIdToFolder(Long matrixId, Long categoryId) {
        String categoryFolder = null;
        if (categoryId != null) {
            final CategoryModel categoryModel = SqlFacade.INSTANCE.getCategorySqlProvider().findById(matrixId, categoryId);
            categoryFolder = categoryModel.getTitle();
        }
        
        return categoryFolder;
    }
    
    private String convertLevelIdToFolder(Long levelId) {
        String levelFolder = null;
        if (levelId != null) {
            levelFolder = "level" + levelId; // NOI18N
        }
        
        return levelFolder;
    }
    
    private String convertMatrixIdToFolder(Long matrixId) {
        final MatrixModel matrixModel = SqlFacade.INSTANCE.getMatrixSqlProvider().findById(matrixId);
        final String matrixFolder = matrixModel.getTitle();
        
        return matrixFolder;
    }
    
    private String convertSubCategoryIdToFolder(Long matrixId, Long categoryId, Long subCategoryId) {
        String subCategoryFolder = null;
        if (subCategoryId != null) {
            final SubCategoryModel subCategoryModel = SqlFacade.INSTANCE.getSubCategorySqlProvider().findById(matrixId, categoryId, subCategoryId);
            subCategoryFolder = subCategoryModel.getTitle();
        }
        
        return subCategoryFolder;
    }

    @Override
    public void create(Folder folder) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create Folder"); // NOI18N
        
        // Matrix folder
        final Long matrixId = folder.getId(Folder.EPathId.MATRIX_ID);
        final String matrixFolder = this.convertMatrixIdToFolder(matrixId);
        
        // Category folder
        final Long categoryId = folder.getId(Folder.EPathId.CATEGORY_ID);
        final String categoryFolder = this.convertCategoryIdToFolder(matrixId, categoryId);
        
        // SubCategory folder
        final Long subCategoryId = folder.getId(Folder.EPathId.SUBCATEGORY_ID);
        final String subCategoryFolder = this.convertSubCategoryIdToFolder(matrixId, categoryId, subCategoryId);
        
        // Level folder
        final Long levelId = folder.getId(Folder.EPathId.LEVEL_ID);
        if (levelId == null) {
        	// Create the folder
            final String pathWithFolder = this.createPath(matrixFolder, categoryFolder, subCategoryFolder);
            final File file = new File(pathWithFolder);
            file.mkdirs();
            
            return;
        }
        
        // Create the level folders
        String levelFolder = this.convertLevelIdToFolder(IFolderHelper.LEVEL_1);
        String pathWithFolder = this.createPath(matrixFolder, categoryFolder, subCategoryFolder, levelFolder);
        File file = new File(pathWithFolder);
        file.mkdirs();
        
        levelFolder = this.convertLevelIdToFolder(IFolderHelper.LEVEL_2);
        pathWithFolder = this.createPath(matrixFolder, categoryFolder, subCategoryFolder, levelFolder);
        file = new File(pathWithFolder);
        file.mkdirs();
        
        levelFolder = this.convertLevelIdToFolder(IFolderHelper.LEVEL_3);
        pathWithFolder = this.createPath(matrixFolder, categoryFolder, subCategoryFolder, levelFolder);
        file = new File(pathWithFolder);
        file.mkdirs();
        
        levelFolder = this.convertLevelIdToFolder(IFolderHelper.LEVEL_4);
        pathWithFolder = this.createPath(matrixFolder, categoryFolder, subCategoryFolder, levelFolder);
        file = new File(pathWithFolder);
        file.mkdirs();
    }
    
    private String createPath(String matrixFolder, String categoryFolder, String subCategoryFolder) {
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
        
        return pathWithFolder.toString();
    }
    
    private String createPath(String matrixFolder, String categoryFolder, String subCategoryFolder, String levelFolder) {
        final StringBuilder pathWithFolder = new StringBuilder();
        pathWithFolder.append(this.createPath(matrixFolder, categoryFolder, subCategoryFolder));
        
        if (levelFolder != null) {
            pathWithFolder.append(File.separator );
            pathWithFolder.append(levelFolder);
        }
        
        return pathWithFolder.toString();
    }

    @Override
    public void open(Folder folder) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Open Folder"); // NOI18N

        // Matrix folder
        final Long matrixId = folder.getId(Folder.EPathId.MATRIX_ID);
        final String matrixFolder = this.convertMatrixIdToFolder(matrixId);
        
        // Category folder
        final Long categoryId = folder.getId(Folder.EPathId.CATEGORY_ID);
        final String categoryFolder = this.convertCategoryIdToFolder(matrixId, categoryId);
        
        // SubCategory folder
        final Long subCategoryId = folder.getId(Folder.EPathId.SUBCATEGORY_ID);
        final String subCategoryFolder = this.convertSubCategoryIdToFolder(matrixId, categoryId, subCategoryId);
        
        // Level folder
        final Long levelId = folder.getId(Folder.EPathId.LEVEL_ID);
        String levelFolder = this.convertLevelIdToFolder(levelId);
        
        // Open the folder
        final Desktop desktop = Desktop.getDesktop();
        final String pathWithFolder = this.createPath(matrixFolder, categoryFolder, subCategoryFolder, levelFolder);
        final File folderToOpen = new File(pathWithFolder);
        try {
            desktop.open(folderToOpen);
        } catch (IOException ex) {
            LoggerFacade.INSTANCE.error(this.getClass(), "Can't open folder: " + folderToOpen.getAbsolutePath(), ex); // NOI18N
        }
    }
    
}
