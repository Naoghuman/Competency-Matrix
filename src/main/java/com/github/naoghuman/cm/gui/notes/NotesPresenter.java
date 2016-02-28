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
package com.github.naoghuman.cm.gui.notes;

import com.github.naoghuman.cm.model.notes.NotesModel;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 *
 * @author PRo
 */
public class NotesPresenter implements Initializable {
    
    @FXML private TextArea taNotes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(URL, ResourceBundle) NotesPresenter"); // NOI18N
        
        assert (taNotes != null) : "fx:id=\"taNotes\" was not injected: check your FXML file 'Notes.fxml'."; // NOI18N
        
    }

    public void initialize(NotesModel notesModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(NotesModel) NotesPresenter"); // NOI18N
        
        taNotes.setText(notesModel.getNotes());
    }
    
    public String getNotes() {
        return taNotes.getText();
    }
    
}
