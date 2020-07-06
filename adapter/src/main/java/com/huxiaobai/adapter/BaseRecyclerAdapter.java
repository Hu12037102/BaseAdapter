package com.huxiaobai.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 项  目 :  BaseRecyclerAdapter
 * 包  名 :  com.huxiaobai.adapter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/1/2
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {
    protected List<T> mData;
    private boolean mIsHasNet = true;
    private static final int TYPE_NOT_NET = 100;
    private static final int TYPE_NOT_DATA = 101;
    private static final int TYPE_HEAD_VIEW = 102;
    private static final int TYPE_FOOT_VIEW = 103;
    private static final int TYPE_NOT_MORE = 104;
    private int mNotNetViewRes = R.mipmap.ic_error;
    private int mNotDataViewRes = R.mipmap.ic_not_data;
    private int mNotNetContentRes = R.string.not_net_error;
    private int mNotDataContentRes = R.string.not_data;
    private View mHeadView;
    private View mFootView;
    protected boolean isHaveHeadView;
    protected boolean isHaveFootView;
    private int mAllDataCount;
    private Context mContext;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected OnItemClickListener onItemClickListener;

    public void setNotNetView(@DrawableRes int notNetViewRes) {
        this.mNotNetViewRes = notNetViewRes;
    }

    public void setNotDataView(@DrawableRes int notDataViewRes) {
        this.mNotDataViewRes = notDataViewRes;
    }

    public void setNotNetContentRes(@StringRes int notNetContentRes) {
        this.mNotNetContentRes = notNetContentRes;
    }

    public void setNotDataContentRes(@StringRes int notDataContentRes) {
        this.mNotDataContentRes = notDataContentRes;
    }

    public void addHeadView(@NonNull View headView) {
        this.mHeadView = headView;
        isHaveHeadView = true;
    }

    public void addFootView(@NonNull View footView) {
        this.mFootView = footView;
        isHaveFootView = true;
    }

    public void removeHeadView() {
        if (isHaveHeadView) {
            isHaveHeadView = false;
        }
    }

    public void removeFootView() {
        if (isHaveFootView) {
            isHaveFootView = false;
        }
    }


    public BaseRecyclerAdapter(@NonNull Context context, @NonNull List<T> data) {
        this.mContext = context;
        this.mData = data;
    }


    public void notifyData(boolean isHasNet) {
        this.mIsHasNet = isHasNet;
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isHaveHeadView) {
            return TYPE_HEAD_VIEW;
        } else if (position == mAllDataCount - 1 && isHaveFootView) {
            return TYPE_FOOT_VIEW;
        } else if (mData.size() == 0) {
            return NetWorkUtils.isNetCanUse(mContext) ? TYPE_NOT_DATA : TYPE_NOT_NET;
        }
        return super.getItemViewType(position);

    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        switch (i) {
            case TYPE_NOT_NET:
                viewHolder = new NotNetViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_not_net_view, viewGroup, false));
                break;
            case TYPE_NOT_DATA:
                viewHolder = new NotDataViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_not_data_view, viewGroup, false));
                break;
            case TYPE_HEAD_VIEW:
                viewHolder = new RecyclerView.ViewHolder(mHeadView) {
                };
                break;
            case TYPE_FOOT_VIEW:
                viewHolder = new RecyclerView.ViewHolder(mFootView) {
                };
                break;
            default:
                viewHolder = onCreateDataViewHolder(viewGroup, i);
                break;
        }
        return (VH) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int i) {
        if (viewHolder instanceof NotNetViewHolder) {
            NotNetViewHolder notNetHolder = (NotNetViewHolder) viewHolder;
            notNetHolder.mIvNotNet.setImageResource(mNotNetViewRes);
            notNetHolder.mTvNotNet.setText(mNotNetContentRes);
            notNetHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onNotNetClick(view);
                    }
                }
            });

        } else if (viewHolder instanceof NotDataViewHolder) {
            NotDataViewHolder notDataHolder = (NotDataViewHolder) viewHolder;
            notDataHolder.mIvNotData.setImageResource(mNotDataViewRes);
            notDataHolder.mTvNotData.setText(mNotDataContentRes);
            notDataHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onNotDataClick(view);
                    }
                }
            });
        } else if (getItemViewType(i) == 0) {
            if (isHaveHeadView) {
                i--;
            }
            onBindViewDataHolder(viewHolder, i);
        }
    }

    protected abstract void onBindViewDataHolder(@NonNull VH viewHolder, int i);

    protected abstract VH onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public int getItemCount() {
        mAllDataCount = mData == null ? 0 : mData.size();
        if (mData == null || mData.size() == 0) {
            mAllDataCount = mAllDataCount + 1;

        }
        if (isHaveHeadView) {
            mAllDataCount += 1;
        }
        if (isHaveFootView) {
            mAllDataCount += 1;
        }
        return mAllDataCount;
    }

    static class NotDataViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvNotData;
        private TextView mTvNotData;

        NotDataViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIvNotData = itemView.findViewById(R.id.iv_not_data);
            mTvNotData = itemView.findViewById(R.id.tv_not_data);

        }
    }

    static class NotNetViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvNotNet;
        private TextView mTvNotNet;

        NotNetViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIvNotNet = itemView.findViewById(R.id.iv_not_net);
            mTvNotNet = itemView.findViewById(R.id.tv_not_net);
        }
    }

    static class NotMoreViewHolder extends RecyclerView.ViewHolder {
        public NotMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onNotNetClick(View view);

        void onNotDataClick(View view);

        void onItemClick(View view, int position);
    }
}
