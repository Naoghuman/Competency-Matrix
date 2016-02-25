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
package com.github.naoghuman.cm.gui.matrixnavigation;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.dialog.api.DialogProvider;
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
public class MatrixNavigationPresenter implements Initializable, IActionConfiguration {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize MatrixNavigationPresenter"); // NOI18N
        
    }
    
    public void onActionShowGlossary() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Gossary"); // NOI18N
        
    }
    
    public void onActionShowNotes() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Notes"); // NOI18N
        
    }
    
    public void onActionCreateNewMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create new MatrixModel"); // NOI18N
        
        final TextInputDialog dialog = DialogProvider.getDialogCreateMatrix();
        final Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final String name = result.get().trim();
        if (name.isEmpty()) {
            return;
        }
        
        final TransferData transferData = new TransferData();
        transferData.setActionId(ACTION__CREATE__MATRIX);
        transferData.setString(name);
        ActionFacade.INSTANCE.handle(transferData);
    }
    
    public void onActionShowTranslation() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show Translation"); // NOI18N
        
    }
    
}
