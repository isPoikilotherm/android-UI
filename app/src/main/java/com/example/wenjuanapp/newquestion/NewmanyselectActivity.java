package com.example.wenjuanapp.newquestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class NewmanyselectActivity extends Activity {
    private ImageView back;
    private Button btn_next,btn_finish;
    private EditText edit_title,edit_selector1,edit_selector2,edit_selector3,edit_selector4;
    private String title,a,b,c,d;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manyselect);
        init();
    }
    private void init(){
        back=(ImageView)findViewById(R.id.new_manyselect_back);
        btn_next=(Button)findViewById(R.id.manyselect_next);
        btn_finish=(Button)findViewById(R.id.manyselect_finished);
        edit_title=(EditText)findViewById(R.id.new_manyselect_name);
        edit_selector1=(EditText)findViewById(R.id.new_manyselect_a);
        edit_selector2=(EditText)findViewById(R.id.new_manyselect_b);
        edit_selector3=(EditText)findViewById(R.id.new_manyselect_c);
        edit_selector4=(EditText)findViewById(R.id.new_manyselect_d);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewmanyselectActivity.this,
                        TypeActivity.class);
                startActivity(intent);
                NewmanyselectActivity.this.finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存储题目
               title=edit_title.getText().toString();
                a=edit_selector1.getText().toString();
                b=edit_selector2.getText().toString();
                c=edit_selector3.getText().toString();
                d=edit_selector4.getText().toString();


                //判断是否为空
                if (TextUtils.isEmpty(title)){
                    Toast.makeText(NewmanyselectActivity.this,"题目不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(a)){
                    Toast.makeText(NewmanyselectActivity.this,"选项a不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(b)){
                    Toast.makeText(NewmanyselectActivity.this,"选项b不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(c)){
                    Toast.makeText(NewmanyselectActivity.this,"选项c不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(d)){
                    Toast.makeText(NewmanyselectActivity.this,"选项d不能为空",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String projectname= AnalysisUtils.readProjectName(NewmanyselectActivity.this);
                    connectiongetprojectid(projectname);
                }





            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                获取控件内容
                 */
                title=edit_title.getText().toString();
                a=edit_selector1.getText().toString();
                b=edit_selector2.getText().toString();
                c=edit_selector3.getText().toString();
                d=edit_selector4.getText().toString();


                //判断是否为空
                if (TextUtils.isEmpty(title)){
                    Toast.makeText(NewmanyselectActivity.this,"题目不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(a)){
                    Toast.makeText(NewmanyselectActivity.this,"选项a不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(b)){
                    Toast.makeText(NewmanyselectActivity.this,"选项b不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(c)){
                    Toast.makeText(NewmanyselectActivity.this,"选项c不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(d)){
                    Toast.makeText(NewmanyselectActivity.this,"选项d不能为空",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String projectname= AnalysisUtils.readProjectName(NewmanyselectActivity.this);
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
                            Log.i(title, "onSuccess: ");
                            connectionsavequestion(project_id,title,a,b,c,d);
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

        String url=address.HOST+"/andriod/addmanyselect";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id",project_id);
        params.put("manyselect_name",oneselect_name);
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

                            Intent intent = new Intent(NewmanyselectActivity.this,
                                    TypeActivity.class);
                            startActivity(intent);
                            NewmanyselectActivity.this.finish();
                        }
                        else {
                            Toast.makeText(NewmanyselectActivity.this, "该问题无法保存！", Toast.LENGTH_SHORT).show();
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
                            connectionsavequestion1(project_id,title,a,b,c,d);
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

        String url=address.HOST+"/andriod/addmanyselect";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id",project_id);
        params.put("manyselect_name",oneselect_name);
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

                            Intent intent = new Intent(NewmanyselectActivity.this,
                                    ProjectcontentnewActivity.class);
                            startActivity(intent);
                            NewmanyselectActivity.this.finish();
                        }
                        else {
                            Toast.makeText(NewmanyselectActivity.this, "该问题无法保存！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



}
