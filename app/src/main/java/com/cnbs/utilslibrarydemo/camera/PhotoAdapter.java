package com.cnbs.utilslibrarydemo.camera;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cnbs.utilslibrary.galleryPick.utils.ScreenUtils;
import com.cnbs.utilslibrarydemo.R;

import java.util.List;


/**
 * PhotoAdapter
 * Created by Yancy on 2016/9/29.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mData;
    private final static String TAG = "PhotoAdapter";
    private Handler handler;
    private boolean isEdit;

    public PhotoAdapter(Context context, List<String> data, Handler handler) {
        this.mContext = context;
        this.mData = data;
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (tag == mData.size()) {
                    Message message = new Message();
                    message.what = 1101;
                    handler.sendMessage(message);
                } else {
                    isEdit = false;
                    notifyDataSetChanged();
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int tag = (int) v.getTag();
                if (tag != mData.size()) {
                    isEdit = true;
                    notifyDataSetChanged();
                }
                return true;
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        // 设置 每个imageView 的大小
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = ScreenUtils.getScreenWidth(mContext) / 3;
        params.width = ScreenUtils.getScreenWidth(mContext) / 3;
        holder.itemView.setLayoutParams(params);

        holder.deleteImg.setVisibility(View.GONE);
        if (position == mData.size()) {
            Glide.with(mContext)
                    .load(R.drawable.add_img)
                    .centerCrop()
                    .into(holder.ivPhoto);
        } else {
            Glide.with(mContext)
                    .load(mData.get(position))
                    .centerCrop()
                    .into(holder.ivPhoto);
        }

        if (isEdit && position != mData.size()) {
            holder.deleteImg.setVisibility(View.VISIBLE);
        }
        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(mData.get(position));
                Message message = new Message();
                message.what = 1102;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto, deleteImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            deleteImg = (ImageView) itemView.findViewById(R.id.delete_img);
        }

    }


}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */