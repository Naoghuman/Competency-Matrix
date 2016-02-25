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
package com.github.naoghuman.cm.gui.matrixthumbnail;

import com.github.naoghuman.cm.application.api.EMoveTo;
import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PRo
 */
public class MatrixThumbnailPresenter implements Initializable, IActionConfiguration {
    
    @FXML private AnchorPane apMouseClick;
    @FXML private Label lName;
    
    private MatrixModel matrixModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(URL, ResourceBundle) MatrixThumbnailPresenter"); // NOI18N
        
    }
    
    public void initialize(MatrixModel matrixModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(String) MatrixThumbnailPresenter"); // NOI18N
        
        this.matrixModel = matrixModel;
        lName.setText(matrixModel.getTitle());
        
        apMouseClick.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() < 2) {
                return;
            }
            
            this.onActionOpenCategoryOverview();
        });
    }
    
    private void onActionOpenCategoryOverview() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action open Category Overview"); // NOI18N
        
        final TransferData transferData = new TransferData();
        transferData.setActionId(ACTION__INIT_MOVE_TO__XY_OVERVIEW);
        transferData.setLong(matrixModel.getId());
        transferData.setObject(EMoveTo.CATEGORY_OVERVIEW);
        ActionFacade.INSTANCE.handle(transferData);
    }
    
//    private void onActionOpenMatrix() {
//        LoggerFacade.INSTANCE.debug(this.getClass(), "On action open Matrix"); // NOI18N
//        
//        final boolean isDialogOpen = DialogProvider.isDialogOpen(EDialogType.MATRIX, matrixModel.getId());
//        if (isDialogOpen) {
//            return;
//        }
//        
//        final MatrixView matrixView = new MatrixView();
//        final MatrixPresenter matrixPresenter = matrixView.getRealPresenter();
//        matrixPresenter.initialize(matrixModel);
//        
//        DialogProvider.register(EDialogType.MATRIX, matrixModel.getId());
//        final Dialog dialog = DialogProvider.getDialogOpenMatrix(matrixView.getView(), matrixModel);
//        final Optional<ButtonType> result = dialog.showAndWait();
//        if (!result.isPresent()) {
//            DialogProvider.remove(EDialogType.MATRIX, matrixModel.getId());
//            return;
//        }
//        
//        final ButtonType buttonType = result.get();
//        if (!buttonType.getButtonData().equals(ButtonType.OK.getButtonData())) {
//            DialogProvider.remove(EDialogType.MATRIX, matrixModel.getId());
//            return;
//        }
//        
//        // Update MatrixModel
////        final ActionTransferModel actionTransferModel = new ActionTransferModel();
////        actionTransferModel.setActionKey(ACTION__UPDATE__MATRIX);
////        actionTransferModel.setObject(matrixModel);
////        ActionFacade.INSTANCE.handle(actionTransferModel);
//        System.out.println("--> button okay im dialog matrix geklickt");
//        DialogProvider.remove(EDialogType.MATRIX, matrixModel.getId());
//    }
    
}
