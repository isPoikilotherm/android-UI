package com.example.wenjuanapp.template;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.LookProjectcontentActivity;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.vo.ProjectVO;
import com.example.wenjuanapp.vo.TemplateVO;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class TemplateView extends Activity {
    private ImageView back;
    private List<TemplateVO> datalist = new ArrayList<TemplateVO>();
    public ListView lv;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        connectiongetview();
        init();
    }
    private void init() {
        list.clear();
        lv=(ListView) findViewById(R.id.template_lv);
        back=(ImageView) findViewById(R.id.template_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemplateView.this.finish();
            }
        });



    }


    public void connectiongetview() {
        String url = address.HOST+"/andriod/selecttemplate";
//        RequestParams params = new RequestParams(); // 绑定参数
//        params.put("user_id", id);
        // Log.i("onSuccess","111111111111");
        HttpUtil.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {

                        for (int i = 0 ; i < response.length() ;i++){
                            JSONObject jsonObject = (JSONObject)response.get(i);
                            Integer template_id = Integer.valueOf(jsonObject.getInt("template_id"));
                            String template_name = jsonObject.getString("template_name");
                            String template_direction = jsonObject.getString("template_direction");

                            TemplateVO templateVO = new TemplateVO(template_id,template_name,template_direction);
                            datalist.add(templateVO);

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


    class MyAdapter extends BaseAdapter {

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
            View view=View.inflate(TemplateView.this,R.layout.project_list_item,null);
            TextView tv = view.findViewById(R.id.myTextView);
        final int template_id=datalist.get(position).getTemplate_id();
            final String template_name=datalist.get(position).getTemplate_name();
            tv.setText(template_name);
            final String template_direction=datalist.get(position).getTemplate_direction();

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TemplateView.this,TemplateContent.class);
                    intent.putExtra("id",template_id);
                    intent.putExtra("name",template_name);
                    intent.putExtra("direction",template_direction);
                    startActivity(intent);


                }
            });

            return view;
        }
    }






}
