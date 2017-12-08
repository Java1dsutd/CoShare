package com.example.chris.coshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.chris.coshare.SampleData.DataModel;
import com.example.chris.coshare.SampleData.SampleDataProvider;

import java.util.List;

public class ViewBookingPage extends AppCompatActivity {

    //TODO: Sameple data only. Replace with backend.
    List<DataModel> dataItemList = SampleDataProvider.dataItemList;
    RecyclerView myrecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbookingpage);

        AddBookingAdapter adapter = new AddBookingAdapter(this, dataItemList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tablesRV);
        recyclerView.setAdapter(adapter);
    }
}
