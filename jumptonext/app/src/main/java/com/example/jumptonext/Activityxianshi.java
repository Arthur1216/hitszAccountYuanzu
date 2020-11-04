package com.example.jumptonext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Activityxianshi extends AppCompatActivity {

    private Button btn_year;
    private Button btn_month;
    private Button btn_day;
    private ListView mlv1;
    private String[] nian = new String[]{
            "2020年 收入6000元 支出4000元 余下2000元",
            "2019年 收入6230元 支出3000元 余下3230元",
            "2018年 收入5000元 支出4000元 余下1000元",
            "2017年 收入4200元 支出3700元 余下500元",
            "2016年 收入4000元 支出3000元 余下1000元",
            "2015年 收入4000元 支出4000元 余下0元",
            "2014年 收入4000元 支出2000元 余下2000元",
            "2013年 收入4000元 支出2000元 余下2000元",
            "2012年 收入4000元 支出2000元 余下2000元",
            "2011年 收入4000元 支出2000元 余下2000元",
            "2010年 收入4000元 支出2000元 余下2000元",
            "2009年 收入6400元 支出4000元 余下2400元"};
    private String[] yue = new String[]{};
    private String[] ri = new String[]{
            "2020年3月10日 水电气 21：08 -200元",
            "2020年3月10日 晚餐 20：11 -15元",
            "2020年3月10日 午餐 13：30 -15元",
            "2020年3月10日 教材 11：08 -200元",
            "2020年3月10日 送礼 10：25 -299元",
            "2020年3月10日 单车 9：08 -1元",
            "2020年3月10日 早饭 8：08 -8元",
            "2020年3月9日 晚餐 19：08 -20元",
            "2020年3月9日 午餐 12：08 -12元",
            "2020年3月9日 早餐 7：08 -8元",
            "2020年3月8日 晚餐 18：08 -20元",
            "2020年3月8日 午餐 12：08 -12元",
            "2020年3月8日 收款 9：08 +2000元"
    };
    private String[] s = new String[]{};
    private String zhanghunow;
    //    private Double[] yue_1 =new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    private String mstr = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityxianshi);

        mlv1 = findViewById(R.id.lv_datas);
        btn_year = findViewById(R.id.year);
        btn_month = findViewById(R.id.month);
        btn_day = findViewById(R.id.day);
        StringBuilder text = new StringBuilder();
        Intent i = getIntent();
        zhanghunow = i.getStringExtra("zh");


        btn_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdate(0);
                mlv1.setAdapter(new MyListAdapter(Activityxianshi.this, nian));

            }
        });
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
                mlv1.setAdapter(new MyListAdapter(Activityxianshi.this, yue));
            }
        });
        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdate(2);
                mlv1.setAdapter(new MyListAdapter(Activityxianshi.this, s));
            }
        });
    }

    public void getdate(final int type) {
        final Double[] yue_1 = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        final Double[] yue_2 = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        final Double[] yue_3 = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        final Double[][] nian_1 = {new Double[0]};
        final Double[][] nian_2 = {new Double[0]};
        final Double[][] nian_3 = {new Double[0]};
        final int[] maxyear = new int[1];
        final int[] minyear = new int[1];
        final String[][] ri = {new String[]{}};
        nian = new String[]{};
        BillViewModel.getUserWithBills(zhanghunow).observe(this, new Observer<List<UserWithBills>>() {
            @Override
            public void onChanged(List<UserWithBills> userWithBills) {

                for (int i = 0; i < userWithBills.size(); i++) {
                    UserWithBills userWithBills1 = userWithBills.get(i);
                    maxyear[0]=1900;
                    minyear[0]=2020;
                    for (int q = 0; q < userWithBills1.bills.size(); q++) {
                        Bill bill = userWithBills1.bills.get(q);
                        if(bill.getYear()>maxyear[0]) {maxyear[0]=bill.getYear();}
                        if(bill.getYear()<minyear[0]) {minyear[0]=bill.getYear();}
                    }
                }


//                maxyear[0] = 2020;
//                minyear[0] = 2015;
                nian_1[0] = yearaccount(maxyear[0], minyear[0]);
                nian_2[0] = yearaccount(maxyear[0], minyear[0]);
                nian_3[0] = yearaccount(maxyear[0], minyear[0]);
                for (int i = 0; i < userWithBills.size(); i++) {
                    UserWithBills userWithBills1 = userWithBills.get(i);
                    for (int q = 0; q < userWithBills1.bills.size(); q++) {
                        Bill bill = userWithBills1.bills.get(q);
                        ri[0] = insert(ri[0], bill.getYear() + "年" + bill.getMonth() + "月" + bill.getDay() + "日" + bill.getHour() + "时 " +bill.getMinute() + "分  成员："+bill.getMember()+" 商家：" + bill.getBusiness() + bill.getCategory() + bill.getSubcategory() + " " + bill.getKind() + ":" + bill.getCost());

                        if (bill.getYear() == Integer.parseInt(mstr)) {
                            switch (bill.getKind()) {
                                case "支出":
                                    yue_1[bill.getMonth() - 1] = yue_1[bill.getMonth() - 1] + bill.getCost();
                                    yue_1[bill.getMonth()-1]=formatDouble1(yue_1[bill.getMonth()-1]);
                                    break;
                                case "转账":
                                    yue_2[bill.getMonth() - 1] = yue_2[bill.getMonth() - 1] + bill.getCost();
                                    yue_2[bill.getMonth()-1]=formatDouble1(yue_2[bill.getMonth()-1]);
                                    break;
                                case "收入":
                                    yue_3[bill.getMonth() - 1] = yue_3[bill.getMonth() - 1] + bill.getCost();
                                    yue_3[bill.getMonth()-1]=formatDouble1(yue_3[bill.getMonth()-1]);
                                    break;
                            }
                        }
                        for (int j = 0; j < maxyear[0] - minyear[0] + 1; j++) {
                            if (bill.getYear() == j + minyear[0]) {
                                switch (bill.getKind()) {
                                    case "支出":
                                        nian_1[0][j] = nian_1[0][j] + bill.getCost();
                                        nian_1[0][j]=formatDouble1(nian_1[0][j]);
                                        break;
                                    case "转账":
                                        nian_2[0][j] = nian_2[0][j] + bill.getCost();
                                        nian_2[0][j]=formatDouble1(nian_2[0][j]);
                                        break;
                                    case "收入":
                                        nian_3[0][j] = nian_3[0][j] + bill.getCost();
                                        nian_3[0][j]=formatDouble1(nian_3[0][j]);
                                        break;
                                }
                            }
                        }
                    }
                }
                s = ri[0];

                for (int i = 0; i < maxyear[0] - minyear[0] + 1; i++) {
                    int nian_now = i + minyear[0];
                    nian = insert(nian, nian_now + "年   支出:" + nian_1[0][i] + "转账:" + nian_2[0][i] + "收入" + nian_3[0][i]);
                }
                yue = new String[]{};
                for (int i = 1; i < 13; i++) {
                    yue = insert(yue, mstr + "年" + i + "月  支出:" + yue_1[i - 1] + "转账:" + yue_2[i - 1] + "收入" + yue_3[i - 1]);
                }
                switch (type){
                    case 0:
                        mlv1.setAdapter(new MyListAdapter(Activityxianshi.this, nian));
                        break;
                    case 1:
                        mlv1.setAdapter(new MyListAdapter(Activityxianshi.this, yue));
                        break;
                    case 2:
                        mlv1.setAdapter(new MyListAdapter(Activityxianshi.this, s));
                        break;
                }
            }
        });


    }

    private static String[] insert(String[] arr, String... str) {
        int size = arr.length;
        int newSize = size + str.length;
        String[] tmp = new String[newSize];
        for (int i = 0; i < size; i++) {
            tmp[i] = arr[i];
        }
        for (int i = size; i < newSize; i++) {
            tmp[i] = str[i - size];
        }
        return tmp;
    }

    private static Double[] yearaccount(int max, int min) {
        int length = max - min + 1;
        Double[] accountlist = new Double[length];
        for (int i = 0; i < length; i++) {
            accountlist[i] = 0.0;
        }
        return accountlist;
    }

    public void onClick1(View v) {
        finish();
    }

    public void showNormalDialog() {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(
                Activityxianshi.this);
        final EditText mEditText = new EditText(Activityxianshi.this);
        /* @setIcon 设置对话框图标
         * 			在这里我并没有使用图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         * @setView 装入一个EditView
         */
        normalDialog.setTitle("你好");
        normalDialog.setMessage("请选择年份");
        normalDialog.setView(mEditText);
        /*
         *  对话框中的Button按钮设置
         *  @setPositiveButton 会出现在第一个位置
         *  @setNeutralButton  会出现在第二个位置
         *  @setNegativeButton 会出现在第三个位置
         */
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //mstr 是全局变量，方便简单的使用
                        mstr = mEditText.getText().toString();
                        getdate(1);
                        mlv1.setAdapter(new MyListAdapter(Activityxianshi.this, yue));
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }
                });
        //显示
        normalDialog.show();
    }
    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }
}