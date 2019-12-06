package com.example.wenjuanapp.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wenjuanapp.LoginActivity;
import com.example.wenjuanapp.R;
import com.example.wenjuanapp.SettingActivity;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MyInfoView extends Activity {
    private Activity mContext;
    private RelativeLayout project,set;
    private ImageButton bt1,bt2;
    private String name;
    private TextView textView;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_myinfo);
        name=AnalysisUtils.readLoginUserName(MyInfoView.this);
        textView = (TextView) findViewById(R.id.tv_user_name);
        connection(name);
        init();

    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;//不执行父类点击事件
        }
        return false;//继续执行父类其他点击事件
    }

    private void init(){
        project=(RelativeLayout)findViewById(R.id.me_project);
        set=(RelativeLayout)findViewById(R.id.me_setting);
        bt1=(ImageButton)findViewById(R.id.me_main_btn);
        bt2=(ImageButton)findViewById(R.id.me_project_btn);



        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoView.this,
                        ProjectView.class);
                startActivity(intent);
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoView.this,
                        SettingActivity.class);
                startActivity(intent);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoView.this,
                        MainView.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoView.this,
                        ProjectView.class);
                startActivity(intent);
            }
        });




    }

    public void connection(String name) {
        String url = address.HOST+"/andriod/getpetname";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("ID", name);
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {
                        JSONObject jsonObject = (JSONObject)response.get(0);
                        String petname = jsonObject.getString("pet_name");
                        textView.setText(petname);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

    }




}







