package com.example.wenjuanapp.projectcontent;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.date.MainActivity;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.view.ProjectView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LookProjectcontentActivity extends Activity {
    private ImageView back;
    private TextView project_name,direction_content;
    private TextView time,share;
    private Button result,remove,delete;
    private String name1,str;
    private  int project_id,i;
    SimpleDateFormat mFormat=new SimpleDateFormat("yyyy-MM-dd");
    Date curdate=new Date(System.currentTimeMillis());


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_project_content);
       // Intent intent=getIntent();
        name1=AnalysisUtils.readProjectName(this);
      //  Log.i(name1, "onCreate: ");

        project_name=(TextView) findViewById(R.id.look_project_content_name);
        direction_content=(TextView) findViewById(R.id.look_direction_content);
        time=(TextView)findViewById(R.id.time);
        str=mFormat.format(curdate);
       // Log.i(str, "onCreate: ");

        init();
        connection(name1);
    }
    private void init(){
        back=(ImageView)findViewById(R.id.look_project_content_back);
        share=(TextView) findViewById(R.id.look_project_content_share);
        time=(TextView) findViewById(R.id.time);
        result=(Button) findViewById(R.id.look_result);
        delete=(Button)findViewById(R.id.look_delete);
        remove=(Button)findViewById(R.id.look_remove);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearprojectStatus();
                LookProjectcontentActivity.this.finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i<0){
                    Toast.makeText(LookProjectcontentActivity.this, "项目已过期，无法发布！",
                            Toast.LENGTH_SHORT).show();
                }else{

                    ClipboardManager copy = (ClipboardManager) LookProjectcontentActivity.this
                            .getSystemService(Context.CLIPBOARD_SERVICE);
                    copy.setText("http://10.0.2.2:8088/andriod/questionnaire?id="+project_id);


                 Toast.makeText(LookProjectcontentActivity.this, "链接已复制！",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookProjectcontentActivity.this,
                        ProjectResultActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(LookProjectcontentActivity.this)
                        .setTitle("是否删除")
                        .setMessage("是否删除该问卷(删除后该问卷所有信息将全部丢失)？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                connectiondeleteproject(project_id);
                                clearprojectStatus();
                            }
                        })
                        .show();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=project_name.getText().toString().trim();
                Intent intent = new Intent(LookProjectcontentActivity.this,
                        ProjectcontentActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
               // Log.i(name1, "onClick:remove ");
                LookProjectcontentActivity.this.finish();
            }
        });
    }

    private void clearprojectStatus() {
        SharedPreferences sp = getSharedPreferences("project", Context.
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.clear();
        editor.commit();
    }

    public void connection(String name) {
        String url = address.HOST+"/andriod/selectprojectbyname";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_name", name);
        // Log.i("onSuccess","111111111111");
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {
                            JSONObject jsonObject = (JSONObject)response.get(0);
                        project_id=jsonObject.getInt("project_id");
                            String name = jsonObject.getString("name");
                            String direction = jsonObject.getString("direction");
                            String end_time = jsonObject.getString("end_time");
                            i=end_time.compareTo(str);
                            project_name.setText(name);
                            direction_content.setText(direction);
                            time.setText(end_time);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void connectiondeleteproject(int id) {
        String url = address.HOST+"/andriod/deleteprojectbyid";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id", id);
        // Log.i("onSuccess","111111111111");
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {

                        if(response.getBoolean("msg")==true){
                            clearprojectStatus();

                            Intent intent = new Intent(LookProjectcontentActivity.this,
                                    ProjectView.class);
                            startActivity(intent);
                            LookProjectcontentActivity.this.finish();

                        }
                        else {
                            Toast.makeText(LookProjectcontentActivity.this, "退出失败！", Toast.LENGTH_SHORT).show();
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
