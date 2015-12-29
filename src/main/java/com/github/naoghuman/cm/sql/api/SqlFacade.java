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
package com.github.naoghuman.cm.sql.api;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.sql.CategorySqlProvider;
import com.github.naoghuman.cm.sql.LevelSqlProvider;
import com.github.naoghuman.cm.sql.MatrixSqlProvider;
import com.github.naoghuman.cm.sql.SubCategorySqlProvider;
import de.pro.lib.logger.api.LoggerFacade;

/**
 *
 * @author PRo
 */
public enum SqlFacade implements IActionConfiguration, IRegisterActions {
    
    INSTANCE;
    
    public CategorySqlProvider getCategorySqlProvider() {
        return CategorySqlProvider.getDefault();
    }
    
    public LevelSqlProvider getLevelSqlProvider() {
        return LevelSqlProvider.getDefault();
    }
    
    public MatrixSqlProvider getMatrixSqlProvider() {
        return MatrixSqlProvider.getDefault();
    }
    
    public SubCategorySqlProvider getSubCategorySqlProvider() {
        return SubCategorySqlProvider.getDefault();
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in SqlFacade"); // NOI18N
        
        CategorySqlProvider.getDefault().registerActions();
        LevelSqlProvider.getDefault().registerActions();
        MatrixSqlProvider.getDefault().registerActions();
        SubCategorySqlProvider.getDefault().registerActions();
    }
    
}
