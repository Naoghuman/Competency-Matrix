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
package com.github.naoghuman.cm.dialog.api;

import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

/**
 *
 * @author PRo
 */
public interface DialogProvider {
    
    public static Map<EDialogType, Long> openIds = FXCollections.observableHashMap();
    
    public static TextInputDialog getDialogCreateCategory() {
        LoggerFacade.INSTANCE.debug(DialogProvider.class, "Get dialog create Category"); // NOI18N
        
        final TextInputDialog dialog = new TextInputDialog(""); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Category"); // NOI18N
        dialog.setHeaderText("Create new Category"); // NOI18N
        
        return dialog;
    }
    
    public static TextInputDialog getDialogCreateMatrix() {
        LoggerFacade.INSTANCE.debug(DialogProvider.class, "Get dialog create Matrix"); // NOI18N
        
        final TextInputDialog dialog = new TextInputDialog(""); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Matrix"); // NOI18N
        dialog.setHeaderText("Create new Matrix"); // NOI18N
        
        return dialog;
    }
    
    public static Alert getDialogDeleteMatrix() {
        LoggerFacade.INSTANCE.debug(DialogProvider.class, "Get dialog delete Matrix"); // NOI18N
        
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Matrix "); // NOI18N
        alert.setHeaderText("Delete this Matrix?"); // NOI18N
        
        final ButtonType buttonTypeOne = new ButtonType(ButtonType.YES.getText(), ButtonData.YES);
        final ButtonType buttonTypeCancel = new ButtonType(ButtonType.CANCEL.getText(), ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        
        return alert;
    }
    
    public static Dialog getDialogOpenMatrix(Parent content, MatrixModel matrixModel) {
        LoggerFacade.INSTANCE.debug(DialogProvider.class, "Get dialog open Matrix"); // NOI18N
        
        final Dialog dialog = new Dialog();
        dialog.initModality(Modality.NONE);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setPrefSize(1280.0d - 200.0d, 720.0d - 200.0d);
        
        final String title = "Matrix: " + matrixModel.getTitle(); // NOI18N
        dialog.setTitle(title);
        
        final ButtonType buttonTypeOK = new ButtonType(ButtonType.OK.getText(), ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().setAll(buttonTypeOK, ButtonType.CANCEL);
        
        return dialog;
    }
    
    public static Dialog getDialogOpenNotes(Parent content) {
        LoggerFacade.INSTANCE.debug(DialogProvider.class, "Get dialog open Notes"); // NOI18N
        
        final Dialog dialog = new Dialog();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().setPrefSize(1280.0d - 200.0d, 720.0d - 200.0d);
        dialog.setTitle("Notes"); // NOI18N
        
        final ButtonType buttonTypeOK = new ButtonType(ButtonType.OK.getText(), ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().setAll(buttonTypeOK, ButtonType.CANCEL);
        
        return dialog;
    }
    
    public static boolean isDialogOpen(EDialogType dialogType, Long id) {
        return openIds.containsKey(dialogType) && openIds.containsValue(id);
    }
    
    public static void register(EDialogType dialogType, Long id) {
        if (openIds.containsKey(dialogType) && openIds.containsValue(id)) {
            return;
        }
        
        openIds.put(dialogType, id);
    }
    
    public static void remove(EDialogType dialogType, Long id) {
        if (openIds.containsKey(dialogType) && openIds.containsValue(id)) {
            openIds.remove(dialogType, id);
        }
    }
    
}
