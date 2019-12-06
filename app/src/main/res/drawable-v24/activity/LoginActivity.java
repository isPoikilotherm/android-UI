package com.example.wenjuan.feature.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wenjuan.feature.R;
import com.example.wenjuan.feature.activity.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back,tv_register,tv_find_psw;
    private Button btn_login;
    private String userName,psw,spPsw;
    private EditText et_user_name,et_psw;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    private void init(){
        tv_main_title= (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back= (TextView) findViewById(R.id.tv_back) ;
        tv_register= (TextView) findViewById(R.id.tv_register);
        tv_find_psw= (TextView) findViewById(R.id.tv_find_psw);
        btn_login= (Button) findViewById (R.id.btn_login);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R. id.et_psw);
        tv_back.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v){
                com.example.wenjuan.feature.activity.LoginActivity.this.finish();
            }
        });
        tv_register.setOnClickListener(new View .OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(com.example.wenjuan.feature.activity.LoginActivity.this,
                RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    userName = et_user_name.getText().toString().trim();
                    psw=et_psw.getText().toString().trim();
                    String md5Psw = MD5Utils.md5(psw);
                    spPsw = readPsw(userName);
                    if (TextUtils.isEmpty(userName)) {
                        Toast.makeText(com.example.wenjuan.feature.activity.LoginActivity.this, "请输入用户名",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(psw)) {
                        Toast.makeText(com.example.wenjuan.feature.activity.LoginActivity.this, "请输入密码",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else if (md5Psw.equals(spPsw)) {
                        Toast.makeText(com.example.wenjuan.feature.activity.LoginActivity.this, "登录成功",
                                Toast.LENGTH_SHORT).show();
                        saveLoginStatus(true, userName);
                        Intent data = new Intent();
                        data.putExtra("isLogin", true);
                        setResult(RESULT_OK, data);
                        com.example.wenjuan.feature.activity.LoginActivity.this.finish();
                        return;
                    } else if ((!TextUtils.isEmpty(spPsw) &&!
                            md5Psw.equals(spPsw))) {
                        Toast.makeText(com.example.wenjuan.feature.activity.LoginActivity.this, "  ",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(com.example.wenjuan.feature.activity.LoginActivity.this, " ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
    private String readPsw (String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName, "");
    }
    private void saveLoginStatus(boolean status, String userName) {
            //1oginInfo表示文件名
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();  //获取编辑器editor . putBoolean ("isLogin", status) ;
        editor.putString("loginUserName", userName);// 存入登录时的用户名editor. commit() ;   /提交修改}
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //从注册界面传递过来的用户名
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());
                }
            }
        }
}


