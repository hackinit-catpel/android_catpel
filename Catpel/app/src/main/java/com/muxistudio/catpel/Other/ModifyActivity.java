package com.muxistudio.catpel.Other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.muxistudio.catpel.R;

/**
 * Created by kolibreath on 17-7-8.
 */

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.imageView3:
                //learning
                Intent intent = new Intent(ModifyActivity.this,SelectBannedAppsActivity.class);
                intent.putExtra("intent","1");
                startActivity(intent);
                break;
            case R.id.imageView6:
                //fitting
                Intent intent1 = new Intent(ModifyActivity.this,SelectBannedAppsActivity.class);
                intent1.putExtra("intent","2");
                startActivity(intent1);
                break;
            case R.id.imageView5:
                //others
                Intent intent2 = new Intent(ModifyActivity.this,SelectBannedAppsActivity.class);
                intent2.putExtra("intent","4");
                startActivity(intent2);
                break;
            case R.id.taking_class:
                Intent intent3 = new Intent(ModifyActivity.this,SelectBannedAppsActivity.class);
                intent3.putExtra("intent","3");
                startActivity(intent3);
                break;
        }
    }

    private Button learning,fitting,takingClass,other;

    private void initView(){
        learning = (Button) findViewById(R.id.imageView3);
        fitting  = (Button) findViewById(R.id.imageView6);
        takingClass = (Button) findViewById(R.id.taking_class);
        other = (Button) findViewById(R.id.imageView5);

        learning.setOnClickListener(this);
        fitting.setOnClickListener(this);
        takingClass.setOnClickListener(this);
        other.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        initView();
    }
}
