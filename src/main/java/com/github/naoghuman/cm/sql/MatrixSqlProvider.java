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
import com.github.naoghuman.cm.model.api.MatrixModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public final class MatrixSqlProvider implements IActionConfiguration, IEntityConfiguration, IRegisterActions {
    
    private static MatrixSqlProvider instance = null;
    
    public static MatrixSqlProvider getDefault() {
        if (instance == null) {
            instance = new MatrixSqlProvider();
        }
        
        return instance;
    }
    
    private MatrixSqlProvider() {}

    private MatrixModel createMatrix(String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create MatrixModel"); // NOI18N
        
        final MatrixModel matrixModel = ModelFacade.getDefaultMatrixModel(title);
        DatabaseFacade.INSTANCE.getCrudService().create(matrixModel);
        
        return matrixModel;
    }
    
    private void deleteMatrix(long matrixModelId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete MatrixModel"); // NOI18N
        
        DatabaseFacade.INSTANCE.getCrudService().delete(MatrixModel.class, matrixModelId);
    }

    public List<MatrixModel> findAll() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all MatrixModels"); // NOI18N
        
        final List<MatrixModel> matrixModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(MatrixModel.class, NAMED_QUERY__NAME__MATRIX_FIND_ALL);
        Collections.sort(matrixModels);
        
        return matrixModels;
    }

    public MatrixModel findById(long matrixModelID) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find by ID MatrixModel"); // NOI18N
        
        return DatabaseFacade.INSTANCE.getCrudService().findById(MatrixModel.class, matrixModelID);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in MatrixSqlProvider"); // NOI18N
        
        this.registerOnActionCreateMatrix();
        this.registerOnActionDeleteMatrix();
    }

    private void registerOnActionCreateMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action create MatrixModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final String name = actionTransferModel.getString();
                    final MatrixModel matrixModel = this.createMatrix(name);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__OVERVIEW_MATRIX);
                        actionTransferModel2.setObject(matrixModel);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);

                        final ActionTransferModel actionTransferModel3 = new ActionTransferModel();
                        actionTransferModel3.setActionKey(ACTION__OPEN__MATRIX);
                        actionTransferModel3.setLong(matrixModel.getId());
                        ActionFacade.INSTANCE.handle(actionTransferModel3);
                    });
                    pt.playFromStart();
                });
    }

    private void registerOnActionDeleteMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action delete MatrixModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__DELETE__MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixModelId = actionTransferModel.getLong();
                    this.deleteMatrix(matrixModelId);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(100.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__OVERVIEW_MATRIX);
                        actionTransferModel2.setObject(null);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);

                        final ActionTransferModel actionTransferModel3 = new ActionTransferModel();
                        actionTransferModel3.setActionKey(ACTION__REMOVE__MATRIX);
                        actionTransferModel3.setLong(matrixModelId);
                        ActionFacade.INSTANCE.handle(actionTransferModel3);
                    });
                    pt.playFromStart();
                });
    }
    
}
