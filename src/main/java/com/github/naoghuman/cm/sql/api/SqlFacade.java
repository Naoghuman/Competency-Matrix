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
package com.github.naoghuman.cm.sql.api;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.sql.MatrixSqlProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public enum SqlFacade implements IActionConfiguration, IRegisterActions {
    
    INSTANCE;

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Register actions in SqlFacade"); // NOI18N
        
        this.registerOnActionCreateCompentencyMatrix();
    }

    private void registerOnActionCreateCompentencyMatrix() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register action create Competency-Matrix"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel actionTransferModel = (ActionTransferModel) ae.getSource();
                    final String title = actionTransferModel.getString();
                    final long matrixModelID = MatrixSqlProvider.getDefault().createCompetencyMatrix(title);
                    
                    final PauseTransition pt = new PauseTransition(Duration.millis(50.0d));
                    pt.setOnFinished((ActionEvent event) -> {
                        final ActionTransferModel actionTransferModel2 = new ActionTransferModel();
                        actionTransferModel2.setActionKey(ACTION__REFRESH__OVERVIEW_COMPETENCY_MATRIX);
                        actionTransferModel2.setLong(matrixModelID);
                        ActionFacade.INSTANCE.handle(actionTransferModel2);

                        final ActionTransferModel actionTransferModel3 = new ActionTransferModel();
                        actionTransferModel3.setActionKey(ACTION__OPEN__COMPETENCY_MATRIX);
                        actionTransferModel3.setLong(matrixModelID);
                        ActionFacade.INSTANCE.handle(actionTransferModel3);
                    });
                    pt.playFromStart();
                });
    }
    
}
