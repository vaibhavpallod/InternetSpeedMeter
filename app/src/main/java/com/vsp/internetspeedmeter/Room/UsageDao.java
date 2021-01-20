package com.vsp.internetspeedmeter.Room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsageDao {

    @Insert
    void insert(Usage usage);

    @Update
    void update(Usage usage);

    @Delete
    void delete(Usage usage);

    @Query("DELETE FROM Usage_Table")
    void deleteAll();

    @Query("SELECT * FROM Usage_Table")
    LiveData<List<Usage>> getAllUsage();
}
