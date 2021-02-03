package com.mytelblocker.mm.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytelblocker.mm.R;
import com.mytelblocker.mm.model.DataHeader;


public class MilitaryHeaderViewHolder extends
        RecyclerView.ViewHolder {

    public TextView tvHeader;

    public MilitaryHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        tvHeader = itemView.findViewById(R.id.tv_header);
    }

    public void bindData(DataHeader dataHeader) {
        tvHeader.setText(dataHeader.getHeader());
    }
}