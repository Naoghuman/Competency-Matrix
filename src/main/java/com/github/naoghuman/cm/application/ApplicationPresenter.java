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

import com.github.naoghuman.cm.application.api.EMoveTo;
import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.gui.categorynavigation.CategoryNavigationPresenter;
import com.github.naoghuman.cm.gui.categorynavigation.CategoryNavigationView;
import com.github.naoghuman.cm.gui.categoryoverview.CategoryOverviewPresenter;
import com.github.naoghuman.cm.gui.categoryoverview.CategoryOverviewView;
import com.github.naoghuman.cm.gui.matrixnavigation.MatrixNavigationView;
import com.github.naoghuman.cm.gui.matrixoverview.MatrixOverviewPresenter;
import com.github.naoghuman.cm.gui.matrixoverview.MatrixOverviewView;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import com.github.naoghuman.cm.util.api.Folder;
import com.github.naoghuman.cm.util.api.UtilFacade;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


/**
 *
 * @author PRo
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    private static final double FORTTEEN = 14.0d;
    
    private static final Duration BLEND_IN_OUT_DURATION = Duration.millis(350.0d);
    private static final Duration BLEND_IN_OUT_PAUSE_DURATION = Duration.millis(50.0d);
    private static final Duration MOVE_TO_DURATION = Duration.millis(750.0d);
    
    private EMoveTo currentPosition = EMoveTo.MATRIX_OVERVIEW;
    
    @FXML private AnchorPane apView;
    @FXML private BorderPane bpNavigation;
    
    private double prefWidth;
    private double prefHeight;
    
    private Pane pane3 = new Pane();
    private Pane pane4 = new Pane();
    private Pane pane5 = new Pane();
    
    private CategoryNavigationView categoryNavigationView;
    private CategoryOverviewView categoryOverviewView;
    private Parent pCategoryOverview;
    
    private MatrixNavigationView matrixNavigationView;
    private Parent pMatrixOverview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
        assert (apView != null)       : "fx:id=\"apView\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (bpNavigation != null) : "fx:id=\"bpNavigation\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.registerActions();
    }
    
    public void initialize() {
        final Rectangle clip = new Rectangle();
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        clip.setWidth(apView.getBoundsInParent().getWidth());
        clip.setHeight(apView.getBoundsInParent().getHeight());
        apView.setClip(clip);
        
        prefWidth = apView.getBoundsInParent().getWidth() - FORTTEEN - FORTTEEN;
        prefHeight = apView.getBoundsInParent().getHeight() - FORTTEEN - FORTTEEN;
        
        this.initializeMatrixOverview();
//        this.initializeCategoryOverview(prefWidth, prefHeight);
//        this.initializeSubCategoryOverview();
//        this.initializeLevelOverview();
        
        // mitte
//        pane3.setStyle("-fx-background-color: DARKSEAGREEN;");
//        pane3.setLayoutX(FORTTEEN);
//        pane3.setLayoutY(FORTTEEN + prefHeight + FORTTEEN + FORTTEEN + prefHeight + FORTTEEN + FORTTEEN);
//        pane3.setPrefWidth(prefWidth);
//        pane3.setPrefHeight(prefHeight);
//        apView.getChildren().add(pane3);
        
        // mitte rechts
//        pane4.setStyle("-fx-background-color: DEEPPINK;");
//        pane4.setLayoutX(FORTTEEN);
//        pane4.setLayoutY(FORTTEEN + prefHeight + FORTTEEN + FORTTEEN + prefHeight + FORTTEEN + FORTTEEN + prefHeight + FORTTEEN + FORTTEEN);
//        pane4.setPrefWidth(prefWidth);
//        pane4.setPrefHeight(prefHeight);
//        apView.getChildren().add(pane4);
        
        // ganz rechts
//        pane5.setStyle("-fx-background-color: GAINSBORO;");
//        pane5.setLayoutX(FORTTEEN);
//        pane5.setLayoutY(FORTTEEN + prefHeight + FORTTEEN + FORTTEEN + prefHeight + FORTTEEN + FORTTEEN + prefHeight + FORTTEEN + FORTTEEN + prefHeight + FORTTEEN + FORTTEEN);
//        pane5.setPrefWidth(prefWidth);
//        pane5.setPrefHeight(prefHeight);
//        apView.getChildren().add(pane5);
    }

    private void initializeMatrixOverview() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MatrixOverview"); // NOI18N
        
        matrixNavigationView = new MatrixNavigationView();
        bpNavigation.setCenter(matrixNavigationView.getView());
                
        final MatrixOverviewView matrixOverviewView = new MatrixOverviewView();
        final MatrixOverviewPresenter matrixOverviewPresenter = matrixOverviewView.getRealPresenter();
        matrixOverviewPresenter.registerActions();
        
        pMatrixOverview = matrixOverviewView.getView();
        apView.getChildren().add(pMatrixOverview);
        
        final double layoutX = FORTTEEN;
        final double layoutY = FORTTEEN;
        matrixOverviewPresenter.initialize(layoutX, layoutY, prefWidth, prefHeight);
    }

    private void initializeCategoryNavigation(Long parentId) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize CategoryNavigation"); // NOI18N
        
        final boolean isCategoryInitialize = (categoryNavigationView == null);
        if (isCategoryInitialize) {
            categoryNavigationView = new CategoryNavigationView();
        }
        final CategoryNavigationPresenter categoryNavigationPresenter = categoryNavigationView.getRealPresenter();
        categoryNavigationPresenter.initialize(parentId);
    }
    
    private void initializeCategoryOverview(Long parentId) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize CategoryOverview"); // NOI18N
        
        final boolean isCategoryInitialize = (categoryOverviewView == null);
        if (isCategoryInitialize) {
            categoryOverviewView = new CategoryOverviewView();
        }
        
        final CategoryOverviewPresenter categoryOverviewPresenter = categoryOverviewView.getRealPresenter();
        if (isCategoryInitialize) {
            pCategoryOverview = categoryOverviewView.getView();
            apView.getChildren().add(pCategoryOverview);

            final double layoutX = FORTTEEN;
            final double layoutY = FORTTEEN + prefHeight + FORTTEEN + FORTTEEN;
            categoryOverviewPresenter.initialize(layoutX, layoutY, prefWidth, prefHeight);
        }
        
        categoryOverviewPresenter.initialize(parentId);
        categoryOverviewPresenter.registerActions();
    };

    private void initializeSubCategoryOverview() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize SubCategoryOverview"); // NOI18N
        
    }

    private void initializeLevelOverview() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize LevelOverview"); // NOI18N
        
    }
    
//    private void initializeSplitPane() {
//        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize SplitPane"); // NOI18N
//        
//        SplitPane.setResizableWithParent(tvNavigation, Boolean.FALSE);
//    }
    
//    private void initializeTreeView() {
//        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize TreeView"); // NOI18N
//        
//        rootNode.getChildren().clear();
//        tvNavigation.setRoot(rootNode);
//    }
    
//    public void onActionCreateMatrix() {
//        LoggerFacade.INSTANCE.debug(this.getClass(), "On action create Matrix"); // NOI18N
//        
//        final TextInputDialog dialog = DialogProvider.getCreateMatrixDialog();
//        final Optional<String> result = dialog.showAndWait();
//        if (!result.isPresent()) {
//            return;
//        }
//        
//        final String name = result.get().trim();
//        if (name.isEmpty()) {
//            return;
//        }
//        
//        final ActionTransferModel actionTransferModel = new ActionTransferModel();
//        actionTransferModel.setActionKey(ACTION__CREATE__MATRIX);
//        actionTransferModel.setString(name);
//        ActionFacade.INSTANCE.handle(actionTransferModel);
//    }
    
    private double getLayoutYfromPane(EMoveTo targetPosition) {
        double layoutY = 0.0d;
        switch(targetPosition) {
            case MATRIX_OVERVIEW:   { layoutY = pMatrixOverview.getLayoutY(); break; }
            case CATEGORY_OVERVIEW:   { layoutY = pCategoryOverview.getLayoutY(); break; }
            case SUBCATEGORY_OVERVIEW: { layoutY = pane3.getLayoutY(); break; }
            case LEVEL_OVERVIEW:  { layoutY = pane4.getLayoutY(); break; }
            case FIVE:  { layoutY = pane5.getLayoutY(); break; }
        }
        
        return layoutY;
    }
    
    private Parent getNavigationFor(EMoveTo targetPosition) {
        Parent navigation = null;
        switch(targetPosition) {
            case MATRIX_OVERVIEW:   { navigation = matrixNavigationView.getView(); break; }
            case CATEGORY_OVERVIEW:   { navigation = categoryNavigationView.getView(); break; }
            case SUBCATEGORY_OVERVIEW: { navigation = null; break; }
            case LEVEL_OVERVIEW:  { navigation = null; break; }
            case FIVE:  { navigation = null; break; }
        }
        
        return navigation;
    }
    
    public void onActionDeleteMatrix() {
//        LoggerFacade.INSTANCE.debug(this.getClass(), "On action delete Matrix"); // NOI18N
//        
//        final CategoryModel matrixModel = (CategoryModel) tvNavigation.getSelectionModel().getSelectedItem();
//        if (matrixModel == null) {
//            return;
//        }
//        
//        final Alert alert = DialogProvider.getDeleteMatrixDialog();
//        final Optional<ButtonType> result = alert.showAndWait();
//        if (!result.isPresent()) {
//            return;
//        }
//        
//        final ButtonType buttonType = result.get();
//        if (!buttonType.getButtonData().equals(ButtonType.YES.getButtonData())) {
//            return;
//        }
//        
//        final ActionTransferModel actionTransferModel = new ActionTransferModel();
//        actionTransferModel.setActionKey(ACTION__DELETE__MATRIX);
//        actionTransferModel.setLong(matrixModel.getId());
//        ActionFacade.INSTANCE.handle(actionTransferModel);
    }
    
    private void onActionBlendInOutNavigation(EMoveTo targetPosition) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Move to Navigation: " + targetPosition.name()); // NOI18N
        
        if (targetPosition.equals(currentPosition)) {
            return;
        }
        
        // Current navigation
        final Node currentNavigation = bpNavigation.getCenter();
        currentNavigation.setOpacity(1.0d);
        
        final FadeTransition ftOut = new FadeTransition(BLEND_IN_OUT_DURATION, currentNavigation);
        ftOut.setFromValue(1.0d);
        ftOut.setToValue(0.0d);
        ftOut.setOnFinished((ActionEvent event) -> {
            bpNavigation.setCenter(null);
        });
        
        // New navigation
        final Parent targetNavigation = this.getNavigationFor(targetPosition);
        targetNavigation.setOpacity(0.0d);
        
        final FadeTransition ftIn = new FadeTransition(BLEND_IN_OUT_DURATION, targetNavigation);
        ftIn.setFromValue(0.0d);
        ftIn.setToValue(1.0d);
        
        // Little pause
        final PauseTransition pt = new PauseTransition(BLEND_IN_OUT_PAUSE_DURATION);
        ftOut.setOnFinished((ActionEvent event) -> {
            bpNavigation.setCenter(targetNavigation);
        });
        
        // Blend it
        final SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(ftOut, pt, ftIn);
        st.playFromStart();
    }
    
    private void onActionCheckXyOverview(Long parentId, EMoveTo targetPosition) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Check XyOverview: " + targetPosition.name()); // NOI18N
        
        switch(targetPosition) {
            case MATRIX_OVERVIEW:   { break; }
            case CATEGORY_OVERVIEW:   {
                this.initializeCategoryNavigation(parentId);
                this.initializeCategoryOverview(parentId);
                break;
            }
            case SUBCATEGORY_OVERVIEW: { this.initializeSubCategoryOverview(); break; }
            case LEVEL_OVERVIEW:  { this.initializeLevelOverview(); break; }
            case FIVE:  { break; }
        }
    }
    
    private void onActionMoveToXyOverview(EMoveTo targetPosition) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action MoveTo XyOverview: " + targetPosition.name()); // NOI18N
        
        if (targetPosition.equals(currentPosition)) {
            return;
        }
        
        final double currentY = this.getLayoutYfromPane(currentPosition);
        final double targetY = this.getLayoutYfromPane(targetPosition);
        final double moveRange = currentY - targetY;
        
        final TranslateTransition tt1 = new TranslateTransition(MOVE_TO_DURATION, pMatrixOverview);
        tt1.setFromY(pMatrixOverview.getTranslateY());
        tt1.setToY(tt1.getFromY() + moveRange);
        
        final TranslateTransition tt2 = new TranslateTransition(MOVE_TO_DURATION, pCategoryOverview);
        tt2.setFromY(pCategoryOverview.getTranslateY());
        tt2.setToY(tt2.getFromY() + moveRange);
        
//        final TranslateTransition tt3 = new TranslateTransition(DURATION, pane3);
//        tt3.setFromY(pane3.getTranslateY());
//        tt3.setToY(tt3.getFromY() + moveRange);
        
//        final TranslateTransition tt4 = new TranslateTransition(DURATION, pane4);
//        tt4.setFromY(pane4.getTranslateY());
//        tt4.setToY(tt4.getFromY() + moveRange);
        
//        final TranslateTransition tt5 = new TranslateTransition(DURATION, pane5);
//        tt5.setFromY(pane5.getTranslateY());
//        tt5.setToY(tt5.getFromY() + moveRange);
        
        final PauseTransition pauseTransition = new PauseTransition(Duration.millis(100.0d));
        
        final ParallelTransition pt = new ParallelTransition(tt1, tt2);//, tt3, tt4, tt5);
        pt.setOnFinished((ActionEvent ae) -> {
            currentPosition = targetPosition;
        });
        
        final SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(pauseTransition, pt);
        st.playFromStart();
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
        
        SqlFacade.INSTANCE.registerActions();
        
        this.registerOnActionCreateFolder();
        this.registerOnActionMoveToXyOverview();
    }
    
    private void registerOnActionCreateFolder() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action create Folder"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__CREATE__FOLDER,
                (ActionEvent ae) -> {
                    final TransferData transferData = (TransferData) ae.getSource();
                    final Folder folder = (Folder) transferData.getObject();
                    UtilFacade.INSTANCE.getFolderHelper().create(folder);
                });
    }
    
    private void registerOnActionMoveToXyOverview() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action Init MoveTo XyOverview"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__INIT_MOVE_TO__XY_OVERVIEW,
                (ActionEvent ae) -> {
                    final TransferData transferData = (TransferData) ae.getSource();
                    final Long parentId = transferData.getLong();
                    final EMoveTo targetPosition = (EMoveTo) transferData.getObject();
                    this.onActionCheckXyOverview(parentId, targetPosition);
                    this.onActionBlendInOutNavigation(targetPosition);
                    this.onActionMoveToXyOverview(targetPosition);
                });
    }
    
}
