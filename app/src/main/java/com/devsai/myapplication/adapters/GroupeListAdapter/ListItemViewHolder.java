package com.devsai.myapplication.adapters.GroupeListAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devsai.myapplication.R;

public class ListItemViewHolder extends RecyclerView.ViewHolder {

    TextView list_Id;

    public ListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.list_Id = itemView.findViewById(R.id.single_listId);
    }
}
