package com.khalith.quiethours.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import kotlinx.coroutines.flow.Flow;

@Dao
public interface GhostNumberDao {
    @Query("SELECT * FROM ghost_numbers")
    List<GhostNumber> getAllGhostNumbersList(); // Use list for simple access or Flow if possible

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GhostNumber ghostNumber);

    @Delete
    void delete(GhostNumber ghostNumber);

    @Query("SELECT * FROM ghost_numbers WHERE number = :number LIMIT 1")
    GhostNumber getByNumber(String number);
}
