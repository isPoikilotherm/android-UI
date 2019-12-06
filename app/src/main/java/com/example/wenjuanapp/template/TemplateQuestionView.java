package com.example.wenjuanapp.template;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wenjuanapp.R;
import com.example.wenjuanapp.fragment.fragment_mangselect;
import com.example.wenjuanapp.fragment.fragment_manyselect_template;
import com.example.wenjuanapp.fragment.fragment_oneselect;
import com.example.wenjuanapp.fragment.fragment_oneselect_template;
import com.example.wenjuanapp.fragment.fragment_scoring;
import com.example.wenjuanapp.fragment.fragment_scoring_template;
import com.example.wenjuanapp.fragment.fragment_shortanswer;
import com.example.wenjuanapp.fragment.fragment_shortanswer_template;

public class TemplateQuestionView extends FragmentActivity {
    private Fragment mFragment1;
    private Fragment mFragment2;
    private Fragment mFragment3;
    private Fragment mFragment4;

    private Button btn_1,btn_2,btn_3,btn_4;
    private ImageView back;
//    private String name1;
    private int project_id;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_question);

        Intent intent=getIntent();
        project_id=intent.getIntExtra("id",0);

        init();
    }

    public int  getID(){
        return project_id;
    }

    private void init(){
        back=(ImageView)findViewById(R.id.template_question_back);
        btn_1=(Button)findViewById(R.id.btn_oneselect_template);
        btn_2=(Button)findViewById(R.id.btn_manyselect_template);
        btn_3=(Button)findViewById(R.id.btn_scoring_template);
        btn_4=(Button)findViewById(R.id.btn_shortanswer_template);

        initFragment(0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemplateQuestionView.this.finish();
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment(0);

            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment(1);

            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment(2);

            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment(3);

            }
        });

    }
    private void initFragment(int i){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFrangment(transaction);
        switch (i){
            case 0:
                if (mFragment1==null){
                    mFragment1=new fragment_oneselect_template();
                    transaction.add(R.id.fragment_selected_template,mFragment1);
                }else{
                    transaction.show(mFragment1);
                }
                break;
            case 1:
                if (mFragment2==null){
                    mFragment2=new fragment_manyselect_template();
                    transaction.add(R.id.fragment_selected_template,mFragment2);
                }else{
                    transaction.show(mFragment2);
                }
                break;
            case 2:
                if (mFragment3==null){
                    mFragment3=new fragment_scoring_template();
                    transaction.add(R.id.fragment_selected_template,mFragment3);
                }else{
                    transaction.show(mFragment3);
                }
                break;
            case 3:
                if (mFragment4==null){
                    mFragment4=new fragment_shortanswer_template();
                    transaction.add(R.id.fragment_selected_template,mFragment4);
                }else{
                    transaction.show(mFragment4);
                }
                break;
        }
        transaction.commit();

    }

    private void hideFrangment(FragmentTransaction transaction){
        if (mFragment1!=null){
            transaction.hide(mFragment1);
        }
        if (mFragment2!=null){
            transaction.hide(mFragment2);
        }
        if (mFragment3!=null){
            transaction.hide(mFragment3);
        }
        if (mFragment4!=null){
            transaction.hide(mFragment4);
        }
    }


}
