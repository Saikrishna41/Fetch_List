package com.devsai.myapplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

//class that has the thread pool executor to run tasks in queue
public class AppExecutors {
    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    //scheduled thread pool to run threads in queue
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getmNetworkIO() {

        return mNetworkIO;

    }


}
