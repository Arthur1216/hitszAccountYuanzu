package com.example.jumptonext;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class tubiao extends AppCompatActivity {

    PieChart pieChart1;
    Spinner way_sn;
    Spinner kind_sn;
    Spinner yiji_sn;
    private EditText startEdit;
    private EditText endEdit;
    private String[] yiji = new String[]{"无","居家", "出行", "通讯","休闲","学习","人情","医疗"};
    private String[][] erji = new String[][]{{"无"},{"日用品", "水电气", "房租", "物管费", "清洁费"},
            {"公共交通", "打车租车", "油费保养"},
            {"网费", "电话费"},{"体健","聚会","旅游"},{"书本","培训","电子设备"},
            {"孝敬长辈","送礼请客"},{"药品","挂号","检查","美容"}};
    private ArrayAdapter<String> adapter1;

    private String[] kind = new String[]{"支出", "转账", "收入"};
    private String[] chenyuan = new String[]{"爸爸", "妈妈", "儿子"};
    private String[] way = new String[]{"成员", "一级", "二级"};

    Double value[] = {0.0, 0.0, 0.0};
    int exam;
    int flag;
    int flag1;
    int tempdiaoyong;
    private int ej;
    private int num;
    private String way_n;
    private String yiji_n;

    private String kind_n;
    private  String[] erji_n;
    private String zhanghunow;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mYear1;
    private int mMonth1;
    private int mDay1;
    private Calendar calendar; // 通过Calendar获取系统时间

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tubiao);
        pieChart1 = findViewById(R.id.consume_pie1_chart);
        calendar = Calendar.getInstance();
        StringBuilder text = new StringBuilder();
        Intent i = getIntent();
        zhanghunow = i.getStringExtra("zh");

        onCreate1();

    }

    private void bingTu1() {
        pieChart1 = findViewById(R.id.consume_pie1_chart);
        pieChart1.setUsePercentValues(true); //设置为显示百分比
        //pieChart1.setDescription("当天时间分配表");//设置描述
        // pieChart1.setExtraOffsets(5, 5, 5, 5);//设置饼状图距离上下左右的偏移量
        pieChart1.setDrawCenterText(true); //设置可以绘制中间的文字
        pieChart1.setCenterTextColor(Color.BLACK); //中间的文本颜色
        pieChart1.setCenterTextSize(18);  //设置中间文本文字的大小
        pieChart1.setDrawHoleEnabled(true); //绘制中间的圆形
        pieChart1.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
        pieChart1.setHoleRadius(40f);//饼状图中间的圆的半径大小
        pieChart1.setTransparentCircleColor(Color.BLACK);//设置圆环的颜色
        pieChart1.setTransparentCircleAlpha(100);//设置圆环的透明度[0,255]
        pieChart1.setTransparentCircleRadius(40f);//设置圆环的半径值
        pieChart1.setRotationEnabled(false);//设置饼状图是否可以旋转(默认为true)
        pieChart1.setRotationAngle(10);//设置饼状图旋转的角度

        Legend l = pieChart1.getLegend(); //设置比例图
        l.setMaxSizePercent(100);
        l.setTextSize(12);
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);//设置每个tab的显示位置（这个位置是指下图右边小方框部分的位置 ）
        l.setXEntrySpace(10f);
        l.setYEntrySpace(5f);//设置tab之间Y轴方向上的空白间距值
        l.setYOffset(0f);

        //饼状图上字体的设置
        pieChart1.setDrawEntryLabels(false);//设置是否绘制Label
        // pieChart1.setEntryLabelColor(Color.BLACK);//设置绘制Label的颜色
        pieChart1.setEntryLabelTextSize(23f);//设置绘制Label的字体大小

        // pieChart1.animateY(100, Easing.EasingOption.EaseInQuad);//设置Y轴上的绘制动画

        //设置数据百分比和描述
        final String[][] tosearch = {{}};
        switch (way_n) {
            case "一级":
                tosearch[0] = yiji;
                break;
            case "二级":
                tosearch[0] = erji[num];
                break;
            case "成员":
                tosearch[0] = chenyuan;
                break;

        }
