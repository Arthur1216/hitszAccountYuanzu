package com.example.jumptonext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private EditText moneyEdit;
    private EditText dateEdit;
    private EditText timeEdit;
    private EditText contentEdit;
    // 底部四个布局按钮
    private LinearLayout layoutDate;
    private LinearLayout layoutTime;
    private LinearLayout layoutCancel;
    private LinearLayout layoutSave;
    // 定义显示时间控件
    private Calendar calendar; // 通过Calendar获取系统时间
    private Calendar cal;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int mMoney;
    String yj;
    String ej;
    String zl;
    String cy;
    String sj;



    int num;
    int num2;
    int flag,flag1;
    int tempdiaoyong;

    Spinner yiji_sn;
    Spinner erji_sn;
    Spinner chenyuan_sn;
    Spinner zhonglei_sn;
    Spinner shangjia_sn;
    private EditText newsecet;
    private  String zhanghunow;
    private String[] yiji = new String[]{"无","居家", "出行", "通讯","休闲","学习","人情","医疗"};
    private String[][] erji = new String[][]{{"无"},{"日用品", "水电气", "房租", "物管费", "清洁费"},
            {"公共交通", "打车租车", "油费保养"},
            {"网费", "电话费"},{"体健","聚会","旅游"},{"书本","培训","电子设备"},
            {"孝敬长辈","送礼请客"},{"药品","挂号","检查","美容"}};
    private String[] chenyuan = new String[]{"爸爸", "妈妈", "儿子"};
    private String[] zhonglei = new String[]{"支出", "转账", "收入"};
    private String[] shangjia = new String[]{"无商家","其他","饭堂","银行","商场","公交","超市"};
    private ArrayAdapter<String> adapter1, adapter2;


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        onCreate1();
        // 获取对象
        moneyEdit = (EditText) findViewById(R.id.editTextmoney);
        timeEdit = (EditText) findViewById(R.id.editTextTime2);
        dateEdit = (EditText) findViewById(R.id.showdate);
        calendar = Calendar.getInstance();
        // 点击"日期"按钮布局 设置日期
        //数据库
        Intent i=getIntent();
        zhanghunow=i.getStringExtra("zh");

        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mMonth=cal.get(Calendar.MONTH);
        mYear = cal.get(Calendar.YEAR);
        mDay = cal.get(Calendar.DATE);
        dateEdit.setText(new StringBuilder()
                .append(mYear)
                .append("-")
                .append((mMonth + 1) < 10 ? "0"
                        + (mMonth + 1) : (mMonth + 1))
                .append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
        mHour=cal.get(Calendar.HOUR);
        mMinute=cal.get(Calendar.MINUTE);
        timeEdit.setText(new StringBuilder()
                .append(mHour<10?"0"+mHour:mHour)
                .append("-")
                .append(mMinute<10?"0"+mMinute:mMinute)
        );


        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity2.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                dateEdit.setText(new StringBuilder()
                                        .append(mYear)
                                        .append("-")
                                        .append((mMonth + 1) < 10 ? "0"
                                                + (mMonth + 1) : (mMonth + 1))
                                        .append("-")
                                        .append((mDay < 10) ? "0" + mDay : mDay));
                            }
                        }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        timeEdit.setFocusable(false);
        timeEdit.setFocusableInTouchMode(false);
        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity2.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour,
                                                  int minute) {
                                // TODO Auto-generated method stub
                                mHour = hour;
                                mMinute = minute;
                                // 更新EditText控件日期 小于10加0
                                timeEdit.setText(new StringBuilder()
                                        .append(mHour<10?"0"+mHour:mHour)
                                        .append("-")
                                        .append(mMinute<10?"0"+mMinute:mMinute)
                                );
                            }
                        }, calendar.get(Calendar.HOUR), calendar
                        .get(Calendar.MINUTE), true).show();
            }
        });
    }


    protected void onCreate1() {
        setContentView(R.layout.activity_main2);




        chenyuan_sn = (Spinner) findViewById(R.id.chenyuan_sn);
        chenyuan_sn.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, chenyuan));
        zhonglei_sn = (Spinner) findViewById(R.id.zhonglei_sn);
        zhonglei_sn.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, zhonglei));
        shangjia_sn = (Spinner) findViewById(R.id.shangjia_sn);
        shangjia_sn.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, shangjia));




        yiji_sn = (Spinner) findViewById(R.id.yiji_sn);
        erji_sn = (Spinner) findViewById(R.id.erji_sn);

        luru();



        adapter1 = new ArrayAdapter<String>(MainActivity2.this,
                android.R.layout.simple_dropdown_item_1line, yiji);
        adapter2 = new ArrayAdapter<String>(MainActivity2.this,
                android.R.layout.simple_dropdown_item_1line, erji[0]);
        yiji_sn.setAdapter(adapter1);
        erji_sn.setAdapter(adapter2);






