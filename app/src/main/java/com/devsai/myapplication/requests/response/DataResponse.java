package com.devsai.myapplication.requests.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse {

    @SerializedName("id")
    @Expose()
    private int id;
    @SerializedName("listId")
    @Expose()
    private int listId;
    @SerializedName("name")
    @Expose
    private String name;


    public int getId() {
        return this.id;
    }

    public int getListId() {
        return this.listId;
    }


    public String getName() {
        return this.name;
    }
}
