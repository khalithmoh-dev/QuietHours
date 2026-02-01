package com.khalith.quiethours.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GhostNumber.class, GhostLog.class}, version = 1, exportSchema = false)
public abstract class GhostDatabase extends RoomDatabase {
    public abstract GhostNumberDao ghostNumberDao();
    public abstract GhostLogDao ghostLogDao();

    private static volatile GhostDatabase INSTANCE;

    public static GhostDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GhostDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GhostDatabase.class, "ghost_database")
                            .allowMainThreadQueries() // Simple for this use case
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
