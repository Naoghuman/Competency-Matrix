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
package com.github.naoghuman.cm.matrix.category.subcategory;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.dialog.api.DialogProvider;
import com.github.naoghuman.cm.model.api.SubCategoryModel;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author PRo
 */
public class SubCategoryPresenter implements Initializable, IActionConfiguration, IRegisterActions {

    @FXML private HBox hbLevels;
    @FXML private Label lSubCategory;
    
    private SubCategoryModel subCategoryModel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize SubCategoryPresenter"); // NOI18N
        
        assert (lSubCategory != null) : "fx:id=\"lSubCategory\" was not injected: check your FXML file 'SubCategory.fxml'."; // NOI18N
        
        this.initializeLevelThumbnail();
        
        this.registerActions();
    }
    
    private void initializeLevelThumbnail() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize LevelThumbnails"); // NOI18N
         
    }

    public void initialize(SubCategoryModel subCategoryModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize SubCategoryModel"); // NOI18N
        
        this.subCategoryModel = subCategoryModel;
        
        lSubCategory.setText(subCategoryModel.getTitle());
        
        /*
        TODO
         - load levels from db
         - show them
        */
    }
    
    public void onActionDeleteSubCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete SubCategory"); // NOI18N
        
        final Alert alert = DialogProvider.getDeleteSubCategoryDialog();
        final Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final ButtonType buttonType = result.get();
        if (!buttonType.getButtonData().equals(ButtonType.YES.getButtonData())) {
            return;
        }
        
        final ActionTransferModel actionTransferModel = new ActionTransferModel();
        actionTransferModel.setActionKey(ACTION__DELETE__SUBCATEGORY);
        actionTransferModel.setObject(subCategoryModel);
        ActionFacade.INSTANCE.handle(actionTransferModel);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in SubCategoryPresenter"); // NOI18N
        
    }
    
}
