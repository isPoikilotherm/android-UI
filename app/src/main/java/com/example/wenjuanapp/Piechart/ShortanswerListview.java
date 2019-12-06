package com.example.wenjuanapp.Piechart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.ProjectQuestionActivity;
import com.example.wenjuanapp.removequestion.ShortanswerActivity;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.vo.ShortanswerAnswerVO;
import com.github.mikephil.charting.data.PieData;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ShortanswerListview extends Activity {
    private ImageView back;
    private TextView tv_name;
    private ListView lv;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    private List<ShortanswerAnswerVO> datelist=new ArrayList<ShortanswerAnswerVO>();
    private String name;
    private int question_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortanswer_list);
        Intent intent=getIntent();
        question_id=intent.getIntExtra("question_id",0);
        name=intent.getStringExtra("name");

        connectionshortanswerresult(question_id);
        init();
    }

    private void init(){
        list.clear();
        back=(ImageView)findViewById(R.id.shortanswer_listview_back);
        tv_name=(TextView)findViewById(R.id.result_shortanswer_name);
        lv=(ListView)findViewById(R.id.shortanswer_listview);

        tv_name.setText(name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShortanswerListview.this.finish();
            }
        });


    }


    public void connectionshortanswerresult(int id) {
        String url = address.HOST+"/andriod/selectshortanswersnswer";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("question_id", id);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.i(String.valueOf(statusCode), "onSuccess: ");
                if (statusCode == 200) {
                    try {

                     for (int i=0;i<response.length();i++) {
                         JSONObject jsonObject = (JSONObject) response.get(i);
                         String answer=jsonObject.getString("answer");
                         ShortanswerAnswerVO vo=new ShortanswerAnswerVO(answer);
                         datelist.add(vo);
                     }
                     System.out.println(datelist);
                        MyAdapter myAdapter = new MyAdapter();
                        lv.setAdapter(myAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datelist.size();
        }

        @Override
        public Object getItem(int position) {
            return datelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 将jiaofeilv文件找出来并转换成view对象
            View view=View.inflate(ShortanswerListview.this,R.layout.answer_shortanswer_item,null);

           // RelativeLayout click=view.findViewById(R.id.shortanswer_click);
//            TextView number=view.findViewById(R.id.new_shortanswer_number);
//            number.setText(String.valueOf(i));
//            i=i+1;
            final String answer=datelist.get(position).getAnswer();
         TextView tv = view.findViewById(R.id.myTextView);
//            final String name=datalist.get(position).getQuestion_name();
           tv.setText(answer);

            return view;
        }
    }





}
