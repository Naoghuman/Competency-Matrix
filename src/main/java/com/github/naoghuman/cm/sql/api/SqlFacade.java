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
package com.github.naoghuman.cm.sql.api;

import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.sql.category.CategorySqlProvider;
import com.github.naoghuman.cm.sql.matrix.MatrixSqlProvider;
import com.github.naoghuman.cm.sql.notes.NotesSqlProvider;
import com.github.naoghuman.lib.logger.api.LoggerFacade;

/**
 *
 * @author PRo
 */
public enum SqlFacade implements IRegisterActions {
    
    INSTANCE;

    public CategorySqlProvider getCategorySqlProvider() {
        return CategorySqlProvider.getDefault();
    }

    public MatrixSqlProvider getMatrixSqlProvider() {
        return MatrixSqlProvider.getDefault();
    }

    public NotesSqlProvider getNotesSqlProvider() {
        return NotesSqlProvider.getDefault();
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in SqlFacade"); // NOI18N
        
        CategorySqlProvider.getDefault().registerActions();
        MatrixSqlProvider.getDefault().registerActions();
        NotesSqlProvider.getDefault().registerActions();
    }
    
}
