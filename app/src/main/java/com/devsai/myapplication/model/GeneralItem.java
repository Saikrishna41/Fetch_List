package com.devsai.myapplication.model;

import com.devsai.myapplication.requests.response.DataResponse;

public class GeneralItem extends ListItem {


    private  DataResponse dataResponse = new DataResponse();

    public DataResponse getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }

    @Override
    public int getType() {
        return TYPE_LIST_ID_GENERAL;
    }
}
