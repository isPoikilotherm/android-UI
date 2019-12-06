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

public class NewoneselectActivity extends Activity {
    private ImageView back;
    private Button btn_next,btn_finish;
    private EditText oneselect_name,oneselect_a,oneselect_b,oneselect_c,oneselect_d;
    private String name1,option_a,option_b,option_c,option_d;
    //private int tag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_oneselect);
        init();

    }
    private void init(){
        back=(ImageView)findViewById(R.id.new_oneselect_back);
        btn_next=(Button)findViewById(R.id.oneselect_next) ;
        btn_finish=(Button)findViewById(R.id.oneselect_finished) ;
        oneselect_name=(EditText)findViewById(R.id.new_oneselect_name);
        oneselect_a=(EditText)findViewById(R.id.new_oneselect_a);
        oneselect_b=(EditText)findViewById(R.id.new_oneselect_b);
        oneselect_c=(EditText)findViewById(R.id.new_oneselect_c);
        oneselect_d=(EditText)findViewById(R.id.new_oneselect_d);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewoneselectActivity.this,
                        TypeActivity.class);
                startActivity(intent);
                NewoneselectActivity.this.finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name1=oneselect_name.getText().toString().trim();
                 option_a=oneselect_a.getText().toString().trim();
                 option_b=oneselect_b.getText().toString().trim();
                 option_c=oneselect_c.getText().toString().trim();
                 option_d=oneselect_d.getText().toString().trim();


                 //判断是否为空
                if (TextUtils.isEmpty(name1)){
                    Toast.makeText(NewoneselectActivity.this,"题目不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_a)){
                    Toast.makeText(NewoneselectActivity.this,"选项a不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_b)){
                    Toast.makeText(NewoneselectActivity.this,"选项b不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_c)){
                    Toast.makeText(NewoneselectActivity.this,"选项c不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_d)){
                    Toast.makeText(NewoneselectActivity.this,"选项d不能为空",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String projectname= AnalysisUtils.readProjectName(NewoneselectActivity.this);
                    connectiongetprojectid(projectname);
                }


            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=oneselect_name.getText().toString().trim();
                option_a=oneselect_a.getText().toString().trim();
                option_b=oneselect_b.getText().toString().trim();
                option_c=oneselect_c.getText().toString().trim();
                option_d=oneselect_d.getText().toString().trim();

                //判断是否为空
                if (TextUtils.isEmpty(name1)){
                    Toast.makeText(NewoneselectActivity.this,"题目不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_a)){
                    Toast.makeText(NewoneselectActivity.this,"选项a不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_b)){
                    Toast.makeText(NewoneselectActivity.this,"选项b不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_c)){
                    Toast.makeText(NewoneselectActivity.this,"选项c不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(option_d)){
                    Toast.makeText(NewoneselectActivity.this,"选项d不能为空",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String projectname= AnalysisUtils.readProjectName(NewoneselectActivity.this);
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
                            connectionsavequestion(project_id,name1,option_a,option_b,option_c,option_d);
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

    public void connectionsavequestion(int project_id,String oneselect_name,String oneselect_a,String oneselect_b,String oneselect_c,String oneselect_d){

        String url=address.HOST+"/andriod/addoneselect";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id",project_id);
        params.put("oneselect_name",oneselect_name);
        params.put("option_a",oneselect_a);
        params.put("option_b",oneselect_b);
        params.put("option_c",oneselect_c);
        params.put("option_d",oneselect_d);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){

                            Intent intent = new Intent(NewoneselectActivity.this,
                                    TypeActivity.class);
                            startActivity(intent);
                            NewoneselectActivity.this.finish();
                        }
                        else {
                            Toast.makeText(NewoneselectActivity.this, "该问题无法保存！", Toast.LENGTH_SHORT).show();
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
                            connectionsavequestion1(project_id,name1,option_a,option_b,option_c,option_d);
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


    public void connectionsavequestion1(int project_id,String oneselect_name,String oneselect_a,String oneselect_b,String oneselect_c,String oneselect_d){

        String url=address.HOST+"/andriod/addoneselect";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id",project_id);
        params.put("oneselect_name",oneselect_name);
        params.put("option_a",oneselect_a);
        params.put("option_b",oneselect_b);
        params.put("option_c",oneselect_c);
        params.put("option_d",oneselect_d);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){

                            Intent intent = new Intent(NewoneselectActivity.this,
                                    ProjectcontentnewActivity.class);
                            startActivity(intent);
                            NewoneselectActivity.this.finish();
                        }
                        else {
                            Toast.makeText(NewoneselectActivity.this, "该问题无法保存！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
