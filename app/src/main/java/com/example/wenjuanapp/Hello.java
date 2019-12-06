package com.example.wenjuanapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wenjuanapp.view.MainView;
import com.example.wenjuanapp.view.ProjectView;

import java.util.Timer;
import java.util.TimerTask;

public class Hello extends AppCompatActivity {
    private TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        tv_version = (TextView) findViewById(R.id.tv_version);
        try {
            // 获取程序包信息
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            tv_version.setText("V" + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("V");
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (readantologin()){
                    Intent intent = new Intent(Hello.this,
                            ProjectView.class);
                    startActivity(intent);
                    Hello.this.finish();
                }else {
                    Intent intent = new Intent(Hello.this,
                            LoginActivity.class);
                    startActivity(intent);
                    Hello.this.finish();
                }
            }
        };
        timer.schedule(task, 3000);
    }

    private  boolean readantologin(){

        SharedPreferences sp = Hello.this.getSharedPreferences("autologin",
                Context.MODE_PRIVATE);
        boolean project_Name = sp.getBoolean("isauto", false);
        if (project_Name){
            return true;
        }else{
            return false;
        }
    }
}










