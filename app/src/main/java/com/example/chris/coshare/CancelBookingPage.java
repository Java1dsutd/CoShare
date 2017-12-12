package com.example.chris.coshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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


public class CancelBookingPage extends AppCompatActivity {
    ImageView locationPic;

    TextView locationText;

    String location;
    TextView addressText;
    TextView dateText;


    FirebaseDatabase database;
    DatabaseReference DBrefLocations;
    backend be;

    DatabaseReference DBrefWhole;
    BackEndFactory befwhole;
    BackendWhole beWhole;

    String tableLocation;
    ArrayList<String> personalDetails;
    String phoneNumber;
    String date;

    Button cancelbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelbookingpage);

        phoneNumber="83423995";
        database= FirebaseDatabase.getInstance();
        DBrefLocations = database.getReference().child("Users");
        personalDetails=new ArrayList<>();

        locationPic = (ImageView) findViewById(R.id.locationbookedImg);
        locationText = (TextView)findViewById(R.id.locationFetch);
        addressText = (TextView)findViewById(R.id.addressFetch);
        dateText  = (TextView)findViewById(R.id.dateFetch);

        cancelbutton = (Button) findViewById(R.id.cancelbooking);

        befwhole = new BackEndFactory();
        beWhole = (BackendWhole) befwhole.getBackend("whole");
        DBrefWhole = beWhole.initialise();

        DBrefWhole.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tableLocation=beWhole.getLatestLocation(dataSnapshot, phoneNumber);
                date = beWhole.getDate(dataSnapshot, phoneNumber);


                if (tableLocation.equals("Bugis")){
                    Log.i("in if statement" , tableLocation);
                    locationPic.setImageResource(R.drawable.bugis);
                    location = tableLocation + " Junction Tower";
                    locationText.setText(location );
                    addressText.setText("230 Victoria Street 188024");
                    dateText.setText(date);

                }

                else if (tableLocation.equals("Orchard")){
                    locationPic.setImageResource(R.drawable.orchard);
                    Log.i("in if statement" , tableLocation);
                    location = tableLocation + " Tower";
                    locationText.setText(location);
                    addressText.setText("400 Orchard Road 238875");
                    dateText.setText(date);

                }

                else if (tableLocation.equals("Tampines")){
                    locationPic.setImageResource(R.drawable.telepark);
                    Log.i("in if statement" , tableLocation);
                    location = tableLocation + " Telepark";
                    locationText.setText(location);
                    addressText.setText("5 Tampines Central 6, Singapore 529482");
                    dateText.setText(date);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void cancelBooking (View view){
        befwhole = new BackEndFactory();
        beWhole = (BackendWhole) befwhole.getBackend("whole");
        DBrefWhole = beWhole.initialise();

        DBrefWhole.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                String tableidselectedbyuser = beWhole.getBookingTableID(datasnapshot, phoneNumber);
                String locationselectedbyuser = beWhole.getLatestLocation(datasnapshot, phoneNumber);

                String availability = beWhole.getBooleanAvailability(datasnapshot, locationselectedbyuser, tableidselectedbyuser);
                String nameofOccupant = beWhole.getName(datasnapshot, phoneNumber);

                beWhole.setDate(phoneNumber, "");
                beWhole.setBookingStatus(phoneNumber, "");

                if (availability.equals("false")) {
                    beWhole.setOccupantName(locationselectedbyuser, tableidselectedbyuser, nameofOccupant);
                    beWhole.setLocationsToFalse(locationselectedbyuser, tableidselectedbyuser, true);
                    beWhole.setBookingTableID(phoneNumber, "");
                    beWhole.setLatestLocation(phoneNumber, "");

                }

                Toast.makeText(CancelBookingPage.this, "Cancelled Latest Booking", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(CancelBookingPage.this, MakeNewBooking.class);
                startActivity(intent);

                finish();

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("DEBUG", "FAILURE");
            }
        });

    }



}
