package com.example.wenjuan.feature.activity.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wenjuan.feature.R;

public class ProjectView extends Activity {
    private ListView mListView;
    //需要适配的数据
    private String[] names={"项目1","项目1","项目1","项目1","项目1","项目1"};
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        //初始化listview控件
        mListView=(ListView)findViewById(R.id.list_view);
        //创建一个adapter实例
        MyBaseAdapter mAdapter=new MyBaseAdapter();
        //设置adapter
        mListView.setAdapter(mAdapter);
    }
    //创建一个类继承BaseAdapter
    class MyBaseAdapter extends BaseAdapter{
        //得到item总数
        public int getCount(){
            return names.length;
        }
        public Object getItem(int position){
            return names[position];
        }
        public long getItemId(int position){
            return position;
        }
        public View getView(int position, View converView, ViewGroup parent){
            View view=View.inflate(com.example.wenjuan.feature.activity.view.ProjectView.this,R.layout.list_item,null);
            TextView mTextView=(TextView) view.findViewById(R.id.myTextView);
            mTextView.setText(names[position]);
            return view;
        }
    }
}