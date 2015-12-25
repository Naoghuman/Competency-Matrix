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
package com.github.naoghuman.cm.matrix;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import static com.github.naoghuman.cm.configuration.api.IActionConfiguration.ACTION__DELETE__MATRIX;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.dialog.api.DialogFacade;
import com.github.naoghuman.cm.matrix.category.CategoryPresenter;
import com.github.naoghuman.cm.matrix.category.CategoryView;
import com.github.naoghuman.cm.model.api.CategoryModel;
import com.github.naoghuman.cm.model.api.MatrixModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 *
 * @author PRo
 */
public class MatrixPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private Label lMatrix;
    @FXML private VBox vbCategories;
    
    private long matrixModelId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MatrixPresenter"); // NOI18N
        
        assert (lMatrix != null)      : "fx:id=\"lMatrix\" was not injected: check your FXML file 'Matrix.fxml'."; // NOI18N
        assert (vbCategories != null) : "fx:id=\"vbCategories\" was not injected: check your FXML file 'Matrix.fxml'."; // NOI18N
        
        this.registerActions();
    }
    
    public void initialize(MatrixModel matrixModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MatrixModel"); // NOI18N
        
        matrixModelId = matrixModel.getId();
        lMatrix.setText(matrixModel.getTitle());
        
        final CategoryModel categoryModel = ModelFacade.getDefaultCategoryModel(matrixModelId, "dummy"); // NOI18N
        this.onActionRefreshMatrix(categoryModel);
    }
    
    public void onActionCreateCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create CategoryModel"); // NOI18N
        
        final TextInputDialog dialog = DialogFacade.getNewCategoryDialog();
        final Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final String name = result.get().trim();
        if (name.isEmpty()) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__CREATE__CATEGORY);
        actionTransferModel.setString(name);
        actionTransferModel.setLong(matrixModelId);
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    public void onActionDeleteMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete Matrix"); // NOI18N
        
        final Alert alert = DialogFacade.getDeleteMatrixDialog();
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
        actionTransferModel.setLong(matrixModelId);
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    private void onActionRefreshMatrix(CategoryModel categoryModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action refresh Matrix"); // NOI18N
        //TODO scroll to categoryModel
        final long parentID = categoryModel.getParentId();
        final List<CategoryModel> categoryModels = SqlFacade.INSTANCE.getCategorySqlProvider().findAll(parentID);
        vbCategories.getChildren().clear();
        if (categoryModels.isEmpty()) {
            return;
        }
        
        categoryModels.stream().forEach((categoryModel2) -> {
            final CategoryView categoryView = new CategoryView();
            final CategoryPresenter categoryPresenter = categoryView.getRealPresenter();
            categoryPresenter.initialize(categoryModel2);
            
            vbCategories.getChildren().add(categoryView.getView());
        });
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in MatrixPresenter"); // NOI18N
        
        this.registerOnActionRefreshMatrix();
    }
    
    private void registerOnActionRefreshMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action refresh MatrixModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REFRESH__MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final CategoryModel categoryModel = (CategoryModel) actionTransferModel.getObject();
                    final long parentID = categoryModel.getParentId();
                    final boolean isEquals = new EqualsBuilder().append(parentID, matrixModelId).isEquals();
                    if (!isEquals) {
                        return;
                    }
                    
                    this.onActionRefreshMatrix(categoryModel);
                });
    }
    
}
