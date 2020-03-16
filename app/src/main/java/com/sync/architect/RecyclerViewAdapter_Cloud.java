package com.sync.architect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter_Cloud extends RecyclerView.Adapter<RecyclerViewAdapter_Cloud.MyViewHolder> {

    private Context mContext;
    private List<Floorplan> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecyclerViewAdapter_Cloud(Context mContext, List<Floorplan> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item,parent,false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.floorplan_name.setText(mData.get(position).getName());
        //holder.floorplan_image.setImageDrawable(mData.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView floorplan_name;
        ImageView floorplan_button;
        //ImageView floorplan_image;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            floorplan_name = itemView.findViewById(R.id.floorplan_name_id);
            floorplan_button = itemView.findViewById(R.id.floorplan_button_id);
            //floorplan_image = itemView.findViewById(R.id.floorplan_image_id);

            floorplan_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

}
