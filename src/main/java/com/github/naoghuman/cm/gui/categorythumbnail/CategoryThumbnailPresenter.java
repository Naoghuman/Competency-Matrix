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
package com.github.naoghuman.cm.gui.categorythumbnail;

import com.github.naoghuman.cm.model.category.CategoryModel;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PRo
 */
public class CategoryThumbnailPresenter implements Initializable {

    @FXML private AnchorPane apMouseClick;
    @FXML private Label lName;
    
    private CategoryModel categoryModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(URL, ResourceBundle) CategoryThumbnailPresenter"); // NOI18N
        
    }
    
    public void initialize(CategoryModel categoryModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize(String) CategoryThumbnailPresenter"); // NOI18N
        
        this.categoryModel = categoryModel;
        lName.setText(categoryModel.getTitle());
        
        apMouseClick.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() < 2) {
                return;
            }
            
            this.onActionOpenSubCategoryOverview();
        });
    }
    
    private void onActionOpenSubCategoryOverview() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Open CategoryOverview"); // NOI18N
        
        /*
        pre
         - implement subcategory-navigation
         - implement subcategory-overview
        
        do
        - 1a) Blend out navigation category-overview
               - 1b) Blend in navigation subcategory-overview from category
        - 2a) Move from category-overview to matrix/category/subcategory-overview
        */
        
    }
    
}
