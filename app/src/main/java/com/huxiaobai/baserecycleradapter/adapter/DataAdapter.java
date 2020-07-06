package com.huxiaobai.baserecycleradapter.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.huxiaobai.baserecycleradapter.R;

import java.util.List;

/**
 * 项  目 :  BaseRecyclerAdapter
 * 包  名 :  com.huxiaobai.baserecycleradapter.adapter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/1/2
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public class DataAdapter extends BaseRecyclerAdapter<DataAdapter.ViewHolder, String> {

    public DataAdapter(@NonNull Context context, @NonNull List<String> data) {
        super(context, data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mTvData.setText(mData.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, i);
                }
            }
        });
    }


    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvData = itemView.findViewById(R.id.tv_data);
        }
    }

}
