package com.example.jumptonext;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {Bill.class,UserDefine.class,User.class},version = 1)
public abstract class BillDatabase extends RoomDatabase {
    private static BillDatabase INSTANCE;
    static synchronized BillDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),BillDatabase.class,"bill_database")
                    .build();
        }
        return INSTANCE;
    }
    public abstract BillDao getBillDao();
    public abstract UserDefineDao getUserDefineDao();
    public abstract UserDao getUserDao();
    public abstract UserWithBillsDao getUserWithBillsDao();
}
