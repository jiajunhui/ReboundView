package com.xapp.jiajunhui.reboundviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xapp.jiajunhui.reboundview.ReboundRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2016/9/9.
 */
public class ReboundRecyclerViewActivity extends AppCompatActivity {

    ReboundRecyclerView mRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound_recycler_view);

        setTitle("ReboundRecyclerView");

        mRecycler = (ReboundRecyclerView) findViewById(R.id.recycler);

//        mRecycler.setTopReboundEnable(false);

        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        List<String> textList = new ArrayList<>();
        for(int i=0;i<20;i++){
            textList.add("text_demo : " + i);
        }
        TestAdapter adapter = new TestAdapter(textList);
        mRecycler.setAdapter(adapter);
    }


    public class TestAdapter extends RecyclerView.Adapter<ItemHolder>{

        private List<String> mList = new ArrayList<>();

        public TestAdapter(List<String> list){
            this.mList = list;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(View.inflate(getApplicationContext(),R.layout.item_layout,null));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            String text = mList.get(position);
            holder.textView.setText(text);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
