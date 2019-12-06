package com.example.wenjuanapp.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.wenjuanapp.projectcontent.LookProjectcontentActivity;
import com.example.wenjuanapp.projectcontent.NewprojectActivity;
import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.ProjectcontentActivity;
import com.example.wenjuanapp.template.TemplateView;
import com.example.wenjuanapp.utils.AnalysisUtils;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.vo.ProjectVO;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

import com.example.wenjuanapp.utils.HttpUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProjectView extends Activity {

    private ImageButton btn1, btn3;
    private ImageView add;
   // private Activity mContext;
    public JSONObject object;
    private List<ProjectVO> datalist = new ArrayList<ProjectVO>();
    public ListView lv;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        connectionURLfindID();
        init();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return false;
    }

    private void init() {
     list.clear();
        lv=(ListView) findViewById(R.id.project_list_view);


        btn1 = (ImageButton) findViewById(R.id.project_main_btn);
        btn3 = (ImageButton) findViewById(R.id.project_me_btn);
        add = (ImageView) findViewById(R.id.project_add);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectView.this,
                        MainView.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectView.this,
                        MyInfoView.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProjectView.this)
                        .setTitle("创建方式")
                        .setMessage("请选择您想要的创建方式")
                        .setPositiveButton("从空白创建", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ProjectView.this,
                                        NewprojectActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("从模板创建", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(ProjectView.this, "暂时没有模板", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProjectView.this,
                                        TemplateView.class);
                                startActivity(intent);
                            }
                        })
                        .show();



            }
        });
    }

    public void connectionURLfindID() {

        String url = address.HOST+"/andriod/findid";
        RequestParams params = new RequestParams(); // 绑定参数
        String id=AnalysisUtils.readLoginUserName(ProjectView.this);
        params.put("ID", id);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                if (statusCode == 200) {
                    try {
                        int id =(int)response.getInt("msg");
                        connectiongetview(id);

                        // test();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void connectiongetview(int id) {
        String url = address.HOST+"/andriod/getview";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("user_id", id);
        // Log.i("onSuccess","111111111111");
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {

                        for (int i = 0 ; i < response.length() ;i++){
                            JSONObject jsonObject = (JSONObject)response.get(i);
                            Integer project_id = Integer.valueOf(jsonObject.getInt("project_id"));
                            Integer user_id = Integer.valueOf(jsonObject.getInt("user_id"));
                            String name = jsonObject.getString("name");
                            String direction = jsonObject.getString("direction");
                            String end_time = jsonObject.getString("end_time");
                            Integer can_answer = Integer.valueOf(jsonObject.getInt("can_answer"));
                            ProjectVO projectVO = new ProjectVO(project_id,user_id,name,direction,end_time,can_answer);
                            datalist.add(projectVO);

                        }
                        MyAdapter myAdapter = new MyAdapter();
                        lv.setAdapter(myAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    private void saveprojectname(String project_name){
        SharedPreferences sp = getSharedPreferences("project", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();  //获取编辑器
        editor.putString ("project_name", project_name) ;
        editor.commit() ;
    }



    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int position) {
            return datalist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 将jiaofeilv文件找出来并转换成view对象
            View view=View.inflate(ProjectView.this,R.layout.project_list_item,null);
            TextView tv = view.findViewById(R.id.myTextView);
           // final int project_id=datalist.get(position).getProject_id();
            final String name=datalist.get(position).getName();
            tv.setText(name);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProjectView.this,LookProjectcontentActivity.class);
                    intent.putExtra("name",name);
                    saveprojectname(name);
                    startActivity(intent);


                }
            });

            return view;
        }
    }

}