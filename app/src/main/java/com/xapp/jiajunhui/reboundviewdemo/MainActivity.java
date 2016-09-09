package com.xapp.jiajunhui.reboundviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_rebound_relative_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReboundRelativeLayoutActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.tv_rebound_scroll_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReboundScrollViewActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.tv_rebound_recycler_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReboundRecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
