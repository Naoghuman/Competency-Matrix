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
import com.github.naoghuman.cm.model.api.MatrixModel;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author PRo
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private ListView lvOverview;
    @FXML private SplitPane spCompetencyMatrix;
    @FXML private TabPane tpCompetencyMatrix;
    @FXML private VBox vbCompetencyMatrix;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
        assert (lvOverview != null)         : "fx:id=\"lvOverview\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (spCompetencyMatrix != null) : "fx:id=\"spCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (tpCompetencyMatrix != null) : "fx:id=\"tpCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (vbCompetencyMatrix != null) : "fx:id=\"vbCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.initializeSplitPane();
        this.initializeListView();
        
        this.registerActions();
        
        this.onActionRefreshListView(null);
    }
    
    private void initializeSplitPane() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize SplitPane"); // NOI18N
        
        SplitPane.setResizableWithParent(vbCompetencyMatrix, Boolean.FALSE);
    }
    
    private void initializeListView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ListView"); // NOI18N
        
        lvOverview.setCellFactory(new Callback<ListView<MatrixModel>, ListCell<MatrixModel>>() {

            @Override
            public ListCell<MatrixModel> call(ListView<MatrixModel> param) {
                return new ListCell<MatrixModel>() {
                    @Override
                    public void updateItem(MatrixModel item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (item != null) {
//                            this.setGraphic(item.getView());
                            this.setText(item.getTitle());
                        } else {
//                            this.setGraphic(null);
                            this.setText(null);
                        }
                    }
                };
            }
        });
        
        lvOverview.setOnMouseClicked((MouseEvent event) -> {
            if (lvOverview.getItems().isEmpty()) {
                return;
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2) {
                final MatrixModel matrixModel = (MatrixModel) lvOverview.getSelectionModel().getSelectedItem();
                final ActionTransferModel transferModel = new ActionTransferModel();
                transferModel.setActionKey(ACTION__OPEN__COMPETENCY_MATRIX);
                transferModel.setLong(matrixModel.getId());

                ActionFacade.INSTANCE.handle(transferModel);
            }
        });
    }
    
    public void onActionCreateCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create Competency-Matrix"); // NOI18N
        
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
    
    public void onActionDeleteCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete Competency-Matrix"); // NOI18N
        
        final MatrixModel matrixModel = (MatrixModel) lvOverview.getSelectionModel().getSelectedItem();
        if (matrixModel == null) {
            return;
        }
        
        final Alert alert = DialogFacade.getDeleteCompetencyMatrixDialog();
        final Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final ButtonType buttonType = result.get();
        if (!buttonType.getButtonData().equals(ButtonType.YES.getButtonData())) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__DELETE__COMPETENCY_MATRIX);
        actionTransferModel.setLong(matrixModel.getId());
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    private void onActionRefreshListView(MatrixModel matrixModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action refresh ListView"); // NOI18N

        final List<MatrixModel> matrixModels = SqlFacade.INSTANCE.getMatrixSqlProvider().findAll();
        lvOverview.getItems().clear();
        lvOverview.getItems().addAll(matrixModels);
        lvOverview.getSelectionModel().select(matrixModel);
    }
    
    private void openCompetencyMatrix(long matrixModelID) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show CompetencyMatrix: " + matrixModelID); // NOI18N
        
        // Check if the CompetencyMatrix is always open
        for (Tab tab : tpCompetencyMatrix.getTabs()) {
            if (tab.getId().equals(String.valueOf(matrixModelID))) {
                tpCompetencyMatrix.getSelectionModel().select(tab);
                return;
            }
        }
        
        // Load MatrixModel
        final MatrixModel matrixModel = SqlFacade.INSTANCE.getMatrixSqlProvider().findById(matrixModelID);
        if (matrixModel == null) {
            return;
        }
        
        // Show the new model
        this.openCompetencyMatrix(matrixModel);
    }
    
    private void openCompetencyMatrix(MatrixModel matrixModel) {
        final Tab tab = new Tab();
        tab.setClosable(Boolean.TRUE);
        // load view for tab
//        tab.setContent(null);
        tab.setId(String.valueOf(matrixModel.getId()));
        tab.setText(matrixModel.getTitle());
        
        tpCompetencyMatrix.getTabs().add(tab);
        tpCompetencyMatrix.getSelectionModel().select(tab);
    }
    
    private void registerOnActionOpenCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action open Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__OPEN__COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixModelID = actionTransferModel.getLong();
                    this.openCompetencyMatrix(matrixModelID);
                });
    }
    
    private void registerOnActionRefreshOverviewCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action refresh overview Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REFRESH__OVERVIEW_COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final MatrixModel matrixModel = (MatrixModel) actionTransferModel.getObject();
                    this.onActionRefreshListView(matrixModel);
                });
    }
    
    private void registerOnActionRemoveCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action close Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REMOVE__COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixModelID = actionTransferModel.getLong();
                    this.removeCompetencyMatrix(matrixModelID);
                });
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
        
        SqlFacade.INSTANCE.registerActions();
        
        this.registerOnActionOpenCompetencyMatrix();
        this.registerOnActionRefreshOverviewCompetencyMatrix();
        this.registerOnActionRemoveCompetencyMatrix();
    }
    
    private void removeCompetencyMatrix(long matrixModelID) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Remove CompetencyMatrix: " + matrixModelID); // NOI18N
        
        // Check if the CompetencyMatrix is open
        for (Iterator<Tab> iterator = tpCompetencyMatrix.getTabs().iterator(); iterator.hasNext();) {
            final Tab tab = iterator.next();
            if (tab.getId().equals(String.valueOf(matrixModelID))) {
                int index = tpCompetencyMatrix.getTabs().indexOf(tab);
                tpCompetencyMatrix.getTabs().remove(tab);
                
                if (tpCompetencyMatrix.getTabs().isEmpty()) {
                    return;
                }
                
                index = index < tpCompetencyMatrix.getTabs().size() ? index : tpCompetencyMatrix.getTabs().size() - 1;
                if (index < 0) {
                    index = 0;
                }
                
                tpCompetencyMatrix.getSelectionModel().select(index);
            }
        }
    }
    
}
