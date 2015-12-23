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

    private MatrixModel createCompetencyMatrix(String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create Competency-Matrix"); // NOI18N
        
        final MatrixModel matrixModel = ModelFacade.getDefaultMatrixModel();
        matrixModel.setTitle(title);
        
        DatabaseFacade.INSTANCE.getCrudService().create(matrixModel);
        
        return matrixModel;
    }
    
    private void deleteCompetencyMatrix(long matrixModelID) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete Competency-Matrix"); // NOI18N
        
        DatabaseFacade.INSTANCE.getCrudService().delete(MatrixModel.class, matrixModelID);
    }

    public List<MatrixModel> findAll() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all Competency-Matrix"); // NOI18N
        
        final List<MatrixModel> matrixModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(MatrixModel.class, NAMED_QUERY__NAME__MATRIX_FIND_ALL);
        Collections.sort(matrixModels);
        
        return matrixModels;
    }

    public MatrixModel findById(long matrixModelID) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find by ID Competency-Matrix"); // NOI18N
        
        return DatabaseFacade.INSTANCE.getCrudService().findById(MatrixModel.class, matrixModelID);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in MatrixSqlProvider"); // NOI18N
        
        this.registerOnActionCreateCompentencyMatrix();
        this.registerOnActionDeleteCompentencyMatrix();
    }

    private void registerOnActionCreateCompentencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register action create Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final String title = actionTransferModel.getString();
                    final MatrixModel matrixModel = this.createCompetencyMatrix(title);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__OVERVIEW_COMPETENCY_MATRIX);
                        actionTransferModel2.setObject(matrixModel);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);

                        final ActionTransferModel actionTransferModel3 = new ActionTransferModel();
                        actionTransferModel3.setActionKey(ACTION__OPEN__COMPETENCY_MATRIX);
                        actionTransferModel3.setLong(matrixModel.getId());
                        ActionFacade.INSTANCE.handle(actionTransferModel3);
                    });
                    pt.playFromStart();
                });
    }

    private void registerOnActionDeleteCompentencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register action delete Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__DELETE__COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixModelID = actionTransferModel.getLong();
                    this.deleteCompetencyMatrix(matrixModelID);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(100.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__OVERVIEW_COMPETENCY_MATRIX);
                        actionTransferModel2.setObject(null);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);

                        final ActionTransferModel actionTransferModel3 = new ActionTransferModel();
                        actionTransferModel3.setActionKey(ACTION__REMOVE__COMPETENCY_MATRIX);
                        actionTransferModel3.setLong(matrixModelID);
                        ActionFacade.INSTANCE.handle(actionTransferModel3);
                    });
                    pt.playFromStart();
                });
    }
    
}