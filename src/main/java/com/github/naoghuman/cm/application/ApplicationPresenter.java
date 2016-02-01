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

import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;


/**
 *
 * @author PRo
 */
public class ApplicationPresenter implements Initializable, IRegisterActions {
    
    @FXML private BorderPane bpCompetencyMatrix;
    @FXML private SplitPane spCompetencyMatrix;
    @FXML private TreeView tvNavigation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
        assert (bpCompetencyMatrix != null) : "fx:id=\"bpCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (spCompetencyMatrix != null) : "fx:id=\"spCompetencyMatrix\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (tvNavigation != null)       : "fx:id=\"tvNavigation\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.initializeSplitPane();
        
        this.registerActions();
    }
    
    private void initializeSplitPane() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize SplitPane"); // NOI18N
        
        SplitPane.setResizableWithParent(tvNavigation, Boolean.FALSE);
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
        
    }
    
    
}
