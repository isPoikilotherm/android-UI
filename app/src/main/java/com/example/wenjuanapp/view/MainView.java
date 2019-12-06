package com.example.wenjuanapp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenjuanapp.LooperPagerAdapter;
import com.example.wenjuanapp.MyViewPager;
import com.example.wenjuanapp.R;
import com.example.wenjuanapp.template.TemplateContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 程序主入口
 */
public class MainView extends Activity implements MyViewPager.OnViewPagerTouchListener, ViewPager.OnPageChangeListener {//implements ViewPager.OnPageChangeListener

    private MyViewPager mloopPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private ImageButton btn2,btn3;
    private MyInfoView myInfoView;
    private LooperPagerAdapter mLooperPagerAdapter;
    private static List<Integer> sPics = new ArrayList<>();
    static {
        sPics.add(R.drawable.a);
        sPics.add(R.drawable.b);
        sPics.add(R.drawable.e);
        sPics.add(R.drawable.c);
        sPics.add(R.drawable.d);
    }

    private Handler mHandler;
    private boolean mIsTouch=false;
    private LinearLayout mPoints_container;
    private ImageView main2,main4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Random random=new Random();

        //准备数据
       /* for (int i=0;i<5;i++){
            sColos.add(Color.argb(random.nextInt(255),
                    random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        }*/
        //给适配器设置数据
        //mLooperPagerAdapter.setData(sColos);
        //
       // mLooperPagerAdapter.notifyDataSetChanged();
        mHandler = new Handler();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;//不执行父类点击事件
        }
        return false;//继续执行父类其他点击事件
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.post(mLooperTask);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mLooperTask);
    }

    private Runnable mLooperTask=new Runnable() {
        @Override
        public void run() {
            if (!mIsTouch) {


                //切换图片到下一个

                int currentItem = mloopPager.getCurrentItem();
                mloopPager.setCurrentItem(++currentItem, false);

            }
            mHandler.postDelayed(this, 3000);
        }
    };

    private void initView  (){

        main2=(ImageView)findViewById(R.id.main2);
        main4=(ImageView)findViewById(R.id.main4);

        // 找到控件
        mloopPager=(MyViewPager)this.findViewById(R.id.looper_pager);
        //设置适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
        mLooperPagerAdapter.setData(sPics);
        mloopPager.setAdapter(mLooperPagerAdapter);
        mloopPager.addOnPageChangeListener(this);
        mloopPager.setOnViewPagerTouchListener(this);
        mPoints_container = this.findViewById(R.id.points_container);
        //根据图片的个数，去添加点的个数
        inserPoint();
        mloopPager.setCurrentItem(mLooperPagerAdapter.getDataRealSize()*100,false);

        btn2= (ImageButton) findViewById(R.id.main_project_btn);
        btn3= (ImageButton) findViewById(R.id.main_me_btn);

        btn2.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainView.this,
                        ProjectView.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainView.this,
                        MyInfoView.class);
                startActivity(intent);
            }
        });
        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this,
                        TemplateContent.class);
                int id=3;
                intent.putExtra("id",id);
                intent.putExtra("name","大学生心理健康调查问卷");
                intent.putExtra("direction","非常感谢您能抽出时间做这份问卷");
                startActivity(intent);
            }
        });
        main4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this,
                        TemplateContent.class);
                int id=4;
                intent.putExtra("id",id);
                intent.putExtra("name","图书馆调查问卷");
                intent.putExtra("direction","我们希望通过此调查问卷，了解您对校图书馆资料的需求，提升我校图书馆资源建设的质量和水平，谢谢您的协助！  ");
                startActivity(intent);
            }
        });
    }

    private void inserPoint() {
        for (int i = 0; i < sPics.size(); i++) {
            View point=new View(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(40,40);
            layoutParams.leftMargin=20;
            point.setBackground(getResources().getDrawable(R.drawable.shape_point_normal)) ;
            point.setLayoutParams(layoutParams);
            mPoints_container.addView(point);


        }
    }

    @Override
    public void onPagerTouch(boolean isTouch) {
        mIsTouch=isTouch;

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        int realPosition;
        if (mLooperPagerAdapter.getDataRealSize()!=0) {
            realPosition=position%mLooperPagerAdapter.getDataRealSize();
        }else{
            realPosition=0;
        }
        setSelectPoint(realPosition);
    }

    private void setSelectPoint(int realPosition) {
        for (int i = 0; i < mPoints_container.getChildCount(); i++) {
            View point=mPoints_container.getChildAt(i);
            if (i!=realPosition){
                point.setBackgroundResource(R.drawable.shape_point_normal);

            }else {
                point.setBackgroundResource(R.drawable.shape_point_selected);


            }
            
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
