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
package com.github.naoghuman.cm;

import com.airhacks.afterburner.injection.Injector;
import com.github.naoghuman.cm.application.ApplicationPresenter;
import com.github.naoghuman.cm.application.ApplicationView;
import com.github.naoghuman.cm.configuration.api.IApplicationConfiguration;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author PRo
 */
public class CompetencyMatrix extends Application implements IApplicationConfiguration {
    
    @Override
    public void init() throws Exception {
        PropertiesFacade.INSTANCE.register(CM__RESOURCE_BUNDLE__COMPETENCY_MATRIX);
        
        final char borderSign = this.getProperty(KEY__COMPETENCY_MATRIX__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__COMPETENCY_MATRIX__MESSAGE_START);
        final String title = this.getProperty(KEY__COMPETENCY_MATRIX__TITLE);
        LoggerFacade.INSTANCE.message(borderSign, 80, String.format(message, title));
        
        final Boolean dropPreferencesFileAtStart = Boolean.FALSE;
        PreferencesFacade.INSTANCE.init(dropPreferencesFileAtStart);
        
        DatabaseFacade.INSTANCE.register(this.getProperty(KEY__COMPETENCY_MATRIX__DATABASE));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final ApplicationView applicationView = new ApplicationView();
        final ApplicationPresenter applicationPresenter = applicationView.getRealPresenter();
        
        final Scene scene = new Scene(applicationView.getView(), 1280, 720);
        primaryStage.setTitle(this.getProperty(KEY__COMPETENCY_MATRIX__TITLE));
        primaryStage.setResizable(Boolean.FALSE);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        primaryStage.show();
        
        applicationPresenter.initialize();
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(CM__RESOURCE_BUNDLE__COMPETENCY_MATRIX, propertyKey);
    }
    
    private void onCloseRequest() {
        // afterburner.fx
        Injector.forgetAll();
        
        // Database
        DatabaseFacade.INSTANCE.shutdown();
        
        // Message
        final char borderSign = this.getProperty(KEY__COMPETENCY_MATRIX__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__COMPETENCY_MATRIX__MESSAGE_STOP);
        final String title = this.getProperty(KEY__COMPETENCY_MATRIX__TITLE);
        LoggerFacade.INSTANCE.message(borderSign, 80, String.format(message, title));
        
        // Timer
        final PauseTransition pt = new PauseTransition(CM__DURATION__125);
        pt.setOnFinished((ActionEvent event) -> {
            Platform.exit();
        });
        pt.playFromStart();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