//        Toast.makeText(tubiao.this,  tosearch[0][0], Toast.LENGTH_LONG).show();
        final ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        BillViewModel.getUserWithBills(zhanghunow).observe(this, new Observer<List<UserWithBills>>() {
            @Override
            public void onChanged(List<UserWithBills> userWithBills) {
                Double[] val = {0.0, 0.0, 0.0,0.0, 0.0, 0.0,0.0, 0.0, 0.0};
                for (int i = 0; i < userWithBills.size(); i++) {
                    UserWithBills userWithBills1 = userWithBills.get(i);
                    for (int j = 0; j < userWithBills1.bills.size(); j++) {
                        Bill bill = userWithBills1.bills.get(j);
                        if (timeright(bill.getYear(), bill.getMonth(), bill.getDay())) {
                            String getn = "";
                            switch (way_n) {
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
                            for (int q = 0; q < tosearch[0].length; q++) {
                                if (getn.equals(tosearch[0][q]) && bill.getKind().equals(kind_n)) {
                                    val[q] = val[q] + bill.getCost();

                                }

                            }
                        }
                    }
                }
                for (int p = 0; p < tosearch[0].length; p++) {
                    exam= val[p].intValue();
                    pieEntries.add(new PieEntry(exam,tosearch[0][p]));

                }
                String centerText = kind_n;
                pieChart1.setCenterText(centerText);//设置圆环中间的文字
                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                ArrayList<Integer> colors = new ArrayList<>();

                // 饼图颜色
                colors.add(Color.rgb(0, 255, 0));
                colors.add(Color.rgb(255, 255, 0));
                colors.add(Color.rgb(255, 0, 0));
                colors.add(Color.rgb(255, 0, 255));
                colors.add(Color.rgb(244, 164, 96));
                colors.add(Color.rgb(30, 144, 255));
                colors.add(Color.rgb(144, 144, 144));
                pieDataSet.setColors(colors);

                pieDataSet.setSliceSpace(0f);//设置选中的Tab离两边的距离
                pieDataSet.setSelectionShift(5f);//设置选中的tab的多出来的
                PieData pieData = new PieData();
                pieData.setDataSet(pieDataSet);

                //各个饼状图所占比例数字的设置
                pieData.setValueFormatter(new PercentFormatter());//设置%
                pieData.setValueTextSize(18f);
                pieData.setValueTextColor(Color.BLUE);

                pieChart1.setData(pieData);
                pieChart1.highlightValues(null);
                pieChart1.invalidate();
            }
        });

    }

    protected void onCreate1() {
        setContentView(R.layout.activity_tubiao);
        way_sn = (Spinner) findViewById(R.id.way_sn);
        way_sn.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, way));
        kind_sn = (Spinner) findViewById(R.id.kind_sn);
        kind_sn.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, kind));
        yiji_sn = (Spinner) findViewById(R.id.yiji_sn);
        yiji_sn.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, yiji));

        luru();




        startEdit = (EditText) findViewById(R.id.startdate);
        startEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                new DatePickerDialog(tubiao.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                startEdit.setText(new StringBuilder()
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

        startEdit = (EditText) findViewById(R.id.startdate);
        startEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                new DatePickerDialog(tubiao.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                startEdit.setText(new StringBuilder()
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
        endEdit = (EditText) findViewById(R.id.enddate);
        endEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                new DatePickerDialog(tubiao.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // TODO Auto-generated method stub
                                mYear = year;
                                mMonth = month;
                                mDay = day;
                                // 更新EditText控件日期 小于10加0
                                endEdit.setText(new StringBuilder()
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
        kind_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                kind_n = kind[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        way_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                way_n = way[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        yiji_sn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                yiji_n = yiji[position];
                num=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClick(View v) {
        finish();
    }
    public void onClicktoliushui(View v) {
        String []to_s={};
        switch (way_n) {
            case "一级":
                to_s = yiji;
                break;
            case "二级":
                to_s = erji[num];
                break;
            case "成员":
                to_s = chenyuan;
                break;

        }
        Intent intent = new Intent(tubiao.this, tubiaoliushui.class);
        Bundle bundle = new Bundle();
        bundle.putString("zh",zhanghunow);
        bundle.putString("kind",kind_n);
        bundle.putString("way",way_n);
        bundle.putInt("y",Integer.parseInt(startEdit.getText().toString().substring(0, 4)));
        bundle.putInt("m",Integer.parseInt(startEdit.getText().toString().substring(5, 7)));
        bundle.putInt("d",Integer.parseInt(startEdit.getText().toString().substring(8, 10)));
        bundle.putInt("y1",Integer.parseInt(endEdit.getText().toString().substring(0, 4)));
        bundle.putInt("m1",Integer.parseInt(endEdit.getText().toString().substring(5, 7)));
        bundle.putInt("d1",Integer.parseInt(endEdit.getText().toString().substring(8, 10)));
        bundle.putStringArray("to_s",to_s);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void drawpicture1(View v) {
        bingTu1();
    }

    protected boolean timeright(int year, int month, int date) {
        int y = Integer.parseInt(startEdit.getText().toString().substring(0, 4));
        int m = Integer.parseInt(startEdit.getText().toString().substring(5, 7));
        int d = Integer.parseInt(startEdit.getText().toString().substring(8, 10));
        int y1 = Integer.parseInt(endEdit.getText().toString().substring(0, 4));
        int m1 = Integer.parseInt(endEdit.getText().toString().substring(5, 7));
        int d1 = Integer.parseInt(endEdit.getText().toString().substring(8, 10));
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
                adapter1 = new ArrayAdapter<String>(tubiao.this,
                        android.R.layout.simple_dropdown_item_1line, yiji);
                yiji_sn.setAdapter(adapter1);

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
}