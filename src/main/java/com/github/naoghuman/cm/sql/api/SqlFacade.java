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
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.event.ActionEvent;

/**
 *
 * @author PRo
 */
public enum SqlFacade implements IActionConfiguration, IRegisterActions, ISql {
    
    INSTANCE;

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
        
        this.registerOnActionCreateNewCompentencyMatrix();
    }

    private void registerOnActionCreateNewCompentencyMatrix() {
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__COMPETENCY_MATRIX,
                (ActionEvent ae) -> {
                    final ActionTransferModel transferModel = (ActionTransferModel) ae.getSource();
                    final String title = transferModel.getString();
                    System.out.println("titel: " + title);
//                    this.show(transferModel.getLong());
                });
    }
    
}
