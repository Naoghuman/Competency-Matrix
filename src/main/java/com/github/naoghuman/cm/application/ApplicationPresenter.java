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
import com.github.naoghuman.cm.dialog.api.DialogProvider;
import com.github.naoghuman.cm.matrix.MatrixPresenter;
import com.github.naoghuman.cm.matrix.MatrixView;
import com.github.naoghuman.cm.matrix.category.subcategory.level.LevelPresenter;
import com.github.naoghuman.cm.matrix.category.subcategory.level.LevelView;
import com.github.naoghuman.cm.model.api.CategoryModel;
import com.github.naoghuman.cm.model.api.LevelModel;
import com.github.naoghuman.cm.model.api.MatrixModel;
import com.github.naoghuman.cm.model.api.SubCategoryModel;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.UtilFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 *
 * @author PRo
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private ListView lvOverview;
    @FXML private SplitPane spCompetencyMatrix;
    @FXML private TabPane tpCompetencyMatrix;
    @FXML private TabPane tpNavigation;
    
    private Window owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
        assert (lvOverview != null)         : "fx:id=\"lvOverview\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (spCompetencyMatrix != null) : "fx:id=\"spCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (tpCompetencyMatrix != null) : "fx:id=\"tpCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (tpNavigation != null)       : "fx:id=\"tpNavigation\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.initializeSplitPane();
        this.initializeListView();
        
        this.registerActions();
        
        this.onActionRefreshListView(null);
    }
    
    public void initialize(Window owner) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter with Window owner"); // NOI18N
        
        this.owner = owner;
    }
    
    private void initializeSplitPane() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize SplitPane"); // NOI18N
        
        SplitPane.setResizableWithParent(tpNavigation, Boolean.FALSE);
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
                transferModel.setActionKey(ACTION__OPEN__MATRIX);
                transferModel.setLong(matrixModel.getId());

                ActionFacade.INSTANCE.handle(transferModel);
            }
        });
    }
    
    public void onActionCreateMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create Matrix"); // NOI18N
        
        final TextInputDialog dialog = DialogProvider.getNewMatrixDialog();
        final Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final String name = result.get().trim();
        if (name.isEmpty()) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__CREATE__MATRIX);
        actionTransferModel.setString(name);
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    public void onActionDeleteMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete Matrix"); // NOI18N
        
        final MatrixModel matrixModel = (MatrixModel) lvOverview.getSelectionModel().getSelectedItem();
        if (matrixModel == null) {
            return;
        }
        
        final Alert alert = DialogProvider.getDeleteMatrixDialog();
        final Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final ButtonType buttonType = result.get();
        if (!buttonType.getButtonData().equals(ButtonType.YES.getButtonData())) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__DELETE__MATRIX);
        actionTransferModel.setLong(matrixModel.getId());
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    private void onActionOpenLevel(LevelModel levelModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show Level: " + levelModel.getId()); // NOI18N
        
        // Create dialog
        final String title = "Level " + levelModel.getLevel(); // NOI18N
        
        final LevelView levelView = new LevelView();
        final LevelPresenter levelPresenter = levelView.getRealPresenter();
        levelPresenter.initialize(levelModel);
        final Parent content = levelView.getView();
        
        final Dialog dialog = DialogProvider.getOpenLevelDialog(owner, title, content);
        
        // Check answer
        final Optional<ButtonType> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final ButtonType buttonType = result.get();
        if (!buttonType.getButtonData().equals(ButtonType.OK.getButtonData())) {
            return;
        }
        
        // Update LevelModel
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__UPDATE__LEVEL);
        actionTransferModel.setObject(levelPresenter.getLevelModel());
        ActionFacade.INSTANCE.handle(actionTransferModel);
        
        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
        actionTransferModel2.setActionKey(ACTION__REFRESH__SUBCATEGORY);
        actionTransferModel2.setObject(levelPresenter.getLevelModel());
        ActionFacade.INSTANCE.handle(actionTransferModel2);
    }
    
    private void onActionOpenMatrix(long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show Matrix: " + matrixId); // NOI18N
        
        // Check if the CompetencyMatrix is always open
        for (Tab tab : tpCompetencyMatrix.getTabs()) {
            final boolean isEquals = new EqualsBuilder().append(tab.getId(), String.valueOf(matrixId)).isEquals();
            if (isEquals) {
                tpCompetencyMatrix.getSelectionModel().select(tab);
                return;
            }
        }
        
        // Load MatrixModel
        final MatrixModel matrixModel = SqlFacade.INSTANCE.getMatrixSqlProvider().findById(matrixId);
        if (matrixModel == null) {
            return;
        }
        
        // Show the new model
        this.openMatrix(matrixModel);
    }
    
    private void openMatrix(MatrixModel matrixModel) {
        final Tab tab = new Tab();
        tab.setClosable(Boolean.TRUE);
        tab.setId(String.valueOf(matrixModel.getId()));
        tab.setText(matrixModel.getTitle());
        
        final MatrixView matrixView = new MatrixView();
        final MatrixPresenter matrixPresenter = matrixView.getRealPresenter();
        matrixPresenter.initialize(matrixModel);
        tab.setContent(matrixView.getView());
        tab.setUserData(matrixPresenter);
        
        tpCompetencyMatrix.getTabs().add(tab);
        tpCompetencyMatrix.getSelectionModel().select(tab);
    }
    
    private void onActionRefreshListView(MatrixModel matrixModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action refresh ListView"); // NOI18N

        final List<MatrixModel> matrixModels = SqlFacade.INSTANCE.getMatrixSqlProvider().findAll();
        lvOverview.getItems().clear();
        lvOverview.getItems().addAll(matrixModels);
        lvOverview.getSelectionModel().select(matrixModel);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
        
        SqlFacade.INSTANCE.registerActions();
        
        this.registerOnActionCreateFolders();
        this.registerOnActionOpenLevel();
        this.registerOnActionOpenMatrix();
        this.registerOnActionRefreshCategory();
        this.registerOnActionRefreshMatrix();
        this.registerOnActionRefreshOverviewMatrix();
        this.registerOnActionRefreshSubCategory();
        this.registerOnActionRemoveCategory();
        this.registerOnActionRemoveMatrix();
        this.registerOnActionRemoveSubCategory();
    }
    
    private void registerOnActionCreateFolders() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action create Folders"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__FOLDER,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final Folder folder = (Folder) actionTransferModel.getObject();
                    UtilFacade.INSTANCE.getFolderHelper().create(folder);
                });
    }
    
    private void registerOnActionOpenLevel() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action open Level"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__OPEN__LEVEL,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final LevelModel levelModel = (LevelModel) actionTransferModel.getObject();
                    this.onActionOpenLevel(levelModel);
                });
    }
    
    private void registerOnActionOpenMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action open Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__OPEN__MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixId = actionTransferModel.getLong();
                    this.onActionOpenMatrix(matrixId);
                });
    }
    
    private void registerOnActionRefreshCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action refresh CategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REFRESH__CATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final SubCategoryModel subCategoryModel = (SubCategoryModel) actionTransferModel.getObject();
                    final long matrixId = subCategoryModel.getMatrixId();
                    
                    for (Tab tab : tpCompetencyMatrix.getTabs()) {
                        final boolean isEquals = new EqualsBuilder().append(tab.getId(), String.valueOf(matrixId)).isEquals();
                        if (!isEquals) {
                            continue;
                        }
                        
                        final MatrixPresenter matrixPresenter = (MatrixPresenter) tab.getUserData();
                        matrixPresenter.onActionRefreshCategory(subCategoryModel); 
                    }
                });
    }
    
    private void registerOnActionRefreshMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action refresh MatrixModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REFRESH__MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final CategoryModel categoryModel = (CategoryModel) actionTransferModel.getObject();
                    final long matrixId = categoryModel.getMatrixId();
                    
                    for (Tab tab : tpCompetencyMatrix.getTabs()) {
                        final boolean isEquals = new EqualsBuilder().append(tab.getId(), String.valueOf(matrixId)).isEquals();
                        if (!isEquals) {
                            continue;
                        }
                        
                        final MatrixPresenter matrixPresenter = (MatrixPresenter) tab.getUserData();
                        matrixPresenter.onActionRefreshMatrix(categoryModel); 
                    }
                });
    }
    
    private void registerOnActionRefreshOverviewMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action refresh overview Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(ACTION__REFRESH__MATRIX_NAVIGATION,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final MatrixModel matrixModel = (MatrixModel) actionTransferModel.getObject();
                    this.onActionRefreshListView(matrixModel);
                });
    }
    
    private void registerOnActionRefreshSubCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action refresh SubCategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REFRESH__SUBCATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final LevelModel levelModel = (LevelModel) actionTransferModel.getObject();
                    final long matrixId = levelModel.getMatrixId();
                    
                    for (Tab tab : tpCompetencyMatrix.getTabs()) {
                        final boolean isEquals = new EqualsBuilder().append(tab.getId(), String.valueOf(matrixId)).isEquals();
                        if (!isEquals) {
                            continue;
                        }
                        
                        final MatrixPresenter matrixPresenter = (MatrixPresenter) tab.getUserData();
                        matrixPresenter.onActionRefreshSubCategory(levelModel); 
                    }
                });
    }

    private void registerOnActionRemoveCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action remove CategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REMOVE__CATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final CategoryModel categoryModel = (CategoryModel) actionTransferModel.getObject();
                    final long matrixId = categoryModel.getMatrixId();
                    
                    for (Tab tab : tpCompetencyMatrix.getTabs()) {
                        final boolean isEquals = new EqualsBuilder().append(tab.getId(), String.valueOf(matrixId)).isEquals();
                        if (!isEquals) {
                            continue;
                        }
                        
                        final MatrixPresenter matrixPresenter = (MatrixPresenter) tab.getUserData();
                        matrixPresenter.onActionRefreshMatrix(categoryModel); 
                    }
                });
    }
    
    private void registerOnActionRemoveMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action remove Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REMOVE__MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final long matrixId = actionTransferModel.getLong();
                    
                    // Check if the Matrix is open
                    for (Iterator<Tab> iterator = tpCompetencyMatrix.getTabs().iterator(); iterator.hasNext();) {
                        final Tab tab = iterator.next();
                        final boolean isEquals = new EqualsBuilder().append(tab.getId(), String.valueOf(matrixId)).isEquals();
                        if (!isEquals) {
                            continue;
                        }

                        // Remove the tab
                        int index = tpCompetencyMatrix.getTabs().indexOf(tab);
                        tpCompetencyMatrix.getTabs().remove(tab);

                        if (tpCompetencyMatrix.getTabs().isEmpty()) {
                            return;
                        }

                        // Select new tab
                        index = index < tpCompetencyMatrix.getTabs().size() ? index : tpCompetencyMatrix.getTabs().size() - 1;
                        if (index < 0) {
                            index = 0;
                        }

                        tpCompetencyMatrix.getSelectionModel().select(index);
                    }
                });
    }

    private void registerOnActionRemoveSubCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action remove SubCategoryModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REMOVE__SUBCATEGORY,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final SubCategoryModel subCategoryModel = (SubCategoryModel) actionTransferModel.getObject();
                    final long matrixId = subCategoryModel.getMatrixId();
                    
                    for (Tab tab : tpCompetencyMatrix.getTabs()) {
                        final boolean isEquals = new EqualsBuilder().append(tab.getId(), String.valueOf(matrixId)).isEquals();
                        if (!isEquals) {
                            continue;
                        }
                        
                        final MatrixPresenter matrixPresenter = (MatrixPresenter) tab.getUserData();
                        matrixPresenter.onActionRefreshCategory(subCategoryModel); 
                    }
                });
    }
    
}
