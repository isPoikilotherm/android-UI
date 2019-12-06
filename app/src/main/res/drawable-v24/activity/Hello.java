package com.example.wenjuan.feature.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import com.example.wenjuan.feature.R;
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
                Intent intent = new Intent(com.example.wenjuan.feature.activity.Hello.this,
                        com.example.wenjuan.feature.activity.LoginActivity.class);
                startActivity(intent);
                com.example.wenjuan.feature.activity.Hello.this.finish();
            }
        };
        timer.schedule(task, 3000);
    }
}










