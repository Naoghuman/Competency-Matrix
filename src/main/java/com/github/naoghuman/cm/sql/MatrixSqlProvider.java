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

import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import com.github.naoghuman.cm.model.api.MatrixModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author PRo
 */
public final class MatrixSqlProvider implements IEntityConfiguration {
    
    private static MatrixSqlProvider instance = null;
    
    public static MatrixSqlProvider getDefault() {
        if (instance == null) {
            instance = new MatrixSqlProvider();
        }
        
        return instance;
    }
    
    private MatrixSqlProvider() {}

    public MatrixModel createCompetencyMatrix(String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create Competency-Matrix"); // NOI18N
        
        final MatrixModel matrixModel = ModelFacade.getDefaultMatrixModel();
        matrixModel.setTitle(title);
        
        DatabaseFacade.INSTANCE.getCrudService().create(matrixModel);
        
        return matrixModel;
    }

    public List<MatrixModel> findAll() {
        final List<MatrixModel> matrixModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(MatrixModel.class, NAMED_QUERY__NAME__MATRIX_FIND_ALL);
        Collections.sort(matrixModels);
        
        return matrixModels;
    }

    public MatrixModel findById(long matrixModelID) {
        return DatabaseFacade.INSTANCE.getCrudService().findById(MatrixModel.class, matrixModelID);
    }
    
}
