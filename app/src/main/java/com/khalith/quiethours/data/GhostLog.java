package com.khalith.quiethours.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ghost_logs")
public class GhostLog {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    public String number;
    public long time;
    public String mode;

    public GhostLog(String number, long time, String mode) {
        this.number = number;
        this.time = time;
        this.mode = mode;
    }
}
