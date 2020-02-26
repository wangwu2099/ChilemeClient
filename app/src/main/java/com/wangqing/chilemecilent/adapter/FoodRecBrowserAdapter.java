package com.wangqing.chilemecilent.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wangqing.chilemecilent.R;
import com.wangqing.chilemecilent.object.dto.FoodRecBrowserDto;

import java.util.List;

public class FoodRecBrowserAdapter extends RecyclerView.Adapter<FoodRecBrowserAdapter.FoodRecBrowserViewHolder> {

    private List<FoodRecBrowserDto> foodRecList;

    public void setFoodRecList(List<FoodRecBrowserDto> foodRecList) {
        this.foodRecList = foodRecList;
    }

    @NonNull
    @Override
    public FoodRecBrowserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.food_rec_browser_holder, parent, false);
        return new FoodRecBrowserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRecBrowserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return foodRecList.size();
    }



    static class FoodRecBrowserViewHolder extends RecyclerView.ViewHolder{
        ImageView userAvatar;
        TextView userName;
        TextView postTime;
        TextView postHeadline;
        ImageView postImage;
        ImageView commentImage;
        ImageView likeImage;
        TextView likeNumber;
        TextView commentNumber;
        public FoodRecBrowserViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            postTime = itemView.findViewById(R.id.postTime);
            postHeadline = itemView.findViewById(R.id.postHeadline);
            postImage = itemView.findViewById(R.id.postImage);
            commentImage = itemView.findViewById(R.id.commentImage);
            likeImage = itemView.findViewById(R.id.likeImage);
            likeNumber = itemView.findViewById(R.id.likeNumber);
            commentNumber = itemView.findViewById(R.id.commentNumber);

        }
    }
}