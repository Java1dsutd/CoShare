package com.example.chris.coshare;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.chris.coshare.SampleData.BackEndFactory;
import com.example.chris.coshare.SampleData.BackendWhole;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Michelle on 12/10/2017.
 */

public class AddBookingPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner locspinner;
    Spinner tableidspinner;
    TextView dateTV;
    ImageView bookingimg;
    TextView addText;


    FirebaseDatabase database;
    DatabaseReference DBrefLocations;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefWhole;

    BackEndFactory befwhole;
    BackendWhole beWhole;

    ArrayList<String> personalDetails;
    String phoneNumber;


    String locationselectedbyuser;
    String tableidselectedbyuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbookingpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dateTV = (TextView) findViewById(R.id.displayDate);
        bookingimg = (ImageView) findViewById(R.id.locationbookedImg);
        addText = (TextView) findViewById(R.id.addressFetch);


        ////////////
        //Firebase//
        ////////////

        phoneNumber="83423995";
        database= FirebaseDatabase.getInstance();

        DBrefUsers = database.getReference();
        DBrefLocations = database.getReference().child("Users");
        personalDetails=new ArrayList<>();

        /////////////////
        //First Spinner//
        /////////////////

        // Spinner element
        locspinner = (Spinner) findViewById(R.id.location_spinner);

        // Spinner click listener
        locspinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Bugis Junction Tower");
        categories.add("Orchard Tower");
        categories.add("Tampines Telepark");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        locspinner.setAdapter(dataAdapter);



        //////////////////
        //Second Spinner//
        //////////////////

        tableidspinner = (Spinner) findViewById(R.id.tableid_spinner);

        // Spinner click listener
        tableidspinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> tableid = new ArrayList<String>();
        tableid.add("Table 1001");
        tableid.add("Table 2001");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterID = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tableid);

        // Drop down layout style - list view with radio button
        dataAdapterID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tableidspinner.setAdapter(dataAdapterID);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.location_spinner){
            Log.i("in location spinner" , "here");
            switch (position){
                case 0:
                    bookingimg.setImageResource(R.drawable.bugis);
                    addText.setText("230 Victoria Street 188024");
//                    Toast.makeText(this, "b", Toast.LENGTH_SHORT).show();
                    locationselectedbyuser = "Bugis";

                    break;


                case 1:
                    bookingimg.setImageResource(R.drawable.orchard);
                    addText.setText("400 Orchard Road 238875");
//                    Toast.makeText(this, "o", Toast.LENGTH_SHORT).show();

                    locationselectedbyuser = "Orchard";

                    break;
                case 2:
                    bookingimg.setImageResource(R.drawable.telepark);
                    addText.setText("5 Tampines Central 6, Singapore 529482");
//                    Toast.makeText(this, "Tampines", Toast.LENGTH_SHORT).show();

                    locationselectedbyuser = "Tampines";

                    break;
            }

        } else if (spinner.getId() == R.id.tableid_spinner){
            Log.i("in tableid spinner" , "here");
            switch (position){
                case 0:
//                    Toast.makeText(this, "t1", Toast.LENGTH_SHORT).show();

                    tableidselectedbyuser = "Table1001";

                    break;

                case 1:
//                    Toast.makeText(this, "t2", Toast.LENGTH_SHORT).show();
                    tableidselectedbyuser = "Table2001";

                    break;


            }

        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



    public void showDatePickerDialog(View v) {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(dayOfMonth) +"-"+String.valueOf(monthOfYear)
                        +"-"+String.valueOf(year);
                dateTV.setText(date);


            }
        }, yy, mm, dd);
        datePicker.show();

    }



    public void submitbooking (View v){

        befwhole = new BackEndFactory();
        beWhole = (BackendWhole) befwhole.getBackend("whole");
        DBrefWhole = beWhole.initialise();


        DBrefWhole.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show = dataSnapshot.toString();
                System.out.println("Is it from here?");
                System.out.println(show);
                Log.i("loc in submit", locationselectedbyuser);
                Log.i("id in submit", tableidselectedbyuser);

                String availability = beWhole.getBooleanAvailability(dataSnapshot, locationselectedbyuser, tableidselectedbyuser);
                String userBookingStatus = beWhole.getBookingStatus(dataSnapshot, phoneNumber);
                String nameofOccupant = beWhole.getName(dataSnapshot, phoneNumber);


                System.out.println("avail " + availability);
                System.out.println("status " + userBookingStatus);
                System.out.println("occupant name " + nameofOccupant);

                if (!userBookingStatus.equals("Booked")) {
                    if (availability.equals("true")) {
                        String dateforDB = dateTV.getText().toString();
                        //set date
                        //System.out.println(dateforDB);
                        beWhole.setDate(phoneNumber, dateforDB);
                        beWhole.setBookingStatus(phoneNumber, "Booked");
                        beWhole.setBookingTableID(phoneNumber,tableidselectedbyuser);
                        beWhole.setLatestLocation(phoneNumber, locationselectedbyuser);
                        beWhole.setOccupantName(locationselectedbyuser,tableidselectedbyuser, nameofOccupant);
                        beWhole.setLocationsToFalse(locationselectedbyuser,tableidselectedbyuser, false);



                        Toast.makeText(AddBookingPage.this, "Booking Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddBookingPage.this, successFragment.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(AddBookingPage.this, "Table is taken. Please select another table!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.i("in to cancel booking", "in here");
                    Intent intent = new Intent(AddBookingPage.this, CancelBookingPage.class);
                    startActivity(intent);

                    Toast.makeText(AddBookingPage.this, "Please cancel existing booking before making new booking!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("DEBUG", "FAILURE");
            }
        });
    }


}
