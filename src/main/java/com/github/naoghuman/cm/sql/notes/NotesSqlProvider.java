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
package com.github.naoghuman.cm.sql.notes;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.model.notes.NotesModel;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

/**
 *
 * @author PRo
 */
public class NotesSqlProvider implements IActionConfiguration, IEntityConfiguration, IRegisterActions {
    
    private static NotesSqlProvider instance = null;
    
    public static NotesSqlProvider getDefault() {
        if (instance == null) {
            instance = new NotesSqlProvider();
        }
        
        return instance;
    }
    
    private NotesSqlProvider() {
        
    }

    public NotesModel findById(long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "FindById NotesModel"); // NOI18N
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(COLUMN_NAME__MATRIX_ID, matrixId);
        
        final List<NotesModel> notesModels = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(NotesModel.class, NAMED_QUERY__NAME__NOTES_FIND_BY_ID, parameters);
        if (notesModels.isEmpty()) {
            return null;
        }
        
        return notesModels.get(0);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in NotesSqlProvider"); // NOI18N
        
        this.registerOnActionUpdateNotes();
    }

    private void registerOnActionUpdateNotes() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action Update NotesModel"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__UPDATE__NOTES,
                (ActionEvent ae) -> {
                    final TransferData transferData = (TransferData) ae.getSource();
                    final NotesModel notesModel = (NotesModel) transferData.getObject();
                    if (notesModel.getId() == IEntityConfiguration.DEFAULT_ID__NOTES_MODEL) {
                        notesModel.setId(System.currentTimeMillis());
                        DatabaseFacade.INSTANCE.getCrudService().create(notesModel);
                    } else {
                        DatabaseFacade.INSTANCE.getCrudService().update(notesModel);
                    }
                });
    }
    
}
