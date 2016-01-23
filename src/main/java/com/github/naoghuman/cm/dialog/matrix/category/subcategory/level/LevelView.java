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
package com.github.naoghuman.cm.dialog.matrix.category.subcategory.level;

import com.github.naoghuman.cm.dialog.matrix.category.subcategory.level.LevelPresenter;
import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author PRo
 */
public class LevelView extends FXMLView {
    
    public LevelPresenter getRealPresenter() {
        return (LevelPresenter) super.getPresenter();
    }
    
}