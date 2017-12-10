package com.example.chris.coshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.coshare.SampleData.FastDataModel;
import com.example.chris.coshare.SampleData.FastSampleDataProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddBookingPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Declare widgets
    TextView locationText;
    TextView timeText;
    Spinner locationSpinner;
    Spinner tableIdSpinner;
    Spinner timeSpinner;
    Button addBookingButton;
//    ImageView addBookingImage;

    // Declare this form's variables
    String selectedLocation;
    String selectedTime;
    String selectedTable;

    String spinnerName;
    int spinnerPos;
    List<FastDataModel> dataItemList = FastSampleDataProvider.dataItemList;     //For testing
    List<String> itemNames = new ArrayList<>();

    //Firebase backend
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefLocations;
    HashMap<ArrayList<String>,Boolean> tabledata;
    backend be;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbookingpage);

        //Firebase
        database = FirebaseDatabase.getInstance();
        DBrefUsers = database.getReference().child("Locations");
        DBrefLocations = database.getReference().child("Users");
        tabledata=new HashMap<>();
        be= new backend();

        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tabledata=be.getEntireTable(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // For testing
        for (FastDataModel item: dataItemList) {
            itemNames.add(item.getLocationName());
        }
         if (getIntent().getExtras() != null) {
             spinnerName = getIntent().getExtras().getString(AddBookingAdapter.ITEM_ID_KEY);     // for getting intent extras
         } else {
             spinnerName = null;
         }

        // Bind views with viewIDs
        locationText = (TextView) findViewById(R.id.textView5);
        timeText = (TextView) findViewById(R.id.timeText);
        locationSpinner = (Spinner) findViewById(R.id.spinner);
        timeSpinner = (Spinner) findViewById(R.id.spinnerTime);
        tableIdSpinner = (Spinner) findViewById(R.id.spinnerTable);
        addBookingButton = (Button) findViewById(R.id.AddBookingButton);

        // Instantiate lists for spinners
        final List<String> locationNameList = new ArrayList<String>();
        final List<String> timeList = new ArrayList<String>();
        final List<String> tableIdList = new ArrayList<String>();

        // Setting adapters and selectionListeners
        //TODO: Replace "R.array.DataSample" with your List
//        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.DataSample, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,itemNames);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);                 //boilerplate
        locationSpinner.setAdapter(locationAdapter);                                                                    //boilerplate
        spinnerPos = locationAdapter.getPosition(spinnerName);
        locationSpinner.setOnItemSelectedListener(this);
        locationSpinner.setSelection(spinnerPos);

        //TODO: Replace "R.array.DataSample" with your List
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.DataSample, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setOnItemSelectedListener(this);

        //TODO: Replace "R.array.DataSample" with your List
        ArrayAdapter<CharSequence> tableIdAdapter = ArrayAdapter.createFromResource(this, R.array.DataSample, android.R.layout.simple_spinner_item);
        tableIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tableIdSpinner.setAdapter(tableIdAdapter);
        tableIdSpinner.setOnItemSelectedListener(this);

        //TODO: Get address from Firebase. Set address textView.
//        locationText.setText("");

        //TODO: Get number of tables from Firebase. Set into textView.
//        timeText.setText("");

    } //end of onCreate()

    public void onAddBooking(View view) {
        //TODO: Update firebase database. Get AddBooking page's data from selectedTable, selectedLocation, selectedTime.

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()){
            case R.id.spinner:
                selectedLocation = parent.getItemAtPosition(i).toString();
                Toast.makeText(this, "Location selected: " + parent.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinnerTime:
                selectedTime = parent.getSelectedItem().toString();
                Toast.makeText(this, "Time Selected: " + parent.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinnerTable:
                selectedTable = parent.getSelectedItem().toString();
                Toast.makeText(this, "Table ID Selected: " + parent.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Not used. Required interface methods
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
