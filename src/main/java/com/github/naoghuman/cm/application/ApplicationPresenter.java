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
package com.github.naoghuman.cm.application;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.dialog.api.DialogFacade;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;

/**
 *
 * @author PRo
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private TabPane tpCompetencyMatrix;
    @FXML private TreeView tvCompetencyMatrix;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
        assert (tpCompetencyMatrix != null) : "fx:id=\"tpCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (tvCompetencyMatrix != null) : "fx:id=\"tvCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.registerActions();
    }
    
    public void onActionCreateCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create new Competency-Matrix"); // NOI18N
        
        final TextInputDialog dialog = DialogFacade.getNewCompetencyMatrixDialog();
        final Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final String name = result.get().trim();
        if (name.isEmpty()) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__CREATE__COMPETENCY_MATRIX);
        actionTransferModel.setString(name);
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    private void registerOnActionOpenCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action open Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__OPEN__COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixModelID = actionTransferModel.getLong();
                    System.out.println("open in tab: " + matrixModelID);
                    /*
                    TODO
                     - load the model
                     - create tab.
                     - show and select it
                    */
                });
    }
    
    private void registerOnActionRefreshOverviewCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action refresh overview Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REFRESH__OVERVIEW_COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixModelID = actionTransferModel.getLong();
                    System.out.println("refresh and select: " + matrixModelID);
                    /*
                    TODO
                     - refresh the overview
                        - load all cms with id, title, generationTime
                     - select id
                    */
                });
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
        
        SqlFacade.INSTANCE.registerActions();
        
        this.registerOnActionOpenCompetencyMatrix();
        this.registerOnActionRefreshOverviewCompetencyMatrix();
    }
    
}
