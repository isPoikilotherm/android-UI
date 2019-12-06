package com.example.wenjuanapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wenjuanapp.newquestion.NewmanyselectActivity;
import com.example.wenjuanapp.newquestion.NewoneselectActivity;
import com.example.wenjuanapp.newquestion.NewscoringActivity;
import com.example.wenjuanapp.newquestion.NewshortanswerActivity;
import com.example.wenjuanapp.projectcontent.ProjectcontentnewActivity;

public class TypeActivity extends Activity {
    private ImageView back;
    private Button btn_one,btn_many,btn_scoring,btn_shortanswer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        init();
    }
    private void init() {
        back = (ImageView) findViewById(R.id.type_back);
        btn_one=(Button)findViewById(R.id.btn_type_one);
        btn_many=(Button)findViewById(R.id.btn_type_many);
        btn_scoring=(Button)findViewById(R.id.btn_type_scoring);
        btn_shortanswer=(Button)findViewById(R.id.btn_type_shortanswer);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this,
                        ProjectcontentnewActivity.class);
                startActivity(intent);
                TypeActivity.this.finish();
            }
        });
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this,
                        NewoneselectActivity.class);
                startActivity(intent);
                TypeActivity.this.finish();
            }
        });
        btn_many.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this,
                        NewmanyselectActivity.class);
                startActivity(intent);
                TypeActivity.this.finish();
            }
        });
        btn_scoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this,
                        NewscoringActivity.class);
                startActivity(intent);
                TypeActivity.this.finish();
            }
        });
        btn_shortanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this,
                        NewshortanswerActivity.class);
                startActivity(intent);
                TypeActivity.this.finish();
            }
        });
    }


}
