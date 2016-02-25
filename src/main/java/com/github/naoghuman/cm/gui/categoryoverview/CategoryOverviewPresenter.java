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
package com.github.naoghuman.cm.gui.categoryoverview;

import com.github.naoghuman.cm.configuration.api.IActionConfiguration;
import com.github.naoghuman.cm.configuration.api.IRegisterActions;
import com.github.naoghuman.cm.gui.categorythumbnail.CategoryThumbnailPresenter;
import com.github.naoghuman.cm.gui.categorythumbnail.CategoryThumbnailView;
import com.github.naoghuman.cm.model.category.CategoryModel;
import com.github.naoghuman.cm.model.matrix.MatrixModel;
import com.github.naoghuman.cm.sql.api.SqlFacade;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author PRo
 */
public class CategoryOverviewPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private AnchorPane apOverview;
    @FXML private FlowPane fpOverview;
    @FXML private Label lOverviewCategories;
    
    private MatrixModel matrixModel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize CategoryOverviewPresenter"); // NOI18N
        
        assert (apOverview != null) : "fx:id=\"apOverview\" was not injected: check your FXML file 'CategoryOverview.fxml'."; // NOI18N
        assert (fpOverview != null) : "fx:id=\"fpOverview\" was not injected: check your FXML file 'CategoryOverview.fxml'."; // NOI18N
        
    }
    
    public void initialize(Long matrixId) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize CategoryOverview with matrixId: " + matrixId); // NOI18N
        
        matrixModel = SqlFacade.INSTANCE.getMatrixSqlProvider().findById(matrixId);
        lOverviewCategories.setText("Overview: All categories from " + matrixModel.getTitle()); // NOI18N
        
        ActionFacade.INSTANCE.handle(ACTION__REFRESH__CATEGORY_OVERVIEW);
    }

    public void initialize(double layoutX, double layoutY, double prefWidth, double prefHeight) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize AnchorPane in CategoryOverviewPresenter"); // NOI18N
        
        apOverview.setLayoutX(layoutX);
        apOverview.setLayoutY(layoutY);
        apOverview.setPrefWidth(prefWidth);
        apOverview.setPrefHeight(prefHeight);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in CategoryOverviewPresenter"); // NOI18N
        
        this.registerOnActionRefreshCategoryOverview();
    }

    private void registerOnActionRefreshCategoryOverview() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action Refresh CategoryOverview with matrixId: " + matrixModel.getId()); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ACTION__REFRESH__CATEGORY_OVERVIEW,
                (ActionEvent ae) -> {
                    LoggerFacade.INSTANCE.debug(this.getClass(), "On action Refresh CategoryOverview with matrixId: " + matrixModel.getId()); // NOI18N
                    
                    final List<CategoryModel> categoryModels = SqlFacade.INSTANCE.getCategorySqlProvider().findAll(matrixModel.getId());
                    final List<Parent> categoryThumbnailViews = FXCollections.observableArrayList();
                    for (CategoryModel categoryModel : categoryModels) {
                        final CategoryThumbnailView categoryThumbnailView = new CategoryThumbnailView();
                        final CategoryThumbnailPresenter categoryThumbnailPresenter = categoryThumbnailView.getRealPresenter();
                        categoryThumbnailPresenter.initialize(categoryModel);
                        categoryThumbnailViews.add(categoryThumbnailView.getView());
                    }
                    
                    fpOverview.getChildren().clear();
                    fpOverview.getChildren().addAll(categoryThumbnailViews);
                });
    }
    
}
