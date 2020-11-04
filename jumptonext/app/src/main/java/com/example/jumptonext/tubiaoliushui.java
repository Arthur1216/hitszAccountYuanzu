package com.example.jumptonext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class tubiaoliushui extends AppCompatActivity {
    private ListView mlv1;
    private  String zhanghunow;
    private  String kind;
    private  String way;
    private String[] ri = new String[]{};
    private String[] to_s;

    private  int y;
    private  int m;
    private  int d;
    private  int y1;
    private  int m1;
    private  int d1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tubiaoliushui);
        mlv1 = findViewById(R.id.show);

        Intent i=getIntent();
        Bundle bundle = i.getExtras();
        zhanghunow=bundle.getString("zh");
        kind=bundle.getString("kind");
        y=bundle.getInt("y");
        m=bundle.getInt("m");
        d=bundle.getInt("d");
        y1=bundle.getInt("y1");
        m1=bundle.getInt("m1");
        d1=bundle.getInt("d1");
        way=bundle.getString("way");
        to_s=bundle.getStringArray("to_s");

        BillViewModel.getUserWithBills(zhanghunow).observe(this, new Observer<List<UserWithBills>>() {
            @Override
            public void onChanged(List<UserWithBills> userWithBills) {
                Double[] val = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                ri = new String[]{};
                for (int i = 0; i < userWithBills.size(); i++) {
                    UserWithBills userWithBills1 = userWithBills.get(i);
                    for (int j = 0; j < userWithBills1.bills.size(); j++) {
                        Bill bill = userWithBills1.bills.get(j);
                        if (timeright(bill.getYear(), bill.getMonth(), bill.getDay(),y,m,d,y1,m1,d1)) {
                            String getn = "";
                            switch (way) {
                                case "一级":
                                    getn = bill.getCategory();
                                    break;
                                case "二级":
                                    getn = bill.getSubcategory();
                                    break;
                                case "成员":
                                    getn = bill.getMember();
                                    break;
                            }
                            for (int q = 0; q < to_s.length; q++) {
                                if (getn.equals(to_s[q]) && bill.getKind().equals(kind)) {
                                    ri= insert(ri, bill.getYear() + "年" + bill.getMonth() + "月" + bill.getDay() + "日" + bill.getHour() + "时 " +bill.getMinute() + "分  成员："+bill.getMember()+" 商家：" + bill.getBusiness() + bill.getCategory() + bill.getSubcategory() + " " + bill.getKind() + ":" + bill.getCost());
                                }

                            }
                        }
                    }
                }
                mlv1.setAdapter(new MyListAdapter(tubiaoliushui.this, ri));
            }
        });
    }
    protected boolean timeright(int year, int month, int date,int y,int m,int d,int y1,int m1,int d1) {
        int flag1 = 0;
        int flag2 = 0;
        if (y < year || (y == year && m < month) || (y == year && m == month && d < date)) {
            flag1 = 1;
        }
        if (y1 > year || (y1 == year && m1 > month) || (y1 == year && m1 == month && d1 > date)) {
            flag2 = 1;
        }
        return flag1 == 1 && flag2 == 1;
    }
    public   void  onClick(View v){
        finish();
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
}