package com.devsai.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devsai.myapplication.model.ListItem;
import com.devsai.myapplication.repository.DataRepository;

import java.util.List;

public class DataViewModel extends ViewModel {

    //to get data from repository to view model
    private DataRepository mDataRepo;

    //to keep track of search mode
    private Boolean isQuerying;

    public Boolean getQuerying() {
        return isQuerying;
    }

    public void setQuerying(Boolean querying) {
        isQuerying = querying;
    }

    public DataViewModel() {

        mDataRepo = DataRepository.getInstance();
        isQuerying = false;


    }

    //for receiving  live data for the data request
    public LiveData<List<ListItem>> getData() {

        return mDataRepo.getData();
    }

    //search data request
    public void searchData() {
        mDataRepo.searchData();
    }


    public void cancelRequest() {
        mDataRepo.cancelRequest();
    }

    //for getting searchview  live data

    public LiveData<List<ListItem>> getDataByID() {

        return mDataRepo.getDataById();
    }

    //search data using search view
    public void searchDataById(String query) {
        mDataRepo.searchDataById(query);
    }


    public void cancelRequestById() {
        mDataRepo.cancelRequestById();
    }

    //disabling search mode when back key is pressed
    public Boolean onBackPressed() {
        if(isQuerying) {
            isQuerying = false;
            return false;
        }
        return true;
    }


}
