package com.example.jumptonext;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UserWithBillsDao {
    @Transaction
    @Query("SELECT * FROM User WHERE name = :userName ORDER BY name")
    LiveData<List<UserWithBills>> getUserWithBills(String userName);
}
