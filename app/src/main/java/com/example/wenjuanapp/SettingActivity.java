package com.example.wenjuanapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.ProjectcontentActivity;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SettingActivity extends AppCompatActivity {

    private ImageView tv_back;
    private RelativeLayout rl_modify_psw,rl_exit_login,rl_modify_petname,rl_delete_user;
    public static SettingActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //设置此界面为竖屏
        init();
    }
    /**
     * 获取界面控件
     */
    private void init() {

        tv_back = (ImageView) findViewById(R.id.set_back);
        rl_modify_psw = (RelativeLayout) findViewById(R.id.rl_modify_psw);
        rl_exit_login = (RelativeLayout) findViewById(R.id.rl_exit_login);
        rl_modify_petname = (RelativeLayout) findViewById(R.id.rl_modify_petname);
        rl_delete_user=(RelativeLayout)findViewById(R.id.rl_delete_user);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        //修改密码的点击事件
        rl_modify_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到修改密码的界面
                Intent intent = new Intent(SettingActivity.this,
                        ModifyPswActivity.class);
                startActivity(intent);
            }
        });
        rl_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("注销账号")
                        .setMessage("是否注销账号？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=AnalysisUtils.readLoginUserName(SettingActivity.this);
                                connectiondeleteuser(name);
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

            }
        });

        //退出登录的点击事件
        rl_exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("退出登录")
                        .setMessage("是否退出登录？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                clearLoginStatus();//清除登录状态和登录时的用户名
                                clearautologininfo();//清除自动登录状态
                                Toast.makeText(SettingActivity.this, "退出登录成功",
                                        Toast.LENGTH_SHORT).show();
                                //退出登录成功后把退出成功的状态传递到LoginActivity中
                                Intent intent = new Intent(SettingActivity.this,
                                        LoginActivity.class);
                                SettingActivity.this.finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        rl_modify_petname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,
                        ModifyPetNameActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 清除SharedPreferences中的登录状态和登录时的用户名
     */
    private void clearLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putBoolean("isLogin", false);
        editor.putString("loginUserName", "");
        editor.commit();
    }
    private void clearautologininfo(){
        SharedPreferences sp = getSharedPreferences("autologin", Context.
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.clear();
        editor.commit();
    }

    public void connectiondeleteuser(String name) {
        String url = address.HOST+"/andriod/deleteuser";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("name", name);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if(response.getBoolean("msg")==true){
                            clearLoginStatus();//清除登录状态和登录时的用户名
                            clearautologininfo();//清除自动登录状态
                            Toast.makeText(SettingActivity.this, "注销成功",
                                    Toast.LENGTH_SHORT).show();
                            //退出登录成功后把退出成功的状态传递到LoginActivity中
                            Intent intent = new Intent(SettingActivity.this,
                                    LoginActivity.class);
                            SettingActivity.this.finish();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SettingActivity.this, "注销失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }




}


