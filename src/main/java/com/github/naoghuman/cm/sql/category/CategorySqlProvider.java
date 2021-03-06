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
package com.github.naoghuman.cm.sql.category;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.api.ModelFacade;
import com.github.naoghuman.cm.model.category.CategoryModel;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.UtilFacade;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class CategorySqlProvider implements IActionConfiguration, IEntityConfiguration, IRegisterActions {
    
    private static CategorySqlProvider instance = null;
    
    public static CategorySqlProvider getDefault() {
        if (instance == null) {
            instance = new CategorySqlProvider();
        }
        
        return instance;
    }
    
    private CategorySqlProvider() {
        
    }
    
    private CategoryModel create(long matrixId, String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create CategoryModel"); // NOI18N
        
        final CategoryModel categoryModel = ModelFacade.getDefaultCategory(matrixId, title);
        DatabaseFacade.INSTANCE.getCrudService().create(categoryModel);
        
        return categoryModel;
    }
    
    public List<CategoryModel> findAll(long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all CategoryModels with matrixId: " + matrixId); // NOI18N
        
        if (matrixId == -1L) {
            return FXCollections.<CategoryModel>emptyObservableList();
        }
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(COLUMN_NAME__MATRIX_ID, matrixId);
        
        final List<CategoryModel> categoryModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(CategoryModel.class, NAMED_QUERY__NAME__CATEGORY_FIND_ALL, parameters);
        Collections.sort(categoryModels);
        
        return categoryModels;
    }

    public CategoryModel findById(long matrixId, long categoryId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "FindById CategoryModel"); // NOI18N
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(COLUMN_NAME__ID, categoryId);
        parameters.put(COLUMN_NAME__MATRIX_ID, matrixId);
        
        final List<CategoryModel> categoryModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(CategoryModel.class, NAMED_QUERY__NAME__CATEGORY_FIND_BY_ID, parameters);
        
        return categoryModels.get(0);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in CategorySqlProvider"); // NOI18N
        
        this.registerOnActionCreateCategory();
    }

    private void registerOnActionCreateCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action Create CategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__CATEGORY,
                (ActionEvent ae) -> {
                    final TransferData transferData = (TransferData) ae.getSource();
                    final String title = transferData.getString();
                    final Long matrixId = transferData.getLong();
                    final CategoryModel categoryModel = this.create(matrixId, title);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        ActionFacade.INSTANCE.handle(ACTION__REFRESH__CATEGORY_OVERVIEW);
                        
                        final TransferData transferData2 = new TransferData();
                        transferData2.setActionId(ACTION__CREATE__FOLDER);
                        final Folder folder = UtilFacade.INSTANCE.getDefaultFolder();
                        folder.register(Folder.EPath.MATRIX, matrixId);
                        folder.register(Folder.EPath.CATEGORY, categoryModel.getId());
                        transferData2.setObject(folder);
                        ActionFacade.INSTANCE.handle(transferData2);
                    });
                    pt.playFromStart();
                });
    }
    
}
