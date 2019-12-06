package com.example.wenjuanapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.ProjectQuestionActivity;
import com.example.wenjuanapp.removequestion.ShortanswerActivity;
import com.example.wenjuanapp.template.TemplateQuestionView;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.vo.ShortanswerVO;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class fragment_shortanswer_template  extends Fragment {
    private View view;
    private List<ShortanswerVO> datalist = new ArrayList<ShortanswerVO>();
    public ListView lv;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    private int  project_id;
   // private  int i=1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_shortanswer,container,false);
        return  view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        project_id=(((TemplateQuestionView)getActivity()).getID());//fragment与activity之间的通信
        initView();
    }
    private void initView() {
        list.clear();
        this.lv = (ListView) getActivity().findViewById(R.id.project_shortanswer_list_view);
        // Log.i(project_name, "222222 ");
        connectiongetview(project_id);
    }


    public void connectiongetview(int id) {
        String url = address.HOST+"/andriod/selecttemplateshortanswer";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id", id);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {

                        for (int i = 0 ; i < response.length() ;i++){
                            JSONObject jsonObject = (JSONObject)response.get(i);
                            Integer question_id = Integer.valueOf(jsonObject.getInt("question_id"));
                            Integer project_id = Integer.valueOf(jsonObject.getInt("project_id"));
                            String question_name = jsonObject.getString("question_name");
                            ShortanswerVO shortanswerVO = new ShortanswerVO(question_id,project_id,question_name);
                            datalist.add(shortanswerVO);
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
            View view=View.inflate(getContext(),R.layout.project_shortanswer_list_item,null);

            RelativeLayout click=view.findViewById(R.id.shortanswer_click);
            TextView number=view.findViewById(R.id.new_shortanswer_number);
            number.setText(String.valueOf(position+1));
           // i=i+1;
            final Integer question_id=datalist.get(position).getQuestion_id();
            TextView tv = view.findViewById(R.id.shortanswer_question);
            final String name=datalist.get(position).getQuestion_name();
            tv.setText(name);
            return view;
        }
    }

}
