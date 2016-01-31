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
package com.github.naoghuman.cm.model.api;

import com.github.naoghuman.cm.model.matrix.category.subcategory.level.LevelModel;
import com.github.naoghuman.cm.model.matrix.category.subcategory.SubCategoryModel;
import com.github.naoghuman.cm.model.matrix.category.CategoryModel;
import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;

/**
 *
 * @author PRo
 */
public interface ModelFacade {
    
    public static CategoryModel getDefaultCategory(long matrixId, String title) {
        final CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(System.currentTimeMillis());
        categoryModel.setMatrixId(matrixId);
        categoryModel.setGenerationTime(System.currentTimeMillis());
        categoryModel.setTitle(title);
        
        return categoryModel;
    }
    
    public static LevelModel getDefaultLevel(long id, long matrixId, long categoryId, long subCategoryId, int level) {
        final LevelModel levelModel = new LevelModel();
        levelModel.setId(id);
        levelModel.setMatrixId(matrixId);
        levelModel.setCategoryId(categoryId);
        levelModel.setSubCategoryId(subCategoryId);
        levelModel.setGenerationTime(System.currentTimeMillis());
        levelModel.setLevel(level);
        levelModel.setNotes(IEntityConfiguration.SIGN__EMPTY);
        
        return levelModel;
    }
    
    public static MatrixModel getDefaultMatrix(String title) {
        final MatrixModel matrixModel = new MatrixModel();
        matrixModel.setId(System.currentTimeMillis());
        matrixModel.setGenerationTime(System.currentTimeMillis());
        matrixModel.setTitle(title);
        
        return matrixModel;
    }
    
    public static SubCategoryModel getDefaultSubCategory(long matrixId, long categoryId, String title) {
        final SubCategoryModel subCategoryModel = new SubCategoryModel();
        subCategoryModel.setId(System.currentTimeMillis());
        subCategoryModel.setMatrixId(matrixId);
        subCategoryModel.setCategoryId(categoryId);
        subCategoryModel.setGenerationTime(System.currentTimeMillis());
        subCategoryModel.setTitle(title);
        
        return subCategoryModel;
    }
    
}
