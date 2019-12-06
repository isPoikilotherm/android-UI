package com.example.wenjuanapp.projectcontent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.wenjuanapp.newquestion.NewmanyselectActivity;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.view.ProjectView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class NewprojectActivity extends Activity implements View.OnClickListener{
    private ImageView back;
    private Button add_question;
    private EditText project_name,direction;
    private TextView end_time;
   private String pname,pdire,endtime,time;
    private TextView mTvSelectedDate;
    private CustomDatePicker mDatePicker;
    SimpleDateFormat mFormat=new SimpleDateFormat("yyyy-MM-dd");
    Date curdate=new Date(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        findViewById(R.id.ll_date).setOnClickListener(this);
        mTvSelectedDate = findViewById(R.id.tv_selected_date);

        init();
    }
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    private void init(){
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2099-05-01", false);
        mTvSelectedDate.setText("2019-4-23");

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

        back=(ImageView)findViewById(R.id.new_project_back);
        add_question=(Button)findViewById(R.id.btn_add_question);
        project_name=(EditText)findViewById(R.id.et_project_name) ;
        direction=(EditText)findViewById(R.id.new_direction_content) ;
        end_time=  (TextView)findViewById((R.id.tv_selected_date))    ;



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewprojectActivity.this,
                        ProjectView.class);
                startActivity(intent);
            }
        });
        add_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pname=project_name.getText().toString().trim();
                pdire=direction.getText().toString().trim();
                endtime=end_time.getText().toString().trim();
                time=mFormat.format(curdate);
                int  i=endtime.compareTo(time);

                if (TextUtils.isEmpty(pname)){
                    Toast.makeText(NewprojectActivity.this,"请输入问卷名称",
                            Toast.LENGTH_SHORT).show();
                    }else if (i<0){
                    Toast.makeText(NewprojectActivity.this,"问卷结束时间不能小于当前时间",
                            Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(pdire))
                {
                    pdire="感谢您能抽出几分钟时间来参加本次答题，现在我们马上开始吧！";
                    connectionURLfindID();
                }else{
                    connectionURLfindID();
                }




            }
        });
    }

    public void connectionURLfindID() {

        String url = address.HOST+"/andriod/findid";
        RequestParams params = new RequestParams(); // 绑定参数
        String id=AnalysisUtils.readLoginUserName(NewprojectActivity.this);
        params.put("ID", id);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        int id =(int)response.getInt("msg");
                        int  cananwer=1;
                        connectionURL(id,pname,pdire,endtime,cananwer);

                        // test();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void connectionURL(Integer user_id,String project_name,String direction,String end_time,Integer can_answer){

        String url=address.HOST+"/andriod/addproject";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("user_id",user_id);
        params.put("project_name",project_name);
        params.put("direction",direction);
        params.put("end_time",end_time);
        params.put("can_answer",can_answer);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("msg")==true){
                            SharedPreferences sp = getSharedPreferences("project", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();  //获取编辑器
                            final String name=pname;
                            editor.putString ("project_name", name) ;
                            editor.commit() ;

                            Intent intent = new Intent(NewprojectActivity.this,
                                    TypeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(NewprojectActivity.this, "你已经有该项目！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
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
