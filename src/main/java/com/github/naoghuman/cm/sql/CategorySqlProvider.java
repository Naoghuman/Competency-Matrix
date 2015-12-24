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
import com.github.naoghuman.cm.model.api.ModelFacade;
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

    private CategoryModel createCategory(long parentID, String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create CategoryModel"); // NOI18N
        
        final CategoryModel categoryModel = ModelFacade.getDefaultCategoryModel(parentID, title);
        DatabaseFacade.INSTANCE.getCrudService().create(categoryModel);
        
        return categoryModel;
    }

    public List<CategoryModel> findAll(long parentId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all CategoryModels"); // NOI18N
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(COLUMN_NAME__PARENT_ID, parentId);
        
        final List<CategoryModel> categoryModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(CategoryModel.class, NAMED_QUERY__NAME__CATEGORY_FIND_ALL, parameters);
        Collections.sort(categoryModels);
        
        return categoryModels;
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
                    final String title = actionTransferModel.getString();
                    final long parentId = actionTransferModel.getLong();
                    final CategoryModel categoryModel = this.createCategory(parentId, title);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__MATRIX);
                        actionTransferModel2.setObject(categoryModel);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);
                    });
                    pt.playFromStart();
                });
    }

    private void registerOnActionDeleteCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action delete CategoryModel"); // NOI18N
        
    }
    
}
