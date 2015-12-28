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
package com.github.naoghuman.cm.dialog.api;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

/**
 * TODO use custom dialog / valid input
 * http://code.makery.ch/blog/javafx-dialogs-official/
 * https://github.com/Daytron/SimpleDialogFX
 * or own dialogs like in DreamBetterWorlds
 *
 * @author PRo
 */
public interface DialogProvider {
    
    public static Alert getDeleteCategoryDialog() {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Delete Category"); // NOI18N
        alert.setHeaderText("Really delete this Category?"); // NOI18N
        
        final ButtonType buttonTypeOne = new ButtonType(ButtonType.YES.getText(), ButtonData.YES);
        final ButtonType buttonTypeCancel = new ButtonType(ButtonType.CANCEL.getText(), ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        
        return alert;
    }
    
    public static Alert getDeleteMatrixDialog() {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Delete Matrix"); // NOI18N
        alert.setHeaderText("Really delete this Matrix?"); // NOI18N
        
        final ButtonType buttonTypeOne = new ButtonType(ButtonType.YES.getText(), ButtonData.YES);
        final ButtonType buttonTypeCancel = new ButtonType(ButtonType.CANCEL.getText(), ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        
        return alert;
    }
    
    public static Alert getDeleteSubCategoryDialog() {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Delete Subcategory"); // NOI18N
        alert.setHeaderText("Really delete this Subcategory?"); // NOI18N
        
        final ButtonType buttonTypeOne = new ButtonType(ButtonType.YES.getText(), ButtonData.YES);
        final ButtonType buttonTypeCancel = new ButtonType(ButtonType.CANCEL.getText(), ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
        
        return alert;
    }
    
    public static TextInputDialog getNewCategoryDialog() {
        final TextInputDialog dialog = new TextInputDialog(""); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("New Category"); // NOI18N
        dialog.setHeaderText("New name from the Category"); // NOI18N
        
        return dialog;
    }
    
    public static TextInputDialog getNewMatrixDialog() {
        final TextInputDialog dialog = new TextInputDialog(""); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("New Matrix"); // NOI18N
        dialog.setHeaderText("New name from the Matrix"); // NOI18N
        
        return dialog;
    }
    
    public static TextInputDialog getNewSubCategoryDialog() {
        final TextInputDialog dialog = new TextInputDialog(""); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("New Subcategory"); // NOI18N
        dialog.setHeaderText("New name from the Subcategory"); // NOI18N
        
        return dialog;
    }
    
}
