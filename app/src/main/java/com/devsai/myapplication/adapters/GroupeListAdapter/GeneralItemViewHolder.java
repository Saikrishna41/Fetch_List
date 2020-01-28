package com.devsai.myapplication.adapters.GroupeListAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devsai.myapplication.R;

public class GeneralItemViewHolder extends RecyclerView.ViewHolder {

    TextView generalListId;
    TextView generalId;

    public GeneralItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.generalListId = itemView.findViewById(R.id.general_listId);
        this.generalId = itemView.findViewById(R.id.general_Id);
    }


}
