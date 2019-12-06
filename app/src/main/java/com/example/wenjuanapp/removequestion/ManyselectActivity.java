package com.example.wenjuanapp.removequestion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.ProjectQuestionActivity;
import com.example.wenjuanapp.projectcontent.ProjectcontentActivity;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ManyselectActivity extends Activity {
    private ImageView back;
    private Button btn_save;
    private TextView delete;
    private EditText manyselect_name,manyselect_a,manyselect_b,manyselect_c,manyselect_d;
    private Integer question_id;
    private String project_name,select_name,option_a,option_b,option_c,option_d;
    private String new_manyselect_name,new_option_a,new_option_b,new_option_c,new_option_d;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_select);
        Intent intent=getIntent();
        question_id=intent.getIntExtra("question_id",0);
     //   Log.i(String.valueOf(question_id), "得到问题ID ");

        //question_id= Integer.valueOf(id);

        project_name=intent.getStringExtra("project_name");
        select_name=intent.getStringExtra("name");
        option_a=intent.getStringExtra("option1");
        option_b=intent.getStringExtra("option2");
        option_c=intent.getStringExtra("option3");
        option_d=intent.getStringExtra("option4");
        init();
    }
    private  void init(){
        back=(ImageView)findViewById(R.id.remove_oneselect_back);
        btn_save=(Button)findViewById(R.id.remove_oneselect_save);
        delete=(TextView)findViewById(R.id.select_question_delete);
        manyselect_name=(EditText)findViewById(R.id.remove_oneselect_name);
        manyselect_a=(EditText)findViewById(R.id.remove_oneselect_a);
        manyselect_b=(EditText)findViewById(R.id.remove_oneselect_b);
        manyselect_c=(EditText)findViewById(R.id.remove_oneselect_c);
        manyselect_d=(EditText)findViewById(R.id.remove_oneselect_d);

        manyselect_name.setText(select_name);
        manyselect_a.setText(option_a);
        manyselect_b.setText(option_b);
        manyselect_c.setText(option_c);
        manyselect_d.setText(option_d);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ManyselectActivity.this)
                        .setTitle("是否删除")
                        .setMessage("是否删除该问题？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                connectiondeletequestion(question_id);

                            }
                        })
                        .show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManyselectActivity.this,ProjectQuestionActivity.class);
                startActivity(intent);
                ManyselectActivity.this.finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_manyselect_name=manyselect_name.getText().toString().trim();
                new_option_a=manyselect_a.getText().toString().trim();
                new_option_b=manyselect_b.getText().toString().trim();
                new_option_c=manyselect_c.getText().toString().trim();
                new_option_d=manyselect_d.getText().toString().trim();

                if (TextUtils.isEmpty(new_manyselect_name)){
                    Toast.makeText(ManyselectActivity.this,"请输入问题名称",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(new_option_a)){
                    Toast.makeText(ManyselectActivity.this,"请输入a选项",
                            Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(new_option_b)){
                    Toast.makeText(ManyselectActivity.this,"请输入b选项",
                            Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(new_option_c)){
                    Toast.makeText(ManyselectActivity.this,"请输入c选项",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(new_option_d)){
                    Toast.makeText(ManyselectActivity.this,"请输入d选项",
                            Toast.LENGTH_SHORT).show();
                }else {
                    connectionURL(question_id,new_manyselect_name,new_option_a,new_option_b,new_option_c,new_option_d);
                }


            }
        });


    }
    public void connectionURL(Integer question_id,String question_name, String option_a,String option_b,String option_c,String option_d){

        String url=address.HOST+"/andriod/updatemanyselect";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("question_id",question_id);
        params.put("question_name",question_name);
        params.put("option_a",option_a);
        params.put("option_b",option_b);
        params.put("option_c",option_c);
        params.put("option_d",option_d);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){
                       Intent intent=new Intent(ManyselectActivity.this,ProjectQuestionActivity.class);
                      startActivity(intent);
                            ManyselectActivity.this.finish();
                        }
                        else {
                            Toast.makeText(ManyselectActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void connectiondeletequestion(int id) {
        String url = address.HOST+"/andriod/deletemanyselect";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("question_id", id);
        // Log.i("onSuccess","111111111111");
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {

                        if(response.getBoolean("msg")==true){

                            Intent intent=new Intent(ManyselectActivity.this,ProjectQuestionActivity.class);
                            startActivity(intent);
                            ManyselectActivity.this.finish();

                        }
                        else {
                            Toast.makeText(ManyselectActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
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
