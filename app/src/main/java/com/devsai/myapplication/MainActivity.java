package com.devsai.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;
import com.devsai.myapplication.adapters.GroupeListAdapter.GroupedListRecylerAdapter;
import com.devsai.myapplication.model.ListItem;
import com.devsai.myapplication.viewmodel.DataViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ui components
    private RecyclerView mRecyclerView;
    private GroupedListRecylerAdapter mAdapters;
    private Toolbar mToolbar;
    private SearchView mSearchView;



    //variables
    private static final String TAG = "MainActivity";
    private DataViewModel mDataViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        mRecyclerView = findViewById(R.id.recyclerView);
        mToolbar = findViewById(R.id.toolbar);
        mSearchView = findViewById(R.id.search);
        setSupportActionBar(mToolbar);
        setTitle("Fetch Items");
        onObserversSubscribed();
        initRecyclerView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initSearch();

    }

    //observe changes
    private void onObserversSubscribed() {

        mDataViewModel.getData().observe(this, new Observer<List<ListItem>>() {


            @Override
            public void onChanged(List<ListItem> listItems) {
                if (listItems != null) {
                    for (ListItem item : listItems) {
                        if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                            mAdapters.setListItem(item);

                        }
                    }
                }


            }
        });


    }

    //api request
    private void initData() {

        if (mDataViewModel.getQuerying()) {
            mDataViewModel.setQuerying(false);
        }

        mDataViewModel.searchData();


    }


    //set recycler view
    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapters = new GroupedListRecylerAdapter();


        mRecyclerView.setAdapter(mAdapters);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)) {

                    mDataViewModel.cancelRequest();
                }
            }
        });


    }

    //searchview
    private void initSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapters.clearItems();
                mDataViewModel.searchDataById(query);
                mDataViewModel.setQuerying(true);
                onsearchSubscribed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //search data observers
    private void onsearchSubscribed() {

        mDataViewModel.getDataByID().observe(this, new Observer<List<ListItem>>() {

            @Override
            public void onChanged(List<ListItem> listItems) {

                if (listItems != null) {
                    for (ListItem item : listItems) {


                        mAdapters.setListSearchItem(item);


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Id found", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (mDataViewModel.onBackPressed()) {
            super.onBackPressed();
            mAdapters.clearItems();

        } else {
            mAdapters.clearItems();
            initData();
        }
    }
}
