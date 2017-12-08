package com.example.chris.coshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chris.coshare.SampleData.DataModel;
import com.example.chris.coshare.SampleData.SampleDataProvider;

import java.util.LinkedList;
import java.util.List;

import static com.example.chris.coshare.SampleData.SampleDataProvider.dataItemList;

public class CancelBookingPage extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();
    private int mCount = 0;

//    Button cancelButton;
    List<DataModel> dataItemList = SampleDataProvider.dataItemList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelbookingpage);

        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + mCount++);
            Log.d("WordList", mWordList.getLast());
        }

        CancelBookingAdapter adapter = new CancelBookingAdapter(this, dataItemList);
        recyclerView =  findViewById(R.id.cancelRV);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        cancelButton = (Button) findViewById(R.id.button2);
    }

}
