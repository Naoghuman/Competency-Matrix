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
package com.github.naoghuman.cm.sql;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import static com.github.naoghuman.cm.configuration.api.IActionConfiguration.ACTION__CREATE__FOLDERS;
import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.api.CategoryModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import com.github.naoghuman.cm.util.api.Folder;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
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
public final class CategorySqlProvider implements IActionConfiguration, IEntityConfiguration, IRegisterActions {
    
    private static CategorySqlProvider instance = null;
    
    public static CategorySqlProvider getDefault() {
        if (instance == null) {
            instance = new CategorySqlProvider();
        }
        
        return instance;
    }
    
    private CategorySqlProvider() {}

    private CategoryModel create(long matrixId, String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create CategoryModel"); // NOI18N
        
        final CategoryModel categoryModel = ModelFacade.getDefaultCategory(matrixId, title);
        DatabaseFacade.INSTANCE.getCrudService().create(categoryModel);
        
        return categoryModel;
    }
    
    public void delete(long matrixId, long categoryId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete CategoryModel with all associated Models"); // NOI18N
        
        // Delete CategoryModel
        DatabaseFacade.INSTANCE.getCrudService().delete(CategoryModel.class, categoryId);
        
        // Delete all SubCategoryModels
        SqlFacade.INSTANCE.getSubCategorySqlProvider().deleteAll(matrixId, categoryId);
    }
    
    public void deleteAll(long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete all CategoryModels from: " + matrixId); // NOI18N
        
        // Find all
        final List<CategoryModel> categoryModels = this.findAll(matrixId);
        
        // Delete them
        for (CategoryModel categoryModel : categoryModels) {
            final long categoryId = categoryModel.getId();
            this.delete(matrixId, categoryId);
        }
    }

    public List<CategoryModel> findAll(long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all CategoryModels"); // NOI18N
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(COLUMN_NAME__MATRIX_ID, matrixId);
        
        final List<CategoryModel> categoryModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(CategoryModel.class, NAMED_QUERY__NAME__CATEGORY_FIND_ALL, parameters);
        Collections.sort(categoryModels);
        
        return categoryModels;
    }

    public CategoryModel findById(long matrixId, long categoryId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find by Id CategoryModel"); // NOI18N
        
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
        this.registerOnActionDeleteCategory();
    }

    private void registerOnActionCreateCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action create CategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__CATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixId = actionTransferModel.getLong();
                    final String title = actionTransferModel.getString();
                    final CategoryModel categoryModel = this.create(matrixId, title);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__MATRIX);
                        actionTransferModel2.setObject(categoryModel);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);
                        
                        final ActionTransferModel actionTransferModel3 = new ActionTransferModel();
                        actionTransferModel3.setActionKey(ACTION__CREATE__FOLDERS);
                        final Folder folder = new Folder();
                        folder.register(Folder.EFolder.MATRIX_ID, categoryModel.getMatrixId());
                        folder.register(Folder.EFolder.CATEGORY_ID, categoryModel.getId());
                        actionTransferModel3.setObject(folder);
                        ActionFacade.INSTANCE.handle(actionTransferModel3);
                    });
                    pt.playFromStart();
                });
    }

    private void registerOnActionDeleteCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action delete CategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__DELETE__CATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final CategoryModel categoryModel = (CategoryModel) actionTransferModel.getObject();
                    final long matrixId = categoryModel.getMatrixId();
                    final long categoryId = categoryModel.getId();
                    this.delete(matrixId, categoryId);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(100.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REMOVE__CATEGORY);
                        actionTransferModel2.setObject(categoryModel);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);
                    });
                    pt.playFromStart();
                });
    }
    
}
