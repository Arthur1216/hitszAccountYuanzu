package com.example.jumptonext;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BillViewModel extends AndroidViewModel {
    private static BillDao billDao;

    public BillViewModel (@NonNull Application application) {
        super(application);
        BillRepository billRepository = new BillRepository(application);
    }

    public static LiveData<List<Bill>> getAllBillsLive() {
        return BillRepository.getAllBillsLive();
    }

    public static LiveData<List<UserDefine>> getAllUserDefine() {
        return BillRepository.getAllUserDefine();
    }

    public static LiveData<List<UserWithBills>> getUserWithBills(String userName) {
        return BillRepository.getUserWithBills(userName);
    }

    public static LiveData<List<User>> getAllUsers() {
        return BillRepository.getAllUsers();
    }

    static void insertBills(Bill... bills) {
        BillRepository.insertBills(bills);
    }

    static void deleteAllBills() {
        BillRepository.deleteAllBills();
    }

    static void insertUserDefine(UserDefine... userDefines) {
        BillRepository.insertUserDefines(userDefines);
    }

    static void clearUserDefine(UserDefine... userDefines) {
        BillRepository.clearUserDefines();
    }

    static void insertUsers(User... users) {
        BillRepository.insertUsers(users);
    }
}
