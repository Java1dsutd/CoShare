package com.example.chris.coshare;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.example.chris.coshare.SampleData.BackEndFactory;
import com.example.chris.coshare.SampleData.BackendWhole;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.chris.coshare.SampleData.BackendLocations;
import com.example.chris.coshare.SampleData.BackendUser;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.chris.coshare.SampleData.FastDataModel.ITEM_ID_KEY;

public class HomePage extends AppCompatActivity {
    ImageButton add;
    ImageButton cancel;
    ImageButton account;
    ImageButton view;

    ImageButton locationPic;
    TextView locationText;

    String location;

    DatabaseReference DBrefWhole;
    BackEndFactory befwhole;
    BackendWhole beWhole;

    private FirebaseDatabase firebase;
    private DatabaseReference myRef;

    private String username;

    private HashMap<String,String> placeID;


    private String tableInfo;
    private String tablePlace;
    private Boolean available;
    private Boolean occupied;
    private String Occupant;
    private String tablePlaceInfo;
    private String tableID;
    private long currentPoint;
    private long newPoint;
    private HashMap<String,Long> locationVisited;
    private long oldVisitNumber;
    private long newVisitNumber;
    
    BackEndFactory QRBackEnd;
    BackendLocations QRLocationsDatabase;
    DatabaseReference QRReference;
    BackendUser QRUserDatabase;
    DatabaseReference QRUserReference;
    HashMap<ArrayList<String>,BackendLocations.tableData> tableInformation;

    String tableLocation;
    ArrayList<String> personalDetails;
    String phoneNumber;
    String date;

    FirebaseDatabase database;
    DatabaseReference DBrefLocations;
    backend be;

    private SharedPreferences sharedPreferences;
    private String nameOfSharedPreferences = "Details";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        phoneNumber="83423995";
        database= FirebaseDatabase.getInstance();
        DBrefLocations = database.getReference().child("Users");
        personalDetails=new ArrayList<>();

        add = (ImageButton) findViewById(R.id.addButton);
        cancel = (ImageButton) findViewById(R.id.cancelButton);
        account = (ImageButton) findViewById(R.id.accountButton);
        view = (ImageButton) findViewById(R.id.viewButton);
        locationPic = (ImageButton) findViewById(R.id.locationPic);
        locationText = (TextView)findViewById(R.id.bookingDetailsTV);

        placeID = new HashMap<String, String>();
        placeID.put("amk", "Ang Mo Kio");
        placeID.put("bgs", "Bugis");
        placeID.put("dbg", "Dhoby Ghaut");
        placeID.put("hqs", "HQ");
        placeID.put("ocd", "Orchard");
        placeID.put("tmp", "Tampines");

        tableInfo = getIntent().getStringExtra("barcode"); //get the data from the camera live view intent

