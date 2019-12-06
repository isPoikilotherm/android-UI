package com.example.wenjuanapp.newquestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wenjuanapp.projectcontent.ProjectcontentnewActivity;
import com.example.wenjuanapp.R;
import com.example.wenjuanapp.TypeActivity;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NewshortanswerActivity extends Activity {
    private ImageView back;
    private Button btn_next,btn_finish;
    private EditText shortanswer_name;
    private String name1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shortanswer);
        init();
    }
    private void init(){
        back=(ImageView)findViewById(R.id.new_shortanswer_back);
        btn_next=(Button)findViewById(R.id.shortanswer_next);
        btn_finish=(Button)findViewById(R.id.shortanswer_finished);
        shortanswer_name=(EditText)findViewById(R.id.new_shortanswer_name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewshortanswerActivity.this,
                        TypeActivity.class);
                startActivity(intent);
                NewshortanswerActivity.this.finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=shortanswer_name.getText().toString().trim();

                if (TextUtils.isEmpty(name1)){
                    Toast.makeText(NewshortanswerActivity.this,"请输入题目",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String projectname= AnalysisUtils.readProjectName(NewshortanswerActivity.this);
                    connectiongetprojectid(projectname);
                }

        }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name1=shortanswer_name.getText().toString().trim();

                if (TextUtils.isEmpty(name1)){
                    Toast.makeText(NewshortanswerActivity.this,"请输入题目",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String projectname= AnalysisUtils.readProjectName(NewshortanswerActivity.this);
                    connectiongetprojectid1(projectname);
                }
            }
        });
    }

    public void connectiongetprojectid(String name) {
        String url = address.HOST+"/andriod/selectprojectbyname";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_name", name);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {

                        for (int i = 0 ; i < response.length() ;i++){
                            JSONObject jsonObject = (JSONObject)response.get(i);
                            Integer project_id = Integer.valueOf(jsonObject.getInt("project_id"));
                            connectionsavequestion(project_id,name1);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void connectionsavequestion(int project_id,String shortanswer_name){

        String url=address.HOST+"/andriod/addshortanswer";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id",project_id);
        params.put("shortanswer_name",shortanswer_name);

        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){

                            Intent intent = new Intent(NewshortanswerActivity.this,
                                    TypeActivity.class);
                            startActivity(intent);
                            NewshortanswerActivity.this.finish();
                        }
                        else {
                            Toast.makeText(NewshortanswerActivity.this, "该问题无法保存！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void connectiongetprojectid1(String name) {
        String url = address.HOST+"/andriod/selectprojectbyname";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_name", name);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {

                        for (int i = 0 ; i < response.length() ;i++){
                            JSONObject jsonObject = (JSONObject)response.get(i);
                            Integer project_id = Integer.valueOf(jsonObject.getInt("project_id"));
                            connectionsavequestion1(project_id,name1);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

    }


    public void connectionsavequestion1(int project_id,String shortanswer_name){

        String url=address.HOST+"/andriod/addshortanswer";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id",project_id);
        params.put("shortanswer_name",shortanswer_name);

        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){

                            Intent intent = new Intent(NewshortanswerActivity.this,
                                    ProjectcontentnewActivity.class);
                            startActivity(intent);
                            NewshortanswerActivity.this.finish();
                        }
                        else {
                            Toast.makeText(NewshortanswerActivity.this, "该问题无法保存！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
