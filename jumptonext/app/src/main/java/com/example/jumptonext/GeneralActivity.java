package com.example.jumptonext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GeneralActivity extends AppCompatActivity {
    BillViewModel billViewModel;

    private String[] zhanghu = new String[]{};
    Spinner zhanghu_sn;
    private ArrayAdapter<String> adapter1;
    EditText newzhanghu;
    int flag;
    private String zhanghunow;


    ListView liushuibiao;
    double money;
    private String[] liushui=new String[]{""};
    int num;

    private ImageButton settingBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billViewModel= ViewModelProviders.of(this).get(BillViewModel.class);

        zhanghu_sn = (Spinner) findViewById(R.id.zhanghu_sn);

        adapter1 = new ArrayAdapter<String>(GeneralActivity.this,
                android.R.layout.simple_dropdown_item_1line, zhanghu);
        zhanghu_sn.setAdapter(adapter1);
        luruzhanghu();
        zhanghu_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                zhanghunow=zhanghu[position] ;
                yu_e();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        liushuibiao=findViewById(R.id.lv_firstdatas);

        yu_e();

        settingBtn = findViewById(R.id.settingButton);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }
    public void yu_e() {
        BillViewModel.getUserWithBills(zhanghunow).observe(this, new Observer<List<UserWithBills>>() {
            @Override
            public void onChanged(List<UserWithBills> userWithBills) {
                liushui=new String[]{};
                TextView textView = (TextView) findViewById(R.id.textView3);
                money=0;
                for(int i = 0;i < userWithBills.size();i++) {
                    UserWithBills userWithBills1 = userWithBills.get(i);
                    for(int j = 0; j<userWithBills1.bills.size();j++){
                        Bill bill = userWithBills1.bills.get(j);
                        liushui = insert(liushui, bill.getYear() + "年" + bill.getMonth() + "月" + bill.getDay() + "日" + bill.getHour() + "时 " +bill.getMinute() + "分  成员："+bill.getMember()+" 商家：" + bill.getBusiness() + bill.getCategory() + bill.getSubcategory() + " " + bill.getKind() + ":" + bill.getCost());
                        switch(bill.getKind()){
                            case "支出" :
                                money=money-bill.getCost();
                                break; //可选
                            case "转账" :
                                money=money-bill.getCost();
                                break; //可选
                            case "收入" :
                                money=money+bill.getCost();
                                break; //可选
                            default:
                                money=100000;

                        }

                    }
                }
                money=formatDouble1(money);
                String strm = money+"";
                textView.setText(strm);
                liushuibiao.setAdapter(new MyListAdapter(GeneralActivity.this,liushui));
            }
        });
    }



    public void onClick(View v) {
        Intent intent = new Intent(GeneralActivity.this, MainActivity2.class);
        intent.putExtra("zh",zhanghunow);
        startActivity(intent);
    }
    public void onClick1(View v) {
        Intent intent = new Intent(GeneralActivity.this, Activityxianshi.class);
        intent.putExtra("zh",zhanghunow);
        startActivity(intent);
    }
    public void onClick4(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(GeneralActivity.this);
        View view1=View.inflate(GeneralActivity.this,R.layout.alert_layout,null);
        builder.setTitle("新增账户").setView(view1);

        newzhanghu=(EditText)view1.findViewById(R.id.xinjian);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String uname=newzhanghu.getText().toString();
                Toast.makeText(GeneralActivity.this,uname,Toast.LENGTH_SHORT).show();
                User user = new User(uname);
                BillViewModel.insertUsers(user);

            }
        });
        builder.create().show();
    }
    public void onClicktubiao(View v) {
        Intent intent = new Intent(GeneralActivity.this, tubiao.class);
        intent.putExtra("zh",zhanghunow);
        startActivity(intent);
    }
    public void qingkong(View v) {
        billViewModel.deleteAllBills();
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

    public void luruzhanghu() {
        BillViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override

            public void onChanged(List<User> users) {

                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    for (int j = 0; j < zhanghu.length; j++) {
                        if (user.getName().equals(zhanghu[j])) {
                            flag = 1;
                            break;
                        } else {
                            flag = 0;
                        }
                    }
                    if (flag == 0) {
                        zhanghu = insert(zhanghu, user.getName());
                    }
                }
                adapter1 = new ArrayAdapter<String>(GeneralActivity.this,
                        android.R.layout.simple_dropdown_item_1line, zhanghu);
                zhanghu_sn.setAdapter(adapter1);
            }
        });
    }

    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }
}