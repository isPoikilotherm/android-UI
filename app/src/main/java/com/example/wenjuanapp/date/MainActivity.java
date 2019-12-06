package com.example.wenjuanapp.date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;


import com.example.wenjuanapp.R;


/**
 * 说明：Demo
 * 作者：liuwan1992
 * 添加时间：2016/9/28
 * 修改人：liuwan1992
 * 修改时间：2018/12/21
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTvSelectedDate;
    private CustomDatePicker mDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datemain);

        findViewById(R.id.ll_date).setOnClickListener(this);
        mTvSelectedDate = findViewById(R.id.tv_selected_date);
        initDatePicker();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    private void initDatePicker() {
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
    }

}

