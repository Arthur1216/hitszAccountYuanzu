package com.example.jumptonext;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {

    private static int numberin;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String[] infor = new String[]{
            "2020年 收入6000元 支出4000元 余下2000元",
            "2020年9月 收入600元支出400元 余额200元",
            "2020年3月10日 水电气 21：08 -200元"};
    private String[] list;

    public MyListAdapter(Context context,String[] list){
        this.list=list;
        this.mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
    }




    public static void setNumber(Context context,int number) {
        numberin=number;
    }//通过activity中不同按钮决定显示列表的哪一项


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView info1,info2,info3,info4,info5,info6,info7,info8,info9,info10;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if(convertView==null){
            convertView=mLayoutInflater.inflate(R.layout.layout_list_item,null);
            holder=new ViewHolder();
            holder.info1=convertView.findViewById(R.id.tv_item1);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.info1.setText(list[position]);
        //holder.info2.setText("2020年9月 收入600元支出400元 余额200元");
        //holder.info3.setText("2020年3月10日 水电气 21：08 -200元");
        return convertView;
    }
}
