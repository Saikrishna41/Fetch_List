package com.devsai.myapplication.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devsai.myapplication.AppExecutors;
import com.devsai.myapplication.model.GeneralItem;
import com.devsai.myapplication.model.IdItem;
import com.devsai.myapplication.model.ListItem;
import com.devsai.myapplication.requests.response.DataResponse;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.devsai.myapplication.utils.Constants.NETWORK_TIMEOUT;

public class DataApiClientCall {

    private static MutableLiveData<List<ListItem>> mConsolData;
    private static MutableLiveData<List<ListItem>> mConsolSearchData;

    private RetrieveDataRunnable mRetrieveDataRunnable;
    private RetrieveDataSearchRunnable mRetrieveSearchDataRunnable;

    private static final String TAG = "DataApiClientCall";
    private static DataApiClientCall instance;


    private DataApiClientCall() {
        mConsolData = new MutableLiveData<>();
        mConsolSearchData = new MutableLiveData<>();


    }

    public static DataApiClientCall getInstance() {
        if (instance == null) {
            instance = new DataApiClientCall();
        }
        return instance;
    }

    public void searchData() {

        if (mRetrieveDataRunnable != null) {
            mRetrieveDataRunnable = null;
        }

        mRetrieveDataRunnable = new RetrieveDataRunnable();

        final Future handler = AppExecutors.getInstance().getmNetworkIO().submit(mRetrieveDataRunnable);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);


    }


    private class RetrieveDataRunnable implements Runnable {

        boolean isCancel;


        public RetrieveDataRunnable() {
            this.isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response<List<DataResponse>> response = searchDataApi().execute();
                List<DataResponse> mList = new LinkedList<>();
                if (response.code() == 200) {

                    Log.d(TAG, "response size: " + response.body().size());
                    for (DataResponse d : response.body()) {
                        if (d.getName() != null && !d.getName().equals("") && d.getName().length() > 0) {

                            mList.add(d);


                        }
                    }
                    Collections.sort(mList, new Comparator<DataResponse>() {
                        @Override
                        public int compare(DataResponse o1, DataResponse o2) {
                            return o1.getListId() - o2.getListId();
                        }
                    });


                } else {
                    {
                        Log.d(TAG, "run: some error occured");
                    }
                }

                groupDataIntoHashMap(mList);
                mList.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        private Call<List<DataResponse>> searchDataApi() {


            return ServiceGenerator.getDataApi().getDataApiResponse();

        }

        private void cancelRequest() {
            isCancel = true;
        }


    }

    public LiveData<List<ListItem>> getData() {

        return mConsolData;
    }

    public void cancelRequest() {
        mRetrieveDataRunnable.cancelRequest();
    }

    private HashMap<Integer, List<DataResponse>> groupDataIntoHashMap(List<DataResponse> dataResponses) {

        HashMap<Integer, List<DataResponse>> groupedHashMap = new HashMap<>();

        for (DataResponse dataResponse : dataResponses) {

            int hashMapKey = dataResponse.getListId();

            if (groupedHashMap.containsKey(hashMapKey)) {
                //the key is already in the hashmap so add the pojo object to the existing key

                groupedHashMap.get(hashMapKey).add(dataResponse);

            } else {
                //the key is not in the hashmap

                List<DataResponse> listDr = new LinkedList<>();

                listDr.add(dataResponse);
                groupedHashMap.put(hashMapKey, listDr);
            }


        }

        List<ListItem> consolidatedList = new LinkedList<>();
        for (int listId : groupedHashMap.keySet()) {
            IdItem idItem = new IdItem();
            idItem.setListId(listId);
            consolidatedList.add(idItem);
            for (DataResponse dataResponse : groupedHashMap.get(listId)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setDataResponse(dataResponse);
                consolidatedList.add(generalItem);
            }
        }

        mConsolData.postValue(consolidatedList);


        return groupedHashMap;


    }


    public void searchDataById(String query) {

        if (mRetrieveSearchDataRunnable != null) {
            mRetrieveSearchDataRunnable = null;
        }

        mRetrieveSearchDataRunnable = new RetrieveDataSearchRunnable(query);

        final Future handler = AppExecutors.getInstance().getmNetworkIO().submit(mRetrieveSearchDataRunnable);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);


    }


    private class RetrieveDataSearchRunnable implements Runnable {

        boolean isCancel;
        String query;


        public RetrieveDataSearchRunnable(String query) {
            this.isCancel = false;
            this.query = query;
        }

        @Override
        public void run() {
            try {
                Response<List<DataResponse>> response = searchDataApiById().execute();
                List<DataResponse> mList = new LinkedList<>();
                if (response.code() == 200) {

                    for (DataResponse d : response.body()) {
                        if (d.getName() != null && !d.getName().equals("") && d.getName().length() > 0) {

                            if (d.getId() == Integer.parseInt(query)) {
                                mList.add(d);
                                groupDataIntoHashMapSearchp(mList, query);
                                break;
                            } else {
                                Log.d(TAG, "run: no such value");
                            }

                        }

                    }
                    Collections.sort(mList, new Comparator<DataResponse>() {
                        @Override
                        public int compare(DataResponse o1, DataResponse o2) {
                            return o1.getListId() - o2.getListId();
                        }
                    });


                } else {
                    {
                        Log.d(TAG, "run: some error occured");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        private Call<List<DataResponse>> searchDataApiById() {


            return ServiceGenerator.getDataApi().getDataApiResponse();

        }

        private void cancelSearchRequest() {
            isCancel = true;
        }


    }

    private HashMap<Integer, List<DataResponse>> groupDataIntoHashMapSearchp(List<DataResponse> dataResponses, String query) {

        HashMap<Integer, List<DataResponse>> groupedHashMap = new HashMap<>();

        for (DataResponse dataResponse : dataResponses) {

            if (dataResponse.getId() == Integer.parseInt(query)) {
                int hashMapKey = dataResponse.getListId();

                if (groupedHashMap.containsKey(hashMapKey)) {
                    //the key is already in the hashmap so add the pojo object to the existing key

                    groupedHashMap.get(hashMapKey).add(dataResponse);

                } else {
                    //the key is not in the hashmap

                    List<DataResponse> listDr = new LinkedList<>();

                    listDr.add(dataResponse);
                    groupedHashMap.put(hashMapKey, listDr);
                }

                break;

            } else {
                mConsolSearchData.postValue(null);
                break;
            }


        }

        List<ListItem> consolidatedList = new LinkedList<>();
        for (int listId : groupedHashMap.keySet()) {
            IdItem idItem = new IdItem();
            idItem.setListId(listId);
            consolidatedList.add(idItem);
            for (DataResponse dataResponse : groupedHashMap.get(listId)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setDataResponse(dataResponse);
                consolidatedList.add(generalItem);
            }
        }

        mConsolSearchData.postValue(consolidatedList);


        return groupedHashMap;


    }


    public LiveData<List<ListItem>> getSearchData() {

        return mConsolSearchData;
    }

    public void cancelSearchRequest() {
        mRetrieveSearchDataRunnable.cancelSearchRequest();
    }


}
