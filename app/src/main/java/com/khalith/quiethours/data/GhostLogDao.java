package com.khalith.quiethours.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GhostLogDao {
    @Query("SELECT * FROM ghost_logs ORDER BY time DESC")
    List<GhostLog> getAllLogs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GhostLog log);

    @Query("DELETE FROM ghost_logs")
    void clearLogs();
}
