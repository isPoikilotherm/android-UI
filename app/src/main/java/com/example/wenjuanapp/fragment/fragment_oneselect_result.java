package com.example.wenjuanapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wenjuanapp.Piechart.OneselectPiechartActivity;
import com.example.wenjuanapp.R;
import com.example.wenjuanapp.projectcontent.ProjectResultActivity;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.example.wenjuanapp.vo.OneselectVO;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class fragment_oneselect_result extends Fragment {
    private View view;

    private List<OneselectVO> datalist = new ArrayList<OneselectVO>();
    public ListView lv;
    public ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    private String project_name;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_oneselect,container,false);
        project_name=(((ProjectResultActivity)getActivity()).getName1());//fragment与activity之间的通信
        return  view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //project_name=(((ProjectQuestionActivity)getActivity()).getName1());//fragment与activity之间的通信

        initView();

    }

    private void initView() {
        list.clear();
        this.lv=(ListView)getActivity().findViewById(R.id.project_oneselect_list_view);

        connectiongetid(project_name);


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
            View view=View.inflate(getContext(),R.layout.project_select_list_item,null);

            RelativeLayout click=view.findViewById(R.id.select_question_onclick);
            TextView number=view.findViewById(R.id.number);
            number.setText(String.valueOf(position+1));
        //    i=i+1;
            final Integer question_id=datalist.get(position).getQuestion_id();
            //  Log.i(String.valueOf(question_id), "getView: ");
            TextView tv = view.findViewById(R.id.select_question);
            final String name=datalist.get(position).getQuestion_name();
            tv.setText(name);
            TextView tv1=view.findViewById(R.id.select_question_a);
            final String option1=datalist.get(position).getOption_a();
            tv1.setText(option1);
            TextView tv2=view.findViewById(R.id.select_question_b);
            final String option2=datalist.get(position).getOption_b();
            tv2.setText(option2);
            TextView tv3=view.findViewById(R.id.select_question_c);
            final String option3=datalist.get(position).getOption_c();
            tv3.setText(option3);
            TextView tv4=view.findViewById(R.id.select_question_d);
            final String option4=datalist.get(position).getOption_d();
            tv4.setText(option4);

            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),OneselectPiechartActivity.class);
                    intent.putExtra("question_id",question_id);
                    intent.putExtra("name",name);
                    intent.putExtra("option1",option1);
                    intent.putExtra("option2",option2);
                    intent.putExtra("option3",option3);
                    intent.putExtra("option4",option4);
                    //intent.putExtra("project_name",project_name);
                    //intent.putExtra("tag",tag);
                    startActivity(intent);
                   // ((ProjectQuestionActivity)getActivity()).finish();
                    // Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }


    public void connectiongetid(String project_name) {
        String url = address.HOST+"/andriod/selectprojectbyname";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_name", project_name);
        // Log.i("onSuccess","111111111111");
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == 200) {
                    try {

                        JSONObject jsonObject = (JSONObject)response.get(0);
                        Integer project_id = Integer.valueOf(jsonObject.getInt("project_id"));
                      //    Log.i(String.valueOf(project_id), "onSuccess: ");
                        connectiongetview(project_id);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

        });

    }


    public void connectiongetview(int id) {
        String url = address.HOST+"/andriod/selectoneselectbyprojectid";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("project_id", id);
        // Log.i("onSuccess","111111111111");
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
                            String option_a = jsonObject.getString("option_a");
                            String option_b = jsonObject.getString("option_b");
                            String option_c= jsonObject.getString("option_c");
                            String option_d= jsonObject.getString("option_d");
                            OneselectVO oneselectVO = new OneselectVO(question_id,project_id,question_name,option_a,option_b,option_c,option_d);
                            datalist.add(oneselectVO);
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


}
