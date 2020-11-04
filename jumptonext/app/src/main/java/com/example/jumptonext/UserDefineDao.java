package com.example.jumptonext;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDefineDao {
    @Insert
    void insertUserDefines(UserDefine... userDefines);
    @Delete
    void deleteUserDefines(UserDefine... userDefines);
    @Query("DELETE FROM UserDefine")
    void clearUserDefines();
    @Query("SELECT * FROM UserDefine ORDER BY ID ")
    LiveData<List<UserDefine>> getUserDefines();
}
