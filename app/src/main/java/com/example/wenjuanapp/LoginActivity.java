package com.example.wenjuanapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import com.example.wenjuanapp.utils.MD5Utils;
import com.example.wenjuanapp.view.ProjectView;
import com.example.wenjuanapp.utils.HttpUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_register,tv_find_psw;
    private Button btn_login;
    private String userName,psw,spPsw;
//    private CheckBox tag;
    private EditText et_user_name,et_psw;
    AsyncHttpClient m_HttpClient;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        m_HttpClient = new AsyncHttpClient();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        tv_register= (TextView) findViewById(R.id.tv_register);
       // tv_find_psw= (TextView) findViewById(R.id.tv_find_psw);
        btn_login= (Button) findViewById (R.id.btn_login);
//        tag=(CheckBox)findViewById(R.id.auto_login);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R. id.et_psw);
        tv_register.setOnClickListener(new View .OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(LoginActivity.this,
                RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
//        tv_find_psw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//
//            }
//        });




        btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    userName = et_user_name.getText().toString().trim();
                    psw=et_psw.getText().toString().trim();
                   final String md5Psw = MD5Utils.md5(psw);
                    //spPsw = readPsw(userName);
                    if (TextUtils.isEmpty(userName)) {
                        Toast.makeText(LoginActivity.this, "请输入用户名",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(psw)) {
                        Toast.makeText(LoginActivity.this, "请输入密码",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        connectionURL(userName,md5Psw);
                    }
//                        if (md5Psw.equals(spPsw)) {
//                        connectionURL(userName,md5Psw);
//                        return;
//                    } else if ((!TextUtils.isEmpty(spPsw) &&!
//                            md5Psw.equals(spPsw))) {
//                        Toast.makeText(LoginActivity.this, "密码错误",
//                                Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        Toast.makeText(LoginActivity.this, "请先注册",
//                                Toast.LENGTH_SHORT).show();
//                    }
                }
        });
    }
//    private String readPsw (String userName) {
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        return sp.getString(userName, "");
//    }
    private void saveLoginStatus(boolean status, String userName) {
            //1oginInfo表示文件名
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();  //获取编辑器
        editor.putBoolean ("isLogin", status) ;
        editor.putString("loginUserName", userName);// 存入登录时的用户名
        editor.commit() ;   //提交修改
    }

    private void clearautologininfo(){
        SharedPreferences sp = getSharedPreferences("autologin", Context.
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.clear();
        editor.commit();
    }

    private void saveautologininfo(boolean tage) {
        //1oginInfo表示文件名
        SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();  //获取编辑器
        editor.putBoolean ("isauto", tage) ;
        editor.commit() ;   //提交修改
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
    public void connectionURL(String id, String pw){

        String url= address.HOST+"/andriod/login";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("ID",id);
        params.put("PW",pw);
        //Toast.makeText(LoginActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("自动登录")
                                    .setMessage("下次是否自动登录此用户？")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            clearautologininfo();
                                            boolean tag1=true;
                                            saveautologininfo(tag1);
                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                                            saveLoginStatus(true, userName);
                                            Intent data = new Intent();
                                            data.putExtra("isLogin", true);
                                            setResult(RESULT_OK, data);
                                            LoginActivity.this.finish();
                                            Intent intent = new Intent(LoginActivity.this,
                                                    ProjectView.class);
                                            startActivityForResult(intent,1);
                                        }
                                    })
                                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            clearautologininfo();
                                            boolean tag1=false;
                                            saveautologininfo(tag1);
                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                                            saveLoginStatus(true, userName);
                                            Intent data = new Intent();
                                            data.putExtra("isLogin", true);
                                            setResult(RESULT_OK, data);
                                            LoginActivity.this.finish();
                                            Intent intent = new Intent(LoginActivity.this,
                                                    ProjectView.class);
                                            startActivityForResult(intent,1);
                                        }
                                    })
                                    .show();

                        }
                        else {
                            clearautologininfo();
                            Toast.makeText(LoginActivity.this, "登录失败！请检查用户名或密码是否输入正确", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

