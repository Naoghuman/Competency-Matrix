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
package com.github.naoghuman.cm.sql.matrix;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.UtilFacade;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
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

    private MatrixModel create(String title) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Create MatrixModel"); // NOI18N
        
        final MatrixModel matrixModel = ModelFacade.getDefaultMatrix(title);
        DatabaseFacade.INSTANCE.getCrudService().create(matrixModel);
        
        return matrixModel;
    }
    
    private void delete(long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Delete MatrixModel with all associated Models"); // NOI18N
        
        // Delete MatrixModel
        DatabaseFacade.INSTANCE.getCrudService().delete(MatrixModel.class, matrixId);
        
        // Delete all CategoryModels
//        SqlFacade.INSTANCE.getCategorySqlProvider().deleteAll(matrixId);
    }

    public List<MatrixModel> findAll() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find all"); // NOI18N
        
        final List<MatrixModel> matrixModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(MatrixModel.class, NAMED_QUERY__NAME__MATRIX_FIND_ALL);
        Collections.sort(matrixModels);
        
        return matrixModels;
    }

    public MatrixModel findById(long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Find by Id MatrixModel"); // NOI18N
        
        return DatabaseFacade.INSTANCE.getCrudService().findById(MatrixModel.class, matrixId);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in MatrixSqlProvider"); // NOI18N
        
        this.registerOnActionCreateMatrix();
        this.registerOnActionDeleteMatrix();
        this.registerOnActionUpdateMatrix();
    }

    private void registerOnActionCreateMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action create MatrixModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__MATRIX,
                (ActionEvent ae) -> {
                    final TransferData transferData = (TransferData) ae.getSource();
                    final String title = transferData.getString();
                    final MatrixModel matrixModel = this.create(title);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        ActionFacade.INSTANCE.handle(ACTION__REFRESH__MATRIX_OVERVIEW);
                        
                        final TransferData transferData2 = new TransferData();
                        transferData2.setActionId(ACTION__CREATE__FOLDER);
                        final Folder folder = UtilFacade.INSTANCE.getDefaultFolder();
                        folder.register(Folder.EPath.MATRIX, matrixModel.getId());
                        transferData2.setObject(folder);
                        ActionFacade.INSTANCE.handle(transferData2);
                    });
                    pt.playFromStart();
                });
    }

    private void registerOnActionDeleteMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action delete MatrixModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__DELETE__MATRIX,
                (ActionEvent ae) -> {
                    final TransferData transferData = (TransferData) ae.getSource();
                    final long matrixId = transferData.getLong();
                    this.delete(matrixId);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(100.0d));
                    pt.setOnFinished((ActionEvent event) -> {
//                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
//                        actionTransferModel2.setActionKey(ACTION__REFRESH__MATRIX_OVERVIEW);
//                        actionTransferModel2.setObject(null);
//                        ActionFacade.INSTANCE.handle(actionTransferModel2);

//                        final ActionTransferModel actionTransferModel3 = new ActionTransferModel();
//                        actionTransferModel3.setActionKey(ACTION__REMOVE__MATRIX);
//                        actionTransferModel3.setLong(matrixId);
//                        ActionFacade.INSTANCE.handle(actionTransferModel3);
                    });
                    pt.playFromStart();
                });
    }

    private void registerOnActionUpdateMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action update MatrixModel"); // NOI18N
        
//        ActionFacade.INSTANCE.register(
//                ACTION__UPDATE__MATRIX,
//                (ActionEvent ae) -> {
//                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
//                    final MatrixModel matrixModel = (MatrixModel) actionTransferModel.getObject();
//                    DatabaseFacade.INSTANCE.getCrudService().update(matrixModel);
//                });
    }
    
}
