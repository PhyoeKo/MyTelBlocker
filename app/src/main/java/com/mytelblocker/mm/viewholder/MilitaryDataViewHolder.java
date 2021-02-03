package com.mytelblocker.mm.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytelblocker.mm.R;
import com.mytelblocker.mm.model.MilitaryData;


public class MilitaryDataViewHolder extends
        RecyclerView.ViewHolder {

    public TextView tvEnglish, tvBurmese;
    public LinearLayout llListItemMain;

    public MilitaryDataViewHolder(@NonNull View itemView) {
        super(itemView);
        tvEnglish = itemView.findViewById(R.id.tv_english);
       // tvBurmese = itemView.findViewById(R.id.tv_burmese);
    }

    public void bindData(MilitaryData militaryData) {
        tvEnglish.setText(militaryData.getEnglishName());
       // tvBurmese.setText(militaryData.getBurmeseName());
    }
}