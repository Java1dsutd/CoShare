package com.example.chris.coshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chris.coshare.SampleData.DataModel;
import com.example.chris.coshare.SampleData.FastDataModel;
import com.example.chris.coshare.SampleData.FastSampleDataProvider;
import com.example.chris.coshare.SampleData.SampleDataProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.chris.coshare.SampleData.SampleDataProvider.dataItemList;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

public class CancelBookingPage extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();
    private int mCount = 0;

//    Button cancelButton;
    List<FastDataModel> dataItemList = FastSampleDataProvider.dataItemList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelbookingpage);

        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + mCount++);
            Log.d("WordList", mWordList.getLast());
        }
        List<String> itemNames = new ArrayList<>();

        //DataItem item = new DataItem("test","test","test","test","test");

        for (FastDataModel item: dataItemList) {
            itemNames.add(item.getLocationName());
        }

//        CancelBookingAdapter adapter = new CancelBookingAdapter(this, mWordList);
        recyclerView =  findViewById(R.id.cancelRV);
        ItemAdapter<FastDataModel> itemAdapter = new ItemAdapter<>();       //
        FastAdapter fastAdapter = FastAdapter.with(itemAdapter);            //bp
        recyclerView.setAdapter(fastAdapter);                               //bp
        itemAdapter.add(dataItemList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        cancelButton = (Button) findViewById(R.id.button2);
    }

}
