package com.example.wenjuanapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.*;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.MD5Utils;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

;import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class ModifyPswActivity extends AppCompatActivity {

    private ImageView back;
    private EditText et_original_psw, et_new_psw, et_new_psw_again;
    private Button btn_save;
    private String originalPsw, newPsw, newPswAgain;
    private String userName,original_md5Psw,newPsw_md5Psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        //设置此界面为书评
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();

        userName=AnalysisUtils.readLoginUserName(ModifyPswActivity.this);

    }

    /**
     * 获取界面控件并处理相关控件的点击事件
     */
    private void init() {
        back=(ImageView)findViewById(R.id.modify_psw_back);

        et_original_psw = (EditText) findViewById(R.id.et_original_psw);
        et_new_psw = (EditText) findViewById(R.id.et_new_psw);
        et_new_psw_again = (EditText) findViewById(R.id.et_new_psw_again);
        btn_save = (Button) findViewById(R.id.btn_save);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPswActivity.this.finish();
            }
        });
        //保存按钮的点击事件
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                Pattern p = Pattern.compile("[a-zA-Z0-9]*");
                Matcher m1 = p.matcher(newPsw);
                Matcher m2 = p.matcher(newPswAgain);
                if (newPsw.length() < 4 || newPsw.length() > 8 || newPswAgain.length() < 4 || newPswAgain.length() > 8) {
                    Toast.makeText(ModifyPswActivity.this, "密码请设置4-8位",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (m1.matches() && m2.matches()) {
                        if (TextUtils.isEmpty(originalPsw)) {
                            Toast.makeText(ModifyPswActivity.this, "请输入原始密码",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (!MD5Utils.md5(originalPsw).equals(readPsw())) {
                            Toast.makeText(ModifyPswActivity.this, "输入的密码与原始密码不一致",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (MD5Utils.md5(newPsw).equals(readPsw())) {
                            Toast.makeText(ModifyPswActivity.this, "输入的新密码与原密码不能一致",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(newPsw)) {
                            Toast.makeText(ModifyPswActivity.this, "请输入新密码",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(newPswAgain)) {
                            Toast.makeText(ModifyPswActivity.this, "请再次输入新密码",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (!newPsw.equals(newPswAgain)) {
                            Toast.makeText(ModifyPswActivity.this, "两次输入的新密码不一致",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            original_md5Psw = MD5Utils.md5(originalPsw);
                            newPsw_md5Psw = MD5Utils.md5(newPsw);
                            connectionURL(userName, original_md5Psw);
                        }
                    } else {
                        Toast.makeText(ModifyPswActivity.this, "密码只能用数字和字母！",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    /**
     * 获取控件上的字符串
     */
    private void getEditString() {
        originalPsw = et_original_psw.getText().toString().trim();
        newPsw = et_new_psw.getText().toString().trim();
        newPswAgain = et_new_psw_again.getText().toString().trim();
    }

    /**
     * 修改登录成功时保存在SharedPreferences中的密码
     */
    private void modifyPsw(String newPsw) {
        String md5Psw = MD5Utils.md5(newPsw); //把密码用MD5加密
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();// 获取编辑器
        editor.putString(userName, md5Psw);  //保存新密码
        editor.commit();  //提交修改
    }

    /**
     * 从SharedPreferences中读取原始密码
     */
    private String readPsw() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");
        return spPsw;
    }




    public void connectionURL(String id, String pw){

        String url=address.HOST+"/andriod/login";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("ID",id);
        params.put("PW",pw);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){

                            connectionmodifypsw(userName,newPsw_md5Psw);

                        }
                        else {
                            Toast.makeText(ModifyPswActivity.this, "原密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void connectionmodifypsw(String id, String pw){

        String url=address.HOST+"/andriod/modifypsw";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("username",id);
        params.put("psw",pw);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){
                            Toast.makeText(ModifyPswActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            modifyPsw(newPsw);
                            Intent data = new Intent();
                            data.putExtra("isLogin", true);
                            setResult(RESULT_OK, data);
                            ModifyPswActivity.this.finish();
                        }
                        else {
                            Toast.makeText(ModifyPswActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



}






