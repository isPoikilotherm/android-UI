package com.example.wenjuanapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ModifyPetNameActivity extends AppCompatActivity {
    private ImageView back;
    private EditText et_petname;
    private Button btn_save;
    private String name, newpetname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_petname);
        //设置此界面为书评
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        name = AnalysisUtils.readLoginUserName(ModifyPetNameActivity.this);
        connection(name);

    }

    private void init() {
        back = (ImageView) findViewById(R.id.modify_petname_back);
        et_petname = (EditText) findViewById(R.id.et_petname);
        btn_save = (Button) findViewById(R.id.btn_petname_save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPetNameActivity.this.finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpetname = et_petname.getText().toString().trim();
                if (TextUtils.isEmpty(newpetname)) {
                    Toast.makeText(ModifyPetNameActivity.this, "昵称不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    connectionURL(name, newpetname);
                }
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
                        JSONObject jsonObject = (JSONObject) response.get(0);
                        String petname = jsonObject.getString("pet_name");
                        et_petname.setText(petname);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
    public void connectionURL(String name,String petname){
        String url = address.HOST+"/andriod//modifypetname";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("username", name);
        params.put("petname", petname);
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if(response.getBoolean("msg")==true){
                            Toast.makeText(ModifyPetNameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            ModifyPetNameActivity.this.finish();
                        }
                        else {
                            Toast.makeText(ModifyPetNameActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
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
