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
import static com.github.naoghuman.cm.configuration.api.IEntityConfiguration.COLUMN_NAME__CATEGORY_ID;
import static com.github.naoghuman.cm.configuration.api.IEntityConfiguration.COLUMN_NAME__MATRIX_ID;
import static com.github.naoghuman.cm.configuration.api.IEntityConfiguration.NAMED_QUERY__NAME__SUBCATEGORY_FIND_ALL;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.api.LevelModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import com.github.naoghuman.cm.model.api.SubCategoryModel;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;

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
    
    public void create(long id, long matrixId, long categoryId, long subCategoryId, int level) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create LevelModel"); // NOI18N
        
        final LevelModel levelModel = ModelFacade.getDefaultLevel(id, matrixId, categoryId, subCategoryId, level);
        DatabaseFacade.INSTANCE.getCrudService().create(levelModel);
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
        
    }
}
