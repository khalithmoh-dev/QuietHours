package com.khalith.quiethours.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ghost_numbers")
public class GhostNumber {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    public String number;
    public String name;

    public GhostNumber(String number, String name) {
        this.number = number;
        this.name = name;
    }
}
