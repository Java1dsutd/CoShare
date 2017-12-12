package com.example.chris.coshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.chris.coshare.SampleData.BackEndFactory;
import com.example.chris.coshare.SampleData.BackendLocations;
import com.example.chris.coshare.SampleData.BackendUser;
import com.example.chris.coshare.SampleData.DataModel;
//import com.example.chris.coshare.SampleData.FastDataModel;
//import com.example.chris.coshare.SampleData.FastSampleDataProvider;
//import com.example.chris.coshare.SampleData.SampleDataProvider;
import com.example.chris.coshare.SampleData.FastDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingPage extends AppCompatActivity {

//    FirebaseDatabase database;
//    DatabaseReference DBref;
    DatabaseReference DBrefLocations;
    BackendLocations be;
    BackEndFactory bef;

    List<FastDataModel> dataItemList;
    List<String> locationData;
//    ViewBookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbookingpage);

        dataItemList = new ArrayList<>();
        locationData = new ArrayList<>();
        final ItemAdapter<FastDataModel> itemAdapter;

//        database = FirebaseDatabase.getInstance();
//        DBref = database.getReference();
//        DBrefLocations = database.getReference().child("Locations");
        bef = new BackEndFactory();
        be = (BackendLocations) bef.getBackend("locations");
        DBrefLocations = be.initialise();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.viewRV);
        itemAdapter = new ItemAdapter<>();
        FastAdapter fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setAdapter(fastAdapter);

        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                locationData = be.getDbLocation(dataSnapshot);
                for(String str:locationData){
                    dataItemList.add(new FastDataModel(str,""+str+".png"));
//                    Toast.makeText(getApplicationContext(),""+str,Toast.LENGTH_SHORT).show();
                }
                itemAdapter.add(dataItemList);
//                adapter = new ViewBookingAdapter(getApplicationContext(), dataItemList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ultimate_recycler_view);
//        Toast.makeText(this,""+locationData,Toast.LENGTH_SHORT).show();
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.viewRV);
//        recyclerView.setAdapter(adapter);

    }
}
