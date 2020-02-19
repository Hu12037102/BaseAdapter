package com.huxiaobai.baserecycleradapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.huxiaobai.baserecycleradapter.adapter.DataAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvData;
    private DataAdapter mDataAdapter;
    private List<String> mData;
    private SmartRefreshLayout mSrlRefresh;
    private int mLimit = 1;
    private int mLastIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mRvData = findViewById(R.id.rv_data);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mSrlRefresh = findViewById(R.id.srl_refresh);

    }

    private void initData() {
        mData = new ArrayList<>();
        loadData(mLimit);

        //dataAdapter.notifyData(false,data,false);
    }

    private void loadData(int limit) {
        for (int i = mLastIndex; i <= limit * 20; i++) {
            mData.add("Mark:".concat(String.valueOf(i)));
            if (i == limit * 20) {
                mLastIndex = limit * 20 + 1;
            }
        }
        if (mDataAdapter == null) {
            mDataAdapter = new DataAdapter(mData);
            mDataAdapter.addHeadView(LayoutInflater.from(this).inflate(R.layout.item_head_view, null));
            mDataAdapter.addFootView(LayoutInflater.from(this).inflate(R.layout.item_data_view, null));
            mDataAdapter.addNotMoreView(LayoutInflater.from(this).inflate(R.layout.item_not_more, mRvData,false));
            mRvData.setAdapter(mDataAdapter);
        } else {
            mDataAdapter.notifyData(NetUtils.hasNetInfo(MainActivity.this));
        }
    }

    private void initEvent() {
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  mData.clear();
               // mDataAdapter.removeFootView();
                mDataAdapter.notifyData(NetUtils.hasNetInfo(MainActivity.this));
            }
        });
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();

                mLimit++;
                loadData(mLimit);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                mData.clear();
                mLastIndex = mLimit = 1;
                loadData(mLimit);
            }
        });
        mDataAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {
                mLastIndex = mLimit = 1;
                loadData(mLimit);
            }

            @Override
            public void onNotDataClick(View view) {
                mLastIndex = mLimit = 1;
                loadData(mLimit);
            }

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "我是：" + mData.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
