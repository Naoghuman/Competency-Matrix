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
package com.github.naoghuman.cm.application;

import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;

/**
 *
 * @author PRo
 */
public class ApplicationPresenter implements Initializable {
    
    @FXML private TabPane tpCompetencyMatrix;
    @FXML private TreeView tvCompetencyMatrix;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
        assert (tpCompetencyMatrix != null) : "fx:id=\"tpCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (tvCompetencyMatrix != null) : "fx:id=\"tvCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
    }
    
    public void onActionCreateNewCompetencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create new Competency Matrix"); // NOI18N
        
    }
    
}