        sharedPreferences = getSharedPreferences(nameOfSharedPreferences, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Username","");
        
        QRBackEnd = new BackEndFactory();
        QRLocationsDatabase = (BackendLocations) QRBackEnd.getBackend("locations");
        QRReference = QRLocationsDatabase.initialise();

        QRUserDatabase = (BackendUser) QRBackEnd.getBackend("users");
        QRUserReference = QRUserDatabase.initialise();

        //addListenerForSingleValueEvent: executes OnDataChange method immediately and after executing that method once it stops listening to the reference location it is attached to
        QRReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    //if the data received is not null
                    if (tableInfo != null) {
                        tablePlaceInfo = tableInfo.substring(0,3); //get the first 3 alphabets for tablePlaceInfo

                        tableID = tableInfo.substring(3); //get the tableID
                        //search for the table location with the first 3 alphabets of tablePlaceInfo
                        tablePlace = placeID.get(tablePlaceInfo);
                        //Extract the entire table
                        tableInformation= QRLocationsDatabase.getEntireTable(dataSnapshot);

                        //create keys to search for the hashmap received from backendfactory
                        ArrayList<String> keys = new ArrayList<String>();
                        keys.add(tablePlace);
                        keys.add(tableID);

                        if (tableInformation.containsKey(keys)){
                            BackendLocations.tableData status = (BackendLocations.tableData)tableInformation.get(keys);
                            occupied = status.currentStatus;
                            available = status.availability;
                            Occupant = status.occupant;

                            Log.i("Norman","hi"+String.valueOf(occupied));
                            Log.i("Norman","bye"+String.valueOf(available));
                            Log.i("Norman","no" + Occupant);

                            if ((!available)) {
                                if ((!occupied)) {
                                    if (username.equals(Occupant)) {  //if the table is booked, and the table is not occupied and the user matched, update current status to occupied
                                        Toast.makeText(getApplicationContext(), "Welcome " + Occupant, Toast.LENGTH_LONG).show();
                                        QRReference.child(tablePlace).child(tableID).child("Current Status").setValue(true);
                                        QRUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                currentPoint = QRUserDatabase.getUserPoints(dataSnapshot,phoneNumber);
                                                Log.i("Norman","currentPoint"+currentPoint);
                                                newPoint = currentPoint + 50;
                                                QRUserReference.child(phoneNumber).child("Points").setValue(newPoint);

                                                locationVisited = QRUserDatabase.getLocationsVisited(dataSnapshot,phoneNumber);
                                                oldVisitNumber = locationVisited.get(tablePlace);
                                                newVisitNumber = oldVisitNumber + 1;
                                                QRUserReference.child(phoneNumber).child("Locations").child(tablePlace).setValue(newVisitNumber);


                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
 
                                    } else { //User scanned the wrong table
                                        AlertDialog wrongDialog = new AlertDialog.Builder(HomePage.this).create();
                                        wrongDialog.setTitle("Opps...");
                                        wrongDialog.setMessage("It seems that you have scanned the wrong table!");
                                        wrongDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(HomePage.this, CameraActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                        wrongDialog.show();
                                    }
                                } else {
                                    if (username.equals(Occupant)) {  //if the table is booked, the table is occupied and the user matched, unbooked the user (User leaving the place)
                                        Toast.makeText(getApplicationContext(), "Thank you for using.", Toast.LENGTH_LONG).show();
                                        QRReference.child(tablePlace).child(tableID).child("Availability").setValue(true);  //true = table is not booked
                                        QRReference.child(tablePlace).child(tableID).child("Occupant").setValue(" ");
                                        QRReference.child(tablePlace).child(tableID).child("Current Status").setValue(false); // false = table is not occupied
                                        QRUserReference.child(phoneNumber).child("Booking Status").setValue(" ");
                                    } else { //(User scans the wrong table)
                                        AlertDialog wrongDialog = new AlertDialog.Builder(HomePage.this).create();
                                        wrongDialog.setTitle("Opps...");
                                        wrongDialog.setMessage("It seems that someone is using this table!");
                                        wrongDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(HomePage.this, CameraActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                        wrongDialog.show();
                                    }

                                }
                            } else if (available) { //if the user scan an unbook table
                                AlertDialog requestDialog = new AlertDialog.Builder(HomePage.this).create();
                                requestDialog.setTitle("Opps...");
                                requestDialog.setMessage("It seems that no one booked this table!");
                                requestDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(HomePage.this, CameraActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                requestDialog.show();

                            }
                        }
                    }

                } catch (NullPointerException ex) { //if cannot find the qrcode value in the firebase
                    AlertDialog invalidDialog = new AlertDialog.Builder(HomePage.this).create();
                    invalidDialog.setTitle("Opps...");
                    invalidDialog.setMessage("Invalid QRCODE detected. Please scan again.");
                    invalidDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                            Intent intent = new Intent(HomePage.this, CameraActivity.class);
                            startActivity(intent);
                        }
                    });
                    invalidDialog.show();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Norman", "Failed to read value.", databaseError.toException());

            }
        });



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
                    locationText.setText(location +  "\n" + date);
                }

                else if (tableLocation.equals("Orchard")){
                    locationPic.setImageResource(R.drawable.orchard);
                    Log.i("in if statement" , tableLocation);
                    location = tableLocation + " Tower";
                    locationText.setText(location +  "\n" + date);

                }
                else if (tableLocation.equals("Tampines")){
                    locationPic.setImageResource(R.drawable.telepark);
                    Log.i("in if statement" , tableLocation);
                    location = tableLocation + " Telepark";
                    locationText.setText(location +  "\n" + date);
                }

                else if (tableLocation.equals("")){
                    locationPic.setImageResource(R.drawable.sad);
//                    Log.i("in if statement" , tableLocation);
//                    location = tableLocation + " Telepark";
                    locationText.setText("No Booking Made");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean openCamera(MenuItem view){
        Intent intent = new Intent(HomePage.this, CameraActivity.class);
        startActivity(intent);
        return true;
    }
//    //Imagebutton link to account activity
//    public void accountIntent (View view) {
//        Intent intent =new Intent(HomePage.this, AccountPage.class);
//        startActivity(intent);
//    }

    public void getDirection (View view) {
        Intent intentB = new Intent(HomePage.this, GetDirections.class);
        intentB.putExtra("LOCATION", location);
        startActivity (intentB);

    }

    //add your intents here edmund
    public void accountIntent (View view) {
        Intent intent =new Intent(HomePage.this, AccountPage.class);
        startActivity(intent);
    }

    public void addBookingIntent(View v) {
        Intent intent = new Intent(HomePage.this, AddBookingPage.class);
        String test = "HQ";
        intent.putExtra(ITEM_ID_KEY,test);
        startActivity(intent);
    }
    public void viewBookingIntent(View v) {
        Intent intent = new Intent(HomePage.this, ViewBookingPage.class);
        startActivity(intent);
    }
    public void cancelBookingIntent(View view){

        be=new backend();
        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalDetails=be.getPersonalData(dataSnapshot,phoneNumber);
                tableLocation=personalDetails.get(5);
                date = personalDetails.get(4);

                if (tableLocation.equals("")) {
                    Intent intent = new Intent (HomePage.this, MakeNewBooking.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent (HomePage.this, CancelBookingPage.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
