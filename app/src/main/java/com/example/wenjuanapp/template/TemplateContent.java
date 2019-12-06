package com.example.wenjuanapp.template;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.TypeActivity;
import com.example.wenjuanapp.projectcontent.LookProjectcontentActivity;
import com.example.wenjuanapp.projectcontent.NewprojectActivity;
import com.example.wenjuanapp.projectcontent.ProjectcontentActivity;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TemplateContent extends Activity {
    private ImageView back;
    private TextView project_name,direction_content;
    private Button question,use;
    private String name,direction;
    private int project_id;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_content);
        Intent intent=getIntent();
        project_id=intent.getIntExtra("id",0);
        name=intent.getStringExtra("name");
        direction=intent.getStringExtra("direction");

        back=(ImageView)findViewById(R.id.template_content_back);
        question=(Button)findViewById(R.id.template_question);
        use=(Button)findViewById(R.id.template_use);
        project_name=(TextView)findViewById(R.id.template_content_name);
        direction_content=(TextView)findViewById(R.id.template_direction_content);

        project_name.setText(name);
        direction_content.setText(direction);

        init();
    }
    private void init() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemplateContent.this.finish();
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TemplateContent.this,TemplateQuestionView.class);
                intent.putExtra("id",project_id);

                startActivity(intent);

            }
        });

        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                connectionURLfindID();


            }
        });

    }

    private void saveprojectname(String project_name){
        SharedPreferences sp = getSharedPreferences("project", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();  //获取编辑器
        editor.putString ("project_name", project_name) ;
        editor.commit() ;
    }


    public void connectionURLfindID() {

        String url = address.HOST+"/andriod/findid";
        RequestParams params = new RequestParams(); // 绑定参数
        String id=AnalysisUtils.readLoginUserName(TemplateContent.this);
        params.put("ID", id);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        int id =(int)response.getInt("msg");
                        int  cananwer=1;
                        connectionURL(project_id,id,cananwer);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void connectionURL(Integer project_id,Integer id,Integer can_answer){

        String url=address.HOST+"/andriod/usetemplate";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("user_id",id);
        params.put("template_id",project_id);
        params.put("can_answer",can_answer);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){

                            saveprojectname(name);
                            Intent intent = new Intent(TemplateContent.this,
                                    ProjectcontentActivity.class);
                            intent.putExtra("name",name);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(TemplateContent.this, "你已经有该项目！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }













}
