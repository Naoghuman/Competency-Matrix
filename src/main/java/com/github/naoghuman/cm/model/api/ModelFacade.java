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
package com.github.naoghuman.cm.model.api;

import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import com.github.naoghuman.cm.model.category.CategoryModel;
import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.cm.model.notes.NotesModel;
import com.github.naoghuman.lib.logger.api.LoggerFacade;

/**
 *
 * @author PRo
 */
public interface ModelFacade extends IEntityConfiguration {
    
    public static CategoryModel getDefaultCategory(long matrixId, String title) {
        LoggerFacade.INSTANCE.debug(ModelFacade.class, "Get default CategoryModel"); // NOI18N
        
        final CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(System.currentTimeMillis());
        categoryModel.setMatrixId(matrixId);
        categoryModel.setGenerationTime(System.currentTimeMillis());
        categoryModel.setTitle(title);
        categoryModel.setDescription(SIGN__EMPTY);
        
        return categoryModel;
    }
    
    public static MatrixModel getDefaultMatrix(String title) {
        LoggerFacade.INSTANCE.debug(ModelFacade.class, "Get default MatrixModel"); // NOI18N
        
        final MatrixModel matrixModel = new MatrixModel();
        matrixModel.setId(System.currentTimeMillis());
        matrixModel.setGenerationTime(System.currentTimeMillis());
        matrixModel.setTitle(title);
        matrixModel.setDescription(SIGN__EMPTY);
        
        return matrixModel;
    }
    
    public static NotesModel getDefaultNotes() {
        LoggerFacade.INSTANCE.debug(ModelFacade.class, "Get default NotesModel"); // NOI18N
        
        final NotesModel notesModel = new NotesModel();
        notesModel.setId(System.currentTimeMillis());
        notesModel.setGenerationTime(System.currentTimeMillis());
        notesModel.setNotes(SIGN__EMPTY);
        
        return notesModel;
    }
    
}
