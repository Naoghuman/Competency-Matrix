/*
 * Copyright (C) 2016 PRo
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
package com.github.naoghuman.cm.gui.categorynavigation;

import com.github.naoghuman.cm.application.api.EMoveTo;
import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.dialog.api.DialogProvider;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.UtilFacade;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author PRo
 */
public class CategoryNavigationPresenter implements Initializable, IActionConfiguration {
    
    private Long matrixId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(URL, ResourceBundle) CategoryNavigationPresenter"); // NOI18N
        
    }
    
    public void initialize(Long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(matrixId) CategoryNavigationPresenter"); // NOI18N
        
        this.matrixId = matrixId;
    }
    
    public void onActionCreateCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create new CategoryModel"); // NOI18N
        
        final TextInputDialog dialog = DialogProvider.getDialogCreateCategory();
        final Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final String name = result.get().trim();
        if (name.isEmpty()) {
            return;
        }
        
        final TransferData transferData = new TransferData();
        transferData.setActionId(ACTION__CREATE__CATEGORY);
        transferData.setLong(matrixId);
        transferData.setString(name);
        ActionFacade.INSTANCE.handle(transferData);
    }
    
    public void onActionDeleteCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete Category"); // NOI18N
        
    }
    
    public void onActionDeleteMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete Matrix"); // NOI18N
        
    }
    
    public void onActionMoveToNavigationMatrixOverview() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Move to Navigation MatrixOverview"); // NOI18N
        
        final TransferData transferData = new TransferData();
        transferData.setActionId(ACTION__INIT_MOVE_TO__XY_OVERVIEW);
        transferData.setObject(EMoveTo.MATRIX_OVERVIEW);
        ActionFacade.INSTANCE.handle(transferData);
    }
    
    public void onActionShowResources() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Resources"); // NOI18N
        
    }
    
    public void onActionShowDetails() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Details"); // NOI18N
        
    }
    
    public void onActionShowFolder() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Folder"); // NOI18N
        
        final Folder folder = new Folder();
        folder.register(Folder.EPath.MATRIX, matrixId);
//        folder.register(Folder.EPath.CATEGORY_ID, categoryModel.getId());
        UtilFacade.INSTANCE.getFolderHelper().open(folder);
    }
    
    public void onActionShowGlossary() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Gossary"); // NOI18N
        
    }
    
    public void onActionShowNotes() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Notes"); // NOI18N
        
        /*
        TODO
         - Load Notes from DB with parentId=Matrix.id
         - Extend NotesModel
            - Add parameter title
         - Add title to NotesModel
         - Initialize NotesPresenter with NotesModel
         - Show NotesView in dialog
        */
    }
    
    
    public void onActionShowTranslation() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Translation"); // NOI18N
        
    }
    
}
