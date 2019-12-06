package com.example.wenjuanapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.MD5Utils;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.view.ProjectView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {
    private ImageView tv_back; //返回按钮
    private Button btn_register;  //注册按钮
    // 用户名、密码、再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again;
    //用户名、密码、再次输入的密码的控件的获取值
    private String userName,psw,pswAgain;
    //标题布局
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    private void init(){
        //从main_ title_ bar.xml 頁面布局中获得对应的UI控件

        tv_back=(ImageView) findViewById(R.id.register_back) ;
        //从activity_ register .xm1寅面布局中获得对应的UI控件
        btn_register=(Button) findViewById (R.id.btn_register);
        et_user_name=(EditText) findViewById(R.id.et_user_name);
        et_psw=(EditText) findViewById (R.id.et_psw) ;
        et_psw_again=(EditText) findViewById (R.id.et_psw_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        } );
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                Pattern p = Pattern.compile("[a-zA-Z0-9]*");
                Matcher m1 = p.matcher(userName);
                Matcher m2 = p.matcher(psw);
                Matcher m3 = p.matcher(pswAgain);
                if (userName.length() < 4 || userName.length() > 8) {
                    Toast.makeText(RegisterActivity.this, "请设置用户名为4-8位",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (psw.length() < 4 || psw.length() > 8 ) {
                    Toast.makeText(RegisterActivity.this, "密码请设置4-8位",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (m1.matches() && m2.matches() && m3.matches()) {
                        if (TextUtils.isEmpty(userName)) {
                            Toast.makeText(RegisterActivity.this, "请输入用户名",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(psw)) {
                            Toast.makeText(RegisterActivity.this, "请输入密码",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(pswAgain)) {
                            Toast.makeText(RegisterActivity.this, "请再次输入密码",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (!psw.equals(pswAgain)) {
                            Toast.makeText(RegisterActivity.this, "输入两次的密码不一样",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            String md5Psw = MD5Utils.md5(psw);
                            connectionURL(userName, md5Psw);
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "用户名和密码均只能用数字和字母！",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }


                }
            }
        });
    }

    private void getEditString() {
        userName = et_user_name.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        pswAgain = et_psw_again.getText().toString().trim();
      //  Log.i(userName, "connectionURL1: ");
    }

    public void connectionURL(String name, String md5Psw){

        String url=address.HOST+"/andriod/register";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("ID",name);
        params.put("PW",md5Psw);
        Log.i(name, "connectionURL: ");
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            saveRegisterInfo(userName, psw);
                            Intent data = new Intent();
                            data.putExtra("userName", userName);
                            setResult(RESULT_OK, data);
                            RegisterActivity.this.finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "已有此用户！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("============="+statusCode);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }



        private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences ("loginInfo",
                MODE_PRIVATE);
        String spPsw=sp.getString (userName,"");
        if (!TextUtils.isEmpty (spPsw) ) {
            has_userName = true;
        }
            return has_userName;
        }
        private void saveRegisterInfo(String userName,String psw) {
            String md5Psw = MD5Utils.md5(psw);  //把密码用MD5加密
            // loginInfo表示文件名
            SharedPreferences sp = getSharedPreferences("loginInfo",
                    MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();// 获取编辑器
            // 以用户名为key,密码为value保存到SharedPreferences中
            editor.putString(userName, md5Psw);
            editor.commit();//提交修改
        }
}
















