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
package com.github.naoghuman.cm.util;

import com.github.naoghuman.cm.util.api.IDateConverter;
import org.joda.time.MutableDateTime;

/**
 *
 * @author PRo
 */
public class DateConverter implements IDateConverter {
    
    public static IDateConverter instance = null;
    
    public static IDateConverter getDefault() {
        if (instance == null) {
            instance = new DateConverter();
        }
        
        return instance;
    }
    
    private DateConverter() {
        
    }
    
    @Override
    public String convertLongToDateTime(Long millis, String pattern) {
        final MutableDateTime mdt = new MutableDateTime(millis);
        return mdt.toString(pattern);
    }
    
}
