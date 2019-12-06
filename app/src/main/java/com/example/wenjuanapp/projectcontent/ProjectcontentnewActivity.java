package com.example.wenjuanapp.projectcontent;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.wenjuanapp.TypeActivity;
import com.example.wenjuanapp.date.CustomDatePicker;
import com.example.wenjuanapp.date.DateFormatUtils;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.view.ProjectView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ProjectcontentnewActivity extends Activity implements View.OnClickListener {
    private ImageView back, save;
    private EditText et_project_content_name, direction_content;
    private Button question,addquestion;
 //   private TextView end_time1;
    private String projectname,end_time1;
    private  int project_id;
    private TextView mTvSelectedDate;
    private CustomDatePicker mDatePicker;
    SimpleDateFormat mFormat=new SimpleDateFormat("yyyy-MM-dd");
    Date curdate=new Date(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_content_new);
        findViewById(R.id.ll_date).setOnClickListener(this);
        mTvSelectedDate = findViewById(R.id.tv_selected_date);


        et_project_content_name = (EditText) findViewById(R.id.et_project_content_name_new
        );

        direction_content = (EditText) findViewById(R.id.direction_content_new);
       // end_time1 = (TextView) findViewById(R.id.time_new);

        projectname = AnalysisUtils.readProjectName(this);

        init();
        connection(projectname);
    }

    /**
     * 清除SharedPreferences中的项目名称
     */
    private void clearprojectStatus() {
        SharedPreferences sp = getSharedPreferences("project", Context.
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.clear();
        editor.commit();
    }

    private void init() {

        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2099-05-01", false);

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);


        back = (ImageView) findViewById(R.id.new_project_content_back_new);
        save = (ImageView) findViewById(R.id.new_project_content_save_new);
        question = (Button) findViewById(R.id.question_new);
        addquestion=(Button)findViewById(R.id.add_question_new);

        addquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProjectcontentnewActivity.this,
                        TypeActivity.class);
                startActivity(intent);
            }
        });


        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_project_content_name.getText().toString().trim();
                Intent intent = new Intent(ProjectcontentnewActivity.this,
                        ProjectQuestionActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProjectcontentnewActivity.this)
                        .setTitle("是否退出")
                        .setMessage("您的项目尚未保存，退出后项目将丢失!")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                connectiondeleteproject(project_id);
                            }
                        })
                        .show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProjectcontentnewActivity.this)
                        .setTitle("是否保存")
                        .setMessage("是否保存您的项目？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=et_project_content_name.getText().toString().trim();
                                String direction=direction_content.getText().toString().trim();
                                String end_time=mTvSelectedDate.getText().toString().trim();
                                end_time1=mFormat.format(curdate);
                                int  i=end_time.compareTo(end_time1);

                                if (TextUtils.isEmpty(name))
                                {
                                    Toast.makeText(ProjectcontentnewActivity.this,"请输入问卷名称",
                                            Toast.LENGTH_SHORT).show();

                                }else if (i<0){
                                    Toast.makeText(ProjectcontentnewActivity.this,"问卷结束时间不能小于当前时间",
                                            Toast.LENGTH_SHORT).show();


                                }else if (TextUtils.isEmpty(direction)){
                                    direction="感谢您能抽出几分钟时间来参加本次答题，现在我们马上开始吧！";
                                    connectionupdateproject(project_id,name,direction,end_time);
                                }else{
                                    connectionupdateproject(project_id,name,direction,end_time);
                                }


                            }
                        })
                        .show();
            }
        });

    }


    public void connection(String name) {
        String url = address.HOST+"/andriod/selectprojectbyname";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_name", name);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {
                            JSONObject jsonObject = (JSONObject) response.get(0);

                            project_id=jsonObject.getInt("project_id");
                            String name = jsonObject.getString("name");
                            String direction = jsonObject.getString("direction");
                            String end_time = jsonObject.getString("end_time");

                            et_project_content_name.setText(name);
                            direction_content.setText(direction);
                        mTvSelectedDate.setText(end_time);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
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

                        if (response.getBoolean("msg") == true) {
                            clearprojectStatus();

                            Intent intent = new Intent(ProjectcontentnewActivity.this,
                                    ProjectView.class);
                            startActivity(intent);
                            ProjectcontentnewActivity.this.finish();

                        } else {
                            Toast.makeText(ProjectcontentnewActivity.this, "退出失败！", Toast.LENGTH_SHORT).show();
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

        public void connectionupdateproject(int id,String name,String direction,String end_time) {
            String url = address.HOST+"/andriod/updateproject";
            RequestParams params = new RequestParams(); // 绑定参数
            params.put("project_id", id);
            params.put("project_name", name);
            params.put("direction", direction);
            params.put("end_time", end_time);
            HttpUtil.get(url, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if (statusCode == 200) {
                        try {

                            if(response.getBoolean("msg")==true){
                                clearprojectStatus();

                                Intent intent = new Intent(ProjectcontentnewActivity.this,
                                        ProjectView.class);
                                startActivity(intent);
                                ProjectcontentnewActivity.this.finish();

                            }
                            else {
                                Toast.makeText(ProjectcontentnewActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
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







    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                // 日期格式为yyyy-MM-dd
                mDatePicker.show(mTvSelectedDate.getText().toString());
                break;
        }
    }
}


