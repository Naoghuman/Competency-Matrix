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

import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

/**
 *
 * @author PRo
 */
public interface DialogFacade {
    
    public static TextInputDialog getNewCompetencyMatrixDialog() {
        final TextInputDialog dialog = new TextInputDialog(""); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("New Competency-Matrix"); // NOI18N
        dialog.setHeaderText("New title from the Competency-Matrix"); // NOI18N
        
        return dialog;
    }
    
}
