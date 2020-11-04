package com.example.jumptonext;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BillDao {
    @Insert
    void insertBills(Bill... bills);

    @Update
    void updateBills(Bill... bills);

    @Delete
    void deleteBills(Bill... bills);

    @Query("DELETE FROM BILL")
    void deleteAllBills();

    @Query("SELECT * FROM BILL ORDER BY ID DESC")
        //List<Bill> getAllBills();
    LiveData<List<Bill>> getAllBillsLive();
}
