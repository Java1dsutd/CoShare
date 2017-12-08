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

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbookingpage);
//        String itemId = getIntent().getExtras().getString(AddBookingAdapter.ITEM_ID_KEY);     // for getting intent extras

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
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.DataSample, android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);                 //boilerplate
        locationSpinner.setAdapter(locationAdapter);                                                                    //boilerplate
        locationSpinner.setOnItemSelectedListener(this);

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