//        adapter1 = new ArrayAdapter<String>(MainActivity2.this,
//                android.R.layout.simple_dropdown_item_1line, yiji);
//        adapter2 = new ArrayAdapter<String>(MainActivity2.this,
//                android.R.layout.simple_dropdown_item_1line, erji[0]);
//
//        yiji_sn.setAdapter(adapter1);
//        erji_sn.setAdapter(adapter2);

        yiji_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                adapter2 = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_dropdown_item_1line, erji[position]);
                erji_sn.setAdapter(adapter2);
                num = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        erji_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                yj=yiji[num] ;
                ej=erji[num][position];

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        zhonglei_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                zl=zhonglei[position] ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        chenyuan_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                cy=chenyuan[position] ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        shangjia_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                sj=shangjia[position] ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }




    public void onClick(View v) {
        finish();
    }


    public void onClickshow(View v) {
        //Toast.makeText(this,   dateEdit.getText().toString(), Toast.LENGTH_SHORT).show();
        int y = Integer.parseInt(dateEdit.getText().toString().substring(0,4));
        int m = Integer.parseInt(dateEdit.getText().toString().substring(5,7));
        int d = Integer.parseInt(dateEdit.getText().toString().substring(8,10));
        int h = Integer.parseInt(timeEdit.getText().toString().substring(0,2));
        int mu = Integer.parseInt(timeEdit.getText().toString().substring(3,5));
        double mo = Double.parseDouble(moneyEdit.getText().toString());


        Bill bill1= new Bill(yj,ej,y,m,d,h,mu,mo,zl,zhanghunow,sj,cy);
        BillViewModel.insertBills(bill1);

    }

    public void onClick2(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
        View view1=View.inflate(MainActivity2.this,R.layout.alert_layout,null);
        builder.setTitle("新增二级分类").setView(view1);

        newsecet=(EditText)view1.findViewById(R.id.xinjian);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String uname=newsecet.getText().toString();
                Toast.makeText(MainActivity2.this,uname,Toast.LENGTH_SHORT).show();
                //String tempstr[]=erji[num];
                UserDefine userDefine = new UserDefine(yiji[num],uname,null);
                BillViewModel.insertUserDefine(userDefine);
//                erji[num]=insert(erji[num],uname);
//                adapter2 = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_dropdown_item_1line, erji[num]);
//                erji_sn.setAdapter(adapter2);
//                adapter1 = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_dropdown_item_1line, yiji);
//                yiji_sn.setAdapter(adapter1);
            }
        });
        builder.create().show();
    }
    public void onClick3(View v){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
        View view1=View.inflate(MainActivity2.this,R.layout.alert_layout,null);
        builder.setTitle("新增一级分类").setView(view1);

        newsecet=(EditText)view1.findViewById(R.id.xinjian);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String uname=newsecet.getText().toString();
                Toast.makeText(MainActivity2.this,uname,Toast.LENGTH_SHORT).show();
                //String tempstr[]=erji[num];
                yiji=insert(yiji,uname);

                android.app.AlertDialog.Builder builder2=new android.app.AlertDialog.Builder(MainActivity2.this);
                View view2=View.inflate(MainActivity2.this,R.layout.alert_layout,null);
                builder2.setTitle("新增二级分类").setView(view2);
                newsecet=(EditText)view2.findViewById(R.id.xinjian);
                builder2.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uname=newsecet.getText().toString();
                        Toast.makeText(MainActivity2.this,uname,Toast.LENGTH_SHORT).show();
                        erji=addrows(erji);
                        int num3=erji.length-1;
//                        erji[num3]=insert(erji[num3],uname);
                        UserDefine userDefine = new UserDefine(yiji[num3],uname,null);
                        BillViewModel.insertUserDefine(userDefine);
//                        adapter2 = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_dropdown_item_1line, erji[num3]);
//                        erji_sn.setAdapter(adapter2);
                    }
                });
                builder2.create().show();

//                adapter1 = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_dropdown_item_1line, yiji);
//                yiji_sn.setAdapter(adapter1);
            }
        });


        builder.create().show();
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

    private static String[][] addrows(String[][] arr){
        int rows=arr.length;
        int newRows=rows+1;
        String[] newstr=new String[]{};
        String[][] temp=new String[newRows][];
        temp[rows]=newstr;
        for(int i=0;i<rows;i++){
            temp[i]=arr[i];
        }
        return temp;
    }

    public void luru(){
        BillViewModel.getAllUserDefine().observe(this, new Observer<List<UserDefine>>() {
            @Override

            public void onChanged(List<UserDefine> userDefiness) {

                for (int i = 0; i < userDefiness.size(); i++) {
                    UserDefine userDefine= userDefiness.get(i);
                    for(int j=0;j<yiji.length;j++){
                        if (userDefine.getCategory().equals(yiji[j])){
                            flag=1;
                            tempdiaoyong=j;
                            break;}
                        else{
                            flag=0;
                        }
                    }
                    if(flag==1){
                        for(int q=0;q<erji[tempdiaoyong].length;q++){
                            if(userDefine.getSubcategory().equals(erji[tempdiaoyong][q])){
                                flag1=1;
                                break;
                            }
                            else{
                                flag1=0;
                            }
                        }
                        if(flag1==0){
                            erji[tempdiaoyong]=insert(erji[tempdiaoyong],userDefine.getSubcategory());
                        }
                    }
                    else{
                        yiji=insert(yiji,userDefine.getCategory());
                        erji=addrows(erji);
                        int num3=erji.length-1;
                        erji[num3]=insert(erji[num3],userDefine.getSubcategory());

                    }
                }
                adapter1 = new ArrayAdapter<String>(MainActivity2.this,
                        android.R.layout.simple_dropdown_item_1line, yiji);
                adapter2 = new ArrayAdapter<String>(MainActivity2.this,
                        android.R.layout.simple_dropdown_item_1line, erji[0]);
                yiji_sn.setAdapter(adapter1);
                erji_sn.setAdapter(adapter2);

            }
        });

    }


//    public void onClickshua(View view) {
//        luru();
//        adapter1 = new ArrayAdapter<String>(MainActivity2.this,
//                android.R.layout.simple_dropdown_item_1line, yiji);
//        adapter2 = new ArrayAdapter<String>(MainActivity2.this,
//                android.R.layout.simple_dropdown_item_1line, erji[0]);
//        yiji_sn.setAdapter(adapter1);
//        erji_sn.setAdapter(adapter2);
//    }
}