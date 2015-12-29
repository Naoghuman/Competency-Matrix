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
package com.github.naoghuman.cm.matrix.category;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.dialog.api.DialogProvider;
import com.github.naoghuman.cm.matrix.category.subcategory.SubCategoryPresenter;
import com.github.naoghuman.cm.matrix.category.subcategory.SubCategoryView;
import com.github.naoghuman.cm.model.api.CategoryModel;
import com.github.naoghuman.cm.model.api.ModelFacade;
import com.github.naoghuman.cm.model.api.SubCategoryModel;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

/**
 *
 * @author PRo
 */
public class CategoryPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private Label lCategory;
    @FXML private VBox vbSubCategories;
    
    private CategoryModel categoryModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize CategoryPresenter"); // NOI18N
        
        assert (lCategory != null)  : "fx:id=\"lCategory\" was not injected: check your FXML file 'Category.fxml'."; // NOI18N
        assert (vbSubCategories != null)  : "fx:id=\"vbSubCategories\" was not injected: check your FXML file 'Category.fxml'."; // NOI18N
        
        this.registerActions();
    }
    
    public void initialize(CategoryModel categoryModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize CategoryModel"); // NOI18N
        
        this.categoryModel = categoryModel;
        
        lCategory.setText(categoryModel.getTitle());
        
        final long matrixId = this.categoryModel.getMatrixId();
        final long categoryId = this.categoryModel.getId();
        final SubCategoryModel subCategoryModel = ModelFacade.getDefaultSubCategory(matrixId, categoryId, "dummy"); // NOI18N
        this.onActionRefreshCategory(subCategoryModel);
    }
    
    public void onActionCreateSubCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create SubCategory"); // NOI18N
        
        final TextInputDialog dialog = DialogProvider.getNewSubCategoryDialog();
        final Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final String name = result.get().trim();
        if (name.isEmpty()) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__CREATE__SUBCATEGORY);
        actionTransferModel.setString(name);
        actionTransferModel.setObject(categoryModel);
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    public void onActionDeleteCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete Category"); // NOI18N
        
        final Alert alert = DialogProvider.getDeleteCategoryDialog();
        final Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final ButtonType buttonType = result.get();
        if (!buttonType.getButtonData().equals(ButtonType.YES.getButtonData())) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__DELETE__CATEGORY);
        actionTransferModel.setObject(categoryModel);
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }

    public void onActionRefreshCategory(SubCategoryModel subCategoryModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action refresh CategoryModel"); // NOI18N
        
        final List<SubCategoryModel> subCategoryModels = SqlFacade.INSTANCE.getSubCategorySqlProvider().findAll(
                subCategoryModel.getMatrixId(), subCategoryModel.getCategoryId());
        vbSubCategories.getChildren().clear();
        if (subCategoryModels.isEmpty()) {
            return;
        }
        
        subCategoryModels.stream().forEach((subCategoryModel2) -> {
            final SubCategoryView subCategoryView = new SubCategoryView();
            final SubCategoryPresenter subCategoryPresenter = subCategoryView.getRealPresenter();
            subCategoryPresenter.initialize(subCategoryModel2);
            
            final Parent view = subCategoryView.getView();
            view.setId(String.valueOf(subCategoryModel2.getId()));
            view.setUserData(subCategoryPresenter);
            
            vbSubCategories.getChildren().add(view);
        });
        
        //TODO scroll to subCategoryModel (or previous if scm==dummy)
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in CategoryPresenter"); // NOI18N
        
    }
    
}
