package com.example.wenjuanapp.removequestion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.ProjectQuestionActivity;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ShortanswerActivity  extends Activity {
    private ImageView back;
    private Button btn_save;
    private TextView delete;
    private EditText shortanswer_name;
    private Integer question_id;
    private String old_shortanswer_name;
    private String new_shortanswer_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_scoring);
        Intent intent = getIntent();
        question_id = intent.getIntExtra("question_id", 0);

        old_shortanswer_name = intent.getStringExtra("name");

        init();
    }

    private void init() {
        back = (ImageView) findViewById(R.id.remove_scoring_back);
        btn_save = (Button) findViewById(R.id.remove_scoring_save);
        delete=(TextView)findViewById(R.id.scoring_question_delete);
        shortanswer_name = (EditText) findViewById(R.id.remove_scoring_name);

        shortanswer_name.setText(old_shortanswer_name);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ShortanswerActivity.this)
                        .setTitle("是否删除")
                        .setMessage("是否删除该问题？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                connectiondeletequestion(question_id);
                                //connectiondeleteproject(project_id);
                                //   clearprojectStatus();
                            }
                        })
                        .show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShortanswerActivity.this,ProjectQuestionActivity.class);
                startActivity(intent);
                ShortanswerActivity.this.finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_shortanswer_name=shortanswer_name.getText().toString().trim();

                if (TextUtils.isEmpty(new_shortanswer_name)){
                    Toast.makeText(ShortanswerActivity.this,"请输入问题名称",
                            Toast.LENGTH_SHORT).show();
                }else {
                    connectionURL(question_id,new_shortanswer_name);
                }

            }
        });
    }

    public void connectionURL(Integer question_id,String question_name){

        String url=address.HOST+"/andriod/updateshortanswer";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("question_id",question_id);
        params.put("question_name",question_name);

        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){
//
                            Intent intent=new Intent(ShortanswerActivity.this,ProjectQuestionActivity.class);
                            startActivity(intent);
                            ShortanswerActivity.this.finish();
                        }
                        else {
                            Toast.makeText(ShortanswerActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void connectiondeletequestion(int id) {
        String url = address.HOST+"/andriod/deleteshortanswer";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("question_id", id);
        // Log.i("onSuccess","111111111111");
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {

                        if(response.getBoolean("msg")==true){

                            Intent intent=new Intent(ShortanswerActivity.this,ProjectQuestionActivity.class);
                            startActivity(intent);
                            ShortanswerActivity.this.finish();

                        }
                        else {
                            Toast.makeText(ShortanswerActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
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
