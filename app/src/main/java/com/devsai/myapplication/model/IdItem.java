package com.devsai.myapplication.model;

public class IdItem extends ListItem {

    private int listId;

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }


    @Override
    public int getType() {
        return TYPE_LIST_ID;
    }
}
