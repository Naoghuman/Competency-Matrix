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

/**
 *
 * @author PRo
 */
public interface ModelFacade {
    
    public static CategoryModel getDefaultCategoryModel(long parentID, String title) {
        final CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(System.currentTimeMillis());
        categoryModel.setParentId(parentID);
        categoryModel.setGenerationTime(System.currentTimeMillis());
        categoryModel.setTitle(title);
        
        return categoryModel;
    }
    
    public static LevelModel getDefaultLevelModel() {
        return new LevelModel();
    }
    
    public static MatrixModel getDefaultMatrixModel(String title) {
        final MatrixModel matrixModel = new MatrixModel();
        matrixModel.setId(System.currentTimeMillis());
        matrixModel.setGenerationTime(System.currentTimeMillis());
        matrixModel.setTitle(title);
        
        return matrixModel;
    }
    
    public static SubCategoryModel getDefaultSubCategoryModel() {
        return new SubCategoryModel();
    }
    
}
