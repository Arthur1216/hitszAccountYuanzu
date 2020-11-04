package com.example.jumptonext;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUsers(User... users);
    @Query("SELECT * FROM User ORDER BY name")
    LiveData<List<User>> getAllUsers();
}
