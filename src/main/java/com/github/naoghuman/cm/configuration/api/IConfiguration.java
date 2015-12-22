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

import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public interface IConfiguration {
    
    public static final Duration CM__DURATION__125 = Duration.millis(125.0d);
    public static final String CM__RESOURCE_BUNDLE__COMPETENCY_MATRIX = "/com/github/naoghuman/cm/CompetencyMatrix.properties"; // NOI18N
    
    public static final String KEY__COMPETENCY_MATRIX__BORDER_SIGN = "competency.matrix.border.sign"; // NOI18N
    public static final String KEY__COMPETENCY_MATRIX__DATABASE = "competency.matrix.database"; // NOI18N
    public static final String KEY__COMPETENCY_MATRIX__MESSAGE_START = "competency.matrix.message.start"; // NOI18N
    public static final String KEY__COMPETENCY_MATRIX__MESSAGE_STOP = "competency.matrix.message.stop"; // NOI18N
    public static final String KEY__COMPETENCY_MATRIX__TITLE = "competency.matrix.title"; // NOI18N
   
}
