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

import com.github.naoghuman.cm.model.api.MatrixModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author PRo
 */
public class MatrixPresenter implements Initializable {
    
    @FXML private Label lMatrix;
    @FXML private VBox vbMatrix;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MatrixPresenter"); // NOI18N
        
        assert (lMatrix != null)  : "fx:id=\"lMatrix\" was not injected: check your FXML file 'Matrix.fxml'."; // NOI18N
        assert (vbMatrix != null) : "fx:id=\"vbMatrix\" was not injected: check your FXML file 'Matrix.fxml'."; // NOI18N
    }
    
    public void initialize(MatrixModel matrixModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MatrixModel"); // NOI18N
        
        lMatrix.setText(matrixModel.getTitle());
    }
    
    public void onActionCreateCategory() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create Category"); // NOI18N
        
    }
    
}
