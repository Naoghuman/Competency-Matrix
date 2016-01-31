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
package com.github.naoghuman.cm.dialog.matrix;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import static com.github.naoghuman.cm.configuration.api.IActionConfiguration.ACTION__CREATE__MATRIX;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.dialog.api.DialogProvider;
import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.IDateConverter;
import com.github.naoghuman.cm.util.api.UtilFacade;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author PRo
 */
public class MatrixPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private Label lMatrix;
    @FXML private TextArea taDescription;
    @FXML private TextArea taNotes;
    
    private MatrixModel matrixModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MatrixPresenter"); // NOI18N
        
        assert (lMatrix != null)        : "fx:id=\"lMatrix\" was not injected: check your FXML file 'Matrix.fxml'."; // NOI18N
        assert (taDescription != null) : "fx:id=\"taDescription\" was not injected: check your FXML file 'Matrix.fxml'."; // NOI18N
        assert (taNotes != null)       : "fx:id=\"taNotes\" was not injected: check your FXML file 'Matrix.fxml'."; // NOI18N
        
    }
    
    public void initialize(MatrixModel matrixModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MatrixModel"); // NOI18N
        
        this.matrixModel = matrixModel;
       
        lMatrix.setText(String.valueOf(this.matrixModel.getTitle()));
        
        taDescription.setText(this.matrixModel.getDescription());
        matrixModel.descriptionProperty().bind(taDescription.textProperty());
        
        taNotes.setText(this.matrixModel.getNotes());
        matrixModel.notesProperty().bind(taNotes.textProperty());
    }
    
    public void onActionAddDate() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action add Date"); // NOI18N
        
        final StringBuilder sb = new StringBuilder();
        sb.append(UtilFacade.INSTANCE.getDateConverter().convertLongToDateTime(System.currentTimeMillis(), 
                IDateConverter.PATTERN__GENERATIONTIME));
        sb.append("\n"); // NOI18N
        sb.append(taNotes.getText());
        
        taNotes.setText(sb.toString());
    }
    
    public void onActionCreateTopic() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create Topic"); // NOI18N
        
        final TextInputDialog dialog = DialogProvider.getNewTopicDialog();
        final Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        
        final String name = result.get().trim();
        if (name.isEmpty()) {
            return;
        }

//        final ActionTransferModel actionTransferModel = new ActionTransferModel();
//        actionTransferModel.setActionKey(ACTION__CREATE__MATRIX);
//        actionTransferModel.setString(name);
//        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    public void onActionOpenMatrixFolder() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action open Matrix folder"); // NOI18N

        final Folder folder = new Folder();
        folder.register(Folder.EPathId.MATRIX_ID, matrixModel.getId());
        UtilFacade.INSTANCE.getFolderHelper().open(folder);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in MatrixPresenter"); // NOI18N
        
    }
    
}
