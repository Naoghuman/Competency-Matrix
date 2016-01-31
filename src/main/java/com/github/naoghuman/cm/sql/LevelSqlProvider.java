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
import com.github.naoghuman.cm.model.matrix.category.subcategory.level.LevelModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

/**
 *
 * @author PRo
 */
public class LevelSqlProvider implements IActionConfiguration, IEntityConfiguration, IRegisterActions {
    
    private static LevelSqlProvider instance = null;
    
    public static LevelSqlProvider getDefault() {
        if (instance == null) {
            instance = new LevelSqlProvider();
        }
        
        return instance;
    }
    
    private LevelSqlProvider() {}
    
    public void create(long matrixId, long categoryId, long subCategoryId, boolean isSingleTransaction) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create LevelModel"); // NOI18N
        
        final long id = System.currentTimeMillis();
        final LevelModel levelModel1 = ModelFacade.getDefaultLevel(id + 0L, matrixId, categoryId, subCategoryId, 1);
        DatabaseFacade.INSTANCE.getCrudService().create(levelModel1, isSingleTransaction);
        
        final LevelModel levelModel2 = ModelFacade.getDefaultLevel(id + 1L, matrixId, categoryId, subCategoryId, 2);
        DatabaseFacade.INSTANCE.getCrudService().create(levelModel2, isSingleTransaction);
        
        final LevelModel levelModel3 = ModelFacade.getDefaultLevel(id + 2L, matrixId, categoryId, subCategoryId, 3);
        DatabaseFacade.INSTANCE.getCrudService().create(levelModel3, isSingleTransaction);
        
        final LevelModel levelModel4 = ModelFacade.getDefaultLevel(id + 3L, matrixId, categoryId, subCategoryId, 4);
        DatabaseFacade.INSTANCE.getCrudService().create(levelModel4, isSingleTransaction);
    }
    
    public void delete(long levelId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete LevelModel: " + levelId); // NOI18N
        
        DatabaseFacade.INSTANCE.getCrudService().delete(LevelModel.class, levelId);
    }

    public void deleteAll(long matrixId, long categoryId, long subCategoryId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete all LevelModels"); // NOI18N
        
        // Find all
        final List<LevelModel> levelModels = this.findAll(matrixId, categoryId, subCategoryId);
        
        // Delete them
        for (LevelModel levelModel : levelModels) {
            final long levelId = levelModel.getId();
            this.delete(levelId);
        }
    }

    public List<LevelModel> findAll(long matrixId, long categoryId, long subCategoryId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all LevelModels"); // NOI18N
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(COLUMN_NAME__MATRIX_ID, matrixId);
        parameters.put(COLUMN_NAME__CATEGORY_ID, categoryId);
        parameters.put(COLUMN_NAME__SUBCATEGORY_ID, subCategoryId);
        
        final List<LevelModel> levelModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(LevelModel.class, NAMED_QUERY__NAME__LEVEL_FIND_ALL, parameters);
        Collections.sort(levelModels);
        
        return levelModels;
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in LevelSqlProvider"); // NOI18N
        
        this.registerOnActionUpdateLevel();
    }

    private void registerOnActionUpdateLevel() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action update LevelModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__UPDATE__LEVEL,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final LevelModel levelModel = (LevelModel) actionTransferModel.getObject();
                    DatabaseFacade.INSTANCE.getCrudService().update(levelModel);
                });
    }
    
}
