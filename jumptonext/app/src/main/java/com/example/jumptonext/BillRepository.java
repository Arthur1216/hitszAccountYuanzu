package com.example.jumptonext;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BillRepository {
    private static LiveData<List<Bill>> allBillsLive;
    private static LiveData<List<UserDefine>> allUserDefines;
    private static LiveData<List<User>> allUsers;
    private static BillDao billDao;
    private static UserDefineDao userDefineDao;
    private static UserDao userDao;
    private static UserWithBillsDao userWithBillsDao;

    public BillRepository(Context context) {
        BillDatabase billDatabase = BillDatabase.getDatabase(context.getApplicationContext());
        billDao = billDatabase.getBillDao();
        allBillsLive = billDao.getAllBillsLive();
        userDao = billDatabase.getUserDao();
        userDefineDao = billDatabase.getUserDefineDao();
        allUserDefines = userDefineDao.getUserDefines();
        userWithBillsDao = billDatabase.getUserWithBillsDao();
        allUsers = userDao.getAllUsers();
    }

    public static LiveData<List<Bill>> getAllBillsLive() {
        return allBillsLive;
    }

    public static LiveData<List<UserDefine>> getAllUserDefine() {
        return allUserDefines;
    }

    public static LiveData<List<UserWithBills>> getUserWithBills(String userName) {
        return userWithBillsDao.getUserWithBills(userName);
    }

    public static LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    static void insertUsers(User... users) {
        new InsertUserAsyncTask(userDao).execute(users);
    }

    static void insertBills(Bill... bills) {
        new InsertAsyncTask(billDao).execute(bills);
    }

    static void deleteAllBills() {
        new DeleteAllAsyncTask(billDao).execute();
    }

    static void insertUserDefines(UserDefine... userDefines) {
        new InsertUserDefineAsyncTask(userDefineDao).execute(userDefines);
    }

    static void clearUserDefines() {
        new ClearUserDefineAsyncTask(userDefineDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Bill, Void, Void> {
        private BillDao billDao;

        public InsertAsyncTask(BillDao billDao) {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Bill... bills) {
            billDao.insertBills(bills);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private BillDao billDao;

        public DeleteAllAsyncTask(BillDao billDao) {
            this.billDao = billDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            billDao.deleteAllBills();
            return null;
        }
    }

    static class InsertUserDefineAsyncTask extends AsyncTask<UserDefine, Void, Void> {
        private UserDefineDao userDefineDao;

        public InsertUserDefineAsyncTask(UserDefineDao userDefineDao) {
            this.userDefineDao = userDefineDao;
        }

        @Override
        protected Void doInBackground(UserDefine... userDefines) {
            userDefineDao.insertUserDefines(userDefines);
            return null;
        }
    }

    static class ClearUserDefineAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDefineDao userDefineDao;

        public ClearUserDefineAsyncTask(UserDefineDao userDefineDao) {
            this.userDefineDao = userDefineDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDefineDao.clearUserDefines();
            return null;
        }
    }

    static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUsers(users);
            return null;
        }
    }
}
