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
package com.github.naoghuman.cm.dialog.matrix.category.subcategory.level;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.matrix.category.subcategory.level.LevelModel;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.IDateConverter;
import com.github.naoghuman.cm.util.api.UtilFacade;
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
public class LevelPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private Label lLevel;
    @FXML private TextArea taDescription;
    @FXML private TextArea taNotes;
    
    private LevelModel levelModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize LevelPresenter"); // NOI18N
        
        assert (lLevel != null)        : "fx:id=\"lLevel\" was not injected: check your FXML file 'Level.fxml'."; // NOI18N
        assert (taDescription != null) : "fx:id=\"taDescription\" was not injected: check your FXML file 'Level.fxml'."; // NOI18N
        assert (taNotes != null)       : "fx:id=\"taNotes\" was not injected: check your FXML file 'Level.fxml'."; // NOI18N
        
    }
    
    public void initialize(LevelModel levelModel) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize LevelModel"); // NOI18N
        
        this.levelModel = levelModel;
       
        lLevel.setText(String.valueOf(this.levelModel.getLevel()));
        
        taDescription.setText(this.levelModel.getDescription());
        levelModel.descriptionProperty().bind(taDescription.textProperty());
        
        taNotes.setText(this.levelModel.getNotes());
        levelModel.notesProperty().bind(taNotes.textProperty());
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
    
    public void onActionOpenLevelFolder() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action open Level folder"); // NOI18N

        final Folder folder = new Folder();
        folder.register(Folder.EPathId.MATRIX_ID, levelModel.getMatrixId());
        folder.register(Folder.EPathId.CATEGORY_ID, levelModel.getCategoryId());
        folder.register(Folder.EPathId.SUBCATEGORY_ID, levelModel.getSubCategoryId());
        folder.register(Folder.EPathId.LEVEL_ID, levelModel.getLevel());
        UtilFacade.INSTANCE.getFolderHelper().open(folder);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in LevelPresenter"); // NOI18N
        
    }
    
}
