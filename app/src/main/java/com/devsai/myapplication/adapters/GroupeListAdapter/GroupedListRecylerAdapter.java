package com.devsai.myapplication.adapters.GroupeListAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devsai.myapplication.R;
import com.devsai.myapplication.model.GeneralItem;
import com.devsai.myapplication.model.IdItem;
import com.devsai.myapplication.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class GroupedListRecylerAdapter extends RecyclerView.Adapter {

    private List<ListItem> mConsolidatedList;
    private static final String TAG = "GroupedListRecylerAdap";
    GeneralItem generalItem;
    IdItem idItem;

    public GroupedListRecylerAdapter() {

        mConsolidatedList = new ArrayList<>();
        generalItem = new GeneralItem();
        idItem = new IdItem();

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = null;
        switch (viewType) {

            case ListItem.TYPE_LIST_ID_GENERAL:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_general_item_, parent, false);
                return new GeneralItemViewHolder(view);
            case ListItem.TYPE_LIST_ID:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_id_items, parent, false);
                return new ListItemViewHolder(view);

            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_id_items, parent, false);
                return new ListItemViewHolder(view);


        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            //for item id and item name
            case ListItem.TYPE_LIST_ID_GENERAL:

                generalItem = (GeneralItem) mConsolidatedList.get(position);
                GeneralItemViewHolder generalItemViewHolder = (GeneralItemViewHolder) holder;

                generalItemViewHolder.generalListId.setText(String.valueOf(generalItem.getDataResponse().getListId()));
                generalItemViewHolder.generalId.setText(String.valueOf(generalItem.getDataResponse().getName()));


                break;


            case ListItem.TYPE_LIST_ID:

                //for the list header by ListID
                idItem = (IdItem) mConsolidatedList.get(position);
                ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;

                listItemViewHolder.list_Id.setText(String.valueOf(idItem.getListId()));


                break;

        }


    }


    @Override
    public int getItemViewType(int position) {

        return mConsolidatedList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mConsolidatedList != null ? mConsolidatedList.size() : 0;
    }


    //set recycler view items for normal list data
    public void setListItem(ListItem items) {


        mConsolidatedList.add(items);
        notifyDataSetChanged();



    }
    //set recycler view list for search data
    public void setListSearchItem(ListItem item) {
        mConsolidatedList.clear();
        notifyDataSetChanged();
        mConsolidatedList.add(item);
        notifyDataSetChanged();
    }
    //clear the lists
    public void clearItems() {
        mConsolidatedList.clear();
        notifyDataSetChanged();
    }


}
