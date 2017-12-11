package com.example.chris.coshare;

import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.chris.coshare.SampleData.FastDataModel;
import com.example.chris.coshare.SampleData.FastSampleDataProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddBookingPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Declare widgets
    TextView locationText;
    TextView noOfTableText;
    Spinner locationSpinner;
    Spinner tableSpinner;
    Button dateButton;
    Button addBookingButton;
    ImageView addBookingImage;
    AlertDialog alertDialog;

    // Declare this form's variables
    String selectedLocation;
    String selectedTable;
    String imageFile;
    String addrFile;

    String spinnerName;
    int spinnerPos;
    List<FastDataModel> dataItemList = FastSampleDataProvider.dataItemList;     //For testing
    List<String> itemNames = new ArrayList<>();

    //Firebase backend
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefLocations;
    DatabaseReference DBref;
    HashMap<ArrayList<String>,Boolean> tabledata = new HashMap<>();
    List<String> dataDbLocation;
    List<String> dataDbTable;
    backend be;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbookingpage);

        //Firebase
        database = FirebaseDatabase.getInstance();
        DBref = database.getReference();
        DBrefUsers = database.getReference().child("Users");
        DBrefLocations = database.getReference().child("Locations");
//        tabledata=new HashMap<>();
        be= new backend();
        dataDbTable = new ArrayList<>();

        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tabledata=be.getEntireTable(dataSnapshot);
//                Toast.makeText(getApplicationContext(), "" + tabledata, Toast.LENGTH_SHORT).show();
                for(ArrayList<String> arr:tabledata.keySet()){
                    if (tabledata.get(arr)!=null) {         //prevent errors
                        if (tabledata.get(arr)) {
                            dataDbTable.add(arr.get(1));
                        }
                    }
                }
                dataDbLocation = be.getDbLocation(dataSnapshot);        //Get Locations only
                //dataDbTable = be.getDbTable(dataSnapshot);

                ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dataDbLocation);
                locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);                 //boilerplate
                locationSpinner.setAdapter(locationAdapter);                                                                    //boilerplate
                locationSpinner.setOnItemSelectedListener(AddBookingPage.this);

                ArrayAdapter<String> tableAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dataDbTable);
                tableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tableSpinner.setAdapter(tableAdapter);
                tableSpinner.setOnItemSelectedListener(AddBookingPage.this);

                noOfTableText.setText(Integer.toString(dataDbTable.size()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Bind views with viewIDs
        locationText = (TextView) findViewById(R.id.textView5);
        locationSpinner = (Spinner) findViewById(R.id.spinner);
        noOfTableText = (TextView) findViewById(R.id.noOfTableText);
        tableSpinner = (Spinner) findViewById(R.id.spinnerTable);
        addBookingButton = (Button) findViewById(R.id.AddBookingButton);
        dateButton = (Button) findViewById(R.id.datePicker);
        addBookingImage = (ImageView) findViewById(R.id.imageView);

        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(this);
        myAlertBuilder.setTitle("Test");
        myAlertBuilder.setMessage("Test message");
        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT);
            }
        });
        alertDialog = myAlertBuilder.create();
    } //end of onCreate()

    // Submit button callback
    public void onAddBooking(View view) {
        DBref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (be.Book(dataSnapshot,selectedLocation,selectedTable,"83423995")) {
                    //TODO: Implement booking with date (see also line 214)
                    Toast.makeText(getApplicationContext(), "Booking success: " , Toast.LENGTH_SHORT).show();
                    alertDialog.show();
                };
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Spinner selection callback
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        // Locate spinner
        switch (parent.getId()){
            // Extract value from location spinner
            case R.id.spinner:
                selectedLocation = parent.getItemAtPosition(i).toString();
                Toast.makeText(this, "Location selected: " + parent.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                switch (selectedLocation) {
                    case "Bugis":
                        imageFile = "bgs.png";
                        locationText.setText(R.string.Address_Bugis);
                        break;
                    case "Orchard":
                        imageFile = "stc.png";
                        locationText.setText(R.string.Address_Orchard);
                        break;
                    case "Tampines":
                        imageFile = "tmp.png";
                        locationText.setText(R.string.Address_Tampines);
                        break;
                    case "Bishan":
                        imageFile = "bsh.png";
                        locationText.setText(R.string.Address_Bishan);
                        break;
                    case "Ang Mo Kio":
                        imageFile = "amk.png";
                        locationText.setText(R.string.FullAddress);
                    case "Rochor":
                        imageFile = "rcr.png";
                        locationText.setText(R.string.FullAddress);
                    default:
                        imageFile = "tmp.png";
                        locationText.setText(R.string.Address_Tampines);
                        break;
                }
                try {
                    InputStream inputStream = view.getContext().getAssets().open(imageFile);
                    Drawable d = Drawable.createFromStream(inputStream, null);
                    addBookingImage.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            // Extract value from table spinner
            case R.id.spinnerTable:
                selectedTable = parent.getSelectedItem().toString();
                Toast.makeText(this, "Table Selected: " + parent.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Datepicker function
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // Datepicker callback
    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + month_string + year_string);

    }

    // Not used. Required interface methods
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
