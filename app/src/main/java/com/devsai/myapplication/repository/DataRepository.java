package com.devsai.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.devsai.myapplication.model.ListItem;
import com.devsai.myapplication.requests.DataApiClientCall;

import java.util.List;

public class DataRepository {

    private static final String TAG = "DataRepository";
    private static DataApiClientCall mClient;

    private static DataRepository instance;

    private MediatorLiveData<List<ListItem>> mDatamed;

    //private constructor to have singleton pattern
    private DataRepository() {

        mClient = DataApiClientCall.getInstance();

    }

    //singleton pattern
    public static DataRepository getInstance() {

        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    //retrieve live data of type listitem which is 0 or 1
    public LiveData<List<ListItem>> getData() {

        return mClient.getData();
    }

    //api request
    public void searchData() {
        mClient.searchData();
    }


    //cancel the api request
    public void cancelRequest() {
        mClient.cancelRequest();
    }


    //get search live data

    public LiveData<List<ListItem>> getDataById() {

        return mClient.getSearchData();
    }

    //api request for search data
    public void searchDataById(String query) {
        mClient.searchDataById(query);
    }


    //cancel the api request
    public void cancelRequestById() {
        mClient.cancelRequest();
    }

}
