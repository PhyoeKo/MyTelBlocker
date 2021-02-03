package com.mytelblocker.mm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytelblocker.mm.R;
import com.mytelblocker.mm.constant.AppConstants;
import com.mytelblocker.mm.model.DataHeader;
import com.mytelblocker.mm.model.MilitaryData;
import com.mytelblocker.mm.viewholder.MilitaryDataViewHolder;
import com.mytelblocker.mm.viewholder.MilitaryHeaderViewHolder;

import java.util.ArrayList;

public class MilitaryDataListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> fuckingMilitaryDataList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case AppConstants.TYPE_ITEM:
                View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row, parent, false);
                viewHolder = new MilitaryDataViewHolder(chatView);
                break;
            case AppConstants.TYPE_HEADER:
                View videoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header, parent, false);
                viewHolder = new MilitaryHeaderViewHolder(videoView);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case AppConstants.TYPE_HEADER:
                DataHeader headerData = (DataHeader) fuckingMilitaryDataList.get(position);
                ((MilitaryHeaderViewHolder) holder).bindData(headerData);
                break;
            case AppConstants.TYPE_ITEM:
                MilitaryData militaryData = (MilitaryData) fuckingMilitaryDataList.get(position);
                ((MilitaryDataViewHolder) holder).bindData(militaryData);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return fuckingMilitaryDataList.size();
    }

    public int getItemViewType(int position) {
        if (fuckingMilitaryDataList.get(position) instanceof MilitaryData) {
            return AppConstants.TYPE_ITEM;
        } else if (fuckingMilitaryDataList.get(position) instanceof DataHeader) {
            return AppConstants.TYPE_HEADER;
        }
        return -1;
    }

    public void setData(ArrayList<Object> fuckingMilitaryDataList) {
        this.fuckingMilitaryDataList.addAll(fuckingMilitaryDataList);
    }

}
