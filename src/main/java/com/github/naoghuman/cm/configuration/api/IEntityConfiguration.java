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
package com.github.naoghuman.cm.configuration.api;

/**
 *
 * @author PRo
 */
public interface IEntityConfiguration {
    
    public static final Long DEFAULT_ID__CATEGORY_MODEL = 1_000_000_000_000L;
    public static final Long DEFAULT_ID__MATRIX_MODEL = 1_000_000_100_000L;
    public static final Long DEFAULT_ID__SUBCATEGORY_MODEL = 1_000_000_200_000L;
    
    public static final String COLUMN_NAME__ID = "id"; // NOI18N
    public static final String COLUMN_NAME__CATEGORY_ID = "categoryId"; // NOI18N
    public static final String COLUMN_NAME__DESCRIPTION = "description"; // NOI18N
    public static final String COLUMN_NAME__GENERATION_TIME = "generationTime"; // NOI18N
    public static final String COLUMN_NAME__LEVEL = "lvl"; // NOI18N
    public static final String COLUMN_NAME__MATRIX_ID = "matrixId"; // NOI18N
    public static final String COLUMN_NAME__SUBCATEGORY_ID = "subCategoryId"; // NOI18N
    public static final String COLUMN_NAME__TITLE = "title"; // NOI18N
    
    public static final String ENTITY__TABLE_NAME__CATEGORY_MODEL = "CategoryModel"; // NOI18N
    public static final String ENTITY__TABLE_NAME__LEVEL_MODEL = "LevelModel"; // NOI18N
    public static final String ENTITY__TABLE_NAME__MATRIX_MODEL = "MatrixModel"; // NOI18N
    public static final String ENTITY__TABLE_NAME__SUBCATEGORY_MODEL = "SubCategoryModel"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__CATEGORY_FIND_ALL = "CategoryModel.findAll"; // NOI18N
    public static final String NAMED_QUERY__NAME__LEVEL_FIND_ALL = "LevelModel.findAll"; // NOI18N
    public static final String NAMED_QUERY__NAME__MATRIX_FIND_ALL = "MatrixModel.findAll"; // NOI18N
    public static final String NAMED_QUERY__NAME__SUBCATEGORY_FIND_ALL = "SubCategoryModel.findAll"; // NOI18N
    
    public static final String NAMED_QUERY__QUERY__CATEGORY_FIND_ALL = "SELECT cm FROM CategoryModel cm WHERE cm.matrixId = :matrixId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__LEVEL_FIND_ALL = "SELECT lm FROM LevelModel lm WHERE lm.matrixId = :matrixId AND lm.categoryId = :categoryId AND lm.subCategoryId = :subCategoryId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__MATRIX_FIND_ALL = "SELECT mm FROM MatrixModel mm"; // NOI18N
    public static final String NAMED_QUERY__QUERY__SUBCATEGORY_FIND_ALL = "SELECT scm FROM SubCategoryModel scm WHERE scm.matrixId = :matrixId AND scm.categoryId = :categoryId"; // NOI18N
    
    public static final String SIGN__EMPTY = ""; // NOI18N
    
}
