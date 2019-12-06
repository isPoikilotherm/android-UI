package com.example.wenjuanapp.Piechart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.utils.HttpUtil;
import com.example.wenjuanapp.utils.address;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ManyselectPiechartActivity extends AppCompatActivity {

    private PieChart mPiechart;
    private TextView mT1, question_name;
    private TextView mT2;
    private TextView mT3;
    private TextView mT4;
    private Integer question_id;
    private ImageView back;
    private String project_name, select_name, option_a, option_b, option_c, option_d;
    public int option_a_number, option_b_number, option_c_number, option_d_number;
    //    private TextView mT5;
//    private TextView mT6;
    private ArrayList<String> xContents;
    private ArrayList<String> xContents1;
    private ArrayList arrayList;
    private ArrayList<Entry> yContent;
    private ArrayList<Integer> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        Intent intent = getIntent();
        question_id = intent.getIntExtra("question_id", 0);
        project_name = intent.getStringExtra("project_name");
        select_name = intent.getStringExtra("name");
        option_a = intent.getStringExtra("option1");
        option_b = intent.getStringExtra("option2");
        option_c = intent.getStringExtra("option3");
        option_d = intent.getStringExtra("option4");
        connectionselectresult(question_id);
        // System.out.println(option_a_number+"1111111");
        initView();


    }

    public void connectionselectresult(int id) {
        String url = address.HOST+"/andriod/selectmanyselectanswer";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("question_id", id);

        HttpUtil.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i(String.valueOf(statusCode), "onSuccess: ");
                if (statusCode == 200) {
                    try {

                    //    System.out.println(response);
                        JSONObject jsonObject = (JSONObject) response.get(0);
                        // Integer a = Integer.valueOf(jsonObject.getString("option_a_number")) ;

                        option_a_number = jsonObject.getInt("option_a_number");
                        option_b_number = jsonObject.getInt("option_b_number");
                        option_c_number = jsonObject.getInt("option_c_number");
                        option_d_number = jsonObject.getInt("option_d_number");

                        PieData mPieData = getPieData(4, 100);
                        showChart(mPiechart, mPieData);


                        //System.out.println(option_a_number);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    /***/
    //这个可以不要，不要看自己需求
    private void initView() {
        mT1 = (TextView) findViewById(R.id.text1);
        mT2 = (TextView) findViewById(R.id.text2);
        mT3 = (TextView) findViewById(R.id.text3);
        mT4 = (TextView) findViewById(R.id.text4);
        back = (ImageView) findViewById(R.id.select_piechart_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManyselectPiechartActivity.this.finish();
            }
        });
        question_name = (TextView) findViewById(R.id.question_name);
        question_name.setText(select_name);
//        mT5 = (TextView) findViewById(R.id.text5);
//        mT6 = (TextView) findViewById(R.id.text6);
        mPiechart = (PieChart) findViewById(R.id.mPieChart);
        PieData mPieData = getPieData(4, 100);
        showChart(mPiechart, mPieData);
    }


    //关键方法在这里-主要用于饼图的画图
    private void showChart(PieChart pieChart, PieData pieData) {

//            pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);//半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
//            pieChart.setHoleRadius(0);  //实心圆

        pieChart.setDescription(""); //添加右下角备注

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        pieChart.setRotationEnabled(true); // 可以手动旋转

        pieChart.setUsePercentValues(true);  //显示成百分比

        pieChart.setCenterText("回答情况分布");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);


        Legend mLegend = pieChart.getLegend();  //设置比例图
//          mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//          mLegend.setForm(Legend.LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
//        pieChart.spin(2000, 0, 360);
    }

    private PieData getPieData(int count, float range) {


        initData();

        PieDataSet pieDataSet = new PieDataSet(yContent, null);
        //设置饼状图之间的距离
        pieDataSet.setSliceSpace(0f);
        //设置饼状图之间的颜色
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        PieData pieData = new PieData(xContents, pieDataSet);

        return pieData;
    }

    //数据源咯，这里我们看预定义饼图区域的颜色、大小、介绍，其中框架会自动计算百分比
    private void initData() {

        // 饼图颜色
        colors = new ArrayList<Integer>();

        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
//        colors.add(Color.rgb(57, 135, 20));
//        colors.add(Color.rgb(77, 105, 20));

        /**展示内容*/
        xContents = new ArrayList<String>();
        xContents.add("A");
        xContents.add("B");
        xContents.add("C");
        xContents.add("D");
//        xContents.add("喷漆");
//        xContents.add("机修");

        xContents1 = new ArrayList<String>();
        xContents1.add(option_a);
        xContents1.add(option_b);
        xContents1.add(option_c);
        xContents1.add(option_d);

        float m1 = option_a_number;

        //  Log.i(String.valueOf(option_a_number), "initData: ");
        float m2 = option_b_number;
        float m3 = option_c_number;
        float m4 = option_d_number;
//        float m5=123;
//        float m6=46;

        arrayList = new ArrayList();
        arrayList.add(option_a_number);
        arrayList.add(option_b_number);
        arrayList.add(option_c_number);
        arrayList.add(option_d_number);
        System.out.println(arrayList + "22222");
        //将数据展示
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mT1.setText(xContents1.get(0) + ":" + option_a_number + "人");
                mT2.setText(xContents1.get(1) + ":" + arrayList.get(1) + "人");
                mT3.setText(xContents1.get(2) + ":" + arrayList.get(2) + "人");
                mT4.setText(xContents1.get(3) + ":" + arrayList.get(3) + "人");
//                mT5.setText(xContents.get(2)+":"+ arrayList.get(2)+"元");
//                mT6.setText(xContents.get(2)+":"+ arrayList.get(2)+"元");
            }
        });
        //展示比例
        yContent = new ArrayList<Entry>();

        yContent.add(new Entry(m1, 0));
        yContent.add(new Entry(m2, 1));
        yContent.add(new Entry(m3, 2));
        yContent.add(new Entry(m4, 3));
//        yContent.add(new Entry(m5,4));
//        yContent.add(new Entry(m6,5));
    }
}