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
import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.api.CategoryModel;
import com.github.naoghuman.cm.model.api.LevelModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import com.github.naoghuman.cm.model.api.SubCategoryModel;
import com.github.naoghuman.cm.sql.api.SqlFacade;
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
public class SubCategorySqlProvider implements IActionConfiguration, IEntityConfiguration, IRegisterActions {
    
    private static SubCategorySqlProvider instance = null;
    
    public static SubCategorySqlProvider getDefault() {
        if (instance == null) {
            instance = new SubCategorySqlProvider();
        }
        
        return instance;
    }
    
    private SubCategorySqlProvider() {}

    private SubCategoryModel create(long matrixId, long categoryId, String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create SubCategoryModel with LevelModels"); // NOI18N
        
        // Create SubCategoryModel
        final SubCategoryModel subCategoryModel = ModelFacade.getDefaultSubCategory(matrixId, categoryId, title);
        DatabaseFacade.INSTANCE.getCrudService().create(subCategoryModel);
        
        // Create LevelModels
        final long id = System.currentTimeMillis();
        final long subCategoryId = subCategoryModel.getId();
        SqlFacade.INSTANCE.getLevelSqlProvider().create(id + 0L, matrixId, categoryId, subCategoryId, 1);
        SqlFacade.INSTANCE.getLevelSqlProvider().create(id + 1L, matrixId, categoryId, subCategoryId, 2);
        SqlFacade.INSTANCE.getLevelSqlProvider().create(id + 2L, matrixId, categoryId, subCategoryId, 3);
        SqlFacade.INSTANCE.getLevelSqlProvider().create(id + 3L, matrixId, categoryId, subCategoryId, 4);
        
        return subCategoryModel;
    }
    
    public void delete(long subCategoryId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete SubCategoryModel"); // NOI18N
        
        DatabaseFacade.INSTANCE.getCrudService().delete(SubCategoryModel.class, subCategoryId);
        
        // TODO delete all levels from subcategory
    }

    public List<SubCategoryModel> findAll(long matrixId, long categoryId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all SubCategoryModels"); // NOI18N
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(COLUMN_NAME__MATRIX_ID, matrixId);
        parameters.put(COLUMN_NAME__CATEGORY_ID, categoryId);
        
        final List<SubCategoryModel> subCategoryModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(SubCategoryModel.class, NAMED_QUERY__NAME__SUBCATEGORY_FIND_ALL, parameters);
        Collections.sort(subCategoryModels);
        
        return subCategoryModels;
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in SubCategorySqlProvider"); // NOI18N
        
        this.registerOnActionCreateSubCategory();
        this.registerOnActionDeleteSubCategory();
    }

    private void registerOnActionCreateSubCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action create SubCategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__SUBCATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    
                    final CategoryModel categoryModel = (CategoryModel) actionTransferModel.getObject();
                    final long matrixId = categoryModel.getMatrixId();
                    final long categoryId = categoryModel.getId();
                    
                    final String title = actionTransferModel.getString();
                    final SubCategoryModel subCategoryModel = this.create(matrixId, categoryId, title);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__CATEGORY);
                        actionTransferModel2.setObject(subCategoryModel);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);
                    });
                    pt.playFromStart();
                });
    }

    private void registerOnActionDeleteSubCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action delete SubCategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__DELETE__SUBCATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final SubCategoryModel subCategoryModel = (SubCategoryModel) actionTransferModel.getObject();
                    this.delete(subCategoryModel.getId());
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(100.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REMOVE__SUBCATEGORY);// TODO REMOVE
                        actionTransferModel2.setObject(subCategoryModel);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);
                    });
                    pt.playFromStart();
                });
    }
    
}
