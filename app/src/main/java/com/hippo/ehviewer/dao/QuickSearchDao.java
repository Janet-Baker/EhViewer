/*
 * Copyright 2022 Moedog
 *
 * This file is part of EhViewer
 *
 * EhViewer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * EhViewer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with EhViewer.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.hippo.ehviewer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuickSearchDao extends BasicDao<QuickSearch> {
    @Query("SELECT * FROM QUICK_SEARCH ORDER BY TIME ASC")
    List<QuickSearch> list();

    @Query("SELECT * FROM QUICK_SEARCH ORDER BY TIME ASC LIMIT :limit OFFSET :offset")
    List<QuickSearch> list(int offset, int limit);

    @Update
    void update(List<QuickSearch> downloadLabels);

    @Update
    void update(QuickSearch quickSearch);

    @Insert
    long insert(QuickSearch quickSearch);

    @Delete
    void delete(QuickSearch quickSearch);
}
