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
package com.github.naoghuman.cm.matrix.category.subcategory.levelthumbnail;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.api.LevelModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author PRo
 */
public class LevelThumbnailPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private Label lLevel;
    @FXML private TextArea taDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize LevelThumbnailPresenter"); // NOI18N
        
        assert (lLevel != null)        : "fx:id=\"lLevel\" was not injected: check your FXML file 'LevelThumbnail.fxml'."; // NOI18N
        assert (taDescription != null) : "fx:id=\"taDescription\" was not injected: check your FXML file 'LevelThumbnail.fxml'."; // NOI18N
        
        this.registerActions();
    }
    
    public void initialize(LevelModel levelModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize LevelModel"); // NOI18N
       
        lLevel.setText("Level " + levelModel.getLevel()); // NOI18N
        taDescription.setText(levelModel.getDescription());
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in LevelThumbnailPresenter"); // NOI18N
        
    }
    
}
