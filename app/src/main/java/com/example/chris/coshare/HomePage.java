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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HomePage extends AppCompatActivity {
    ImageButton add;
    ImageButton cancel;
    ImageButton account;
    ImageButton view;
    ImageView locationPic;
    TextView locationText;

    private FirebaseDatabase firebase;
    private DatabaseReference myRef;

    private String username;

    private HashMap<String,String> placeID;


    private String tableID;
    private String tablePlace;
    private Boolean booked;
    private Boolean occupied;
    private String Occupant;
    private String tableDigit;

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
        locationPic = (ImageView) findViewById(R.id.locationPic);
        locationText = (TextView)findViewById(R.id.bookingDetailsTV);

        firebase = FirebaseDatabase.getInstance("https://coshare-795d4.firebaseio.com/");
        myRef = firebase.getReference(); //get access to the root of the Firebase JSON tree

        // username = getIntent().getStringExtra("user");

        placeID = new HashMap<String, String>();
        placeID.put("1", "Bugis");
        placeID.put("2", "Tampines");

        tableID = getIntent().getStringExtra("barcode"); //get the data from the camera live view intent

        sharedPreferences = getSharedPreferences(nameOfSharedPreferences, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Username","");


        //addListenerForSingleValueEvent: executes OnDataChange method immediately and after executing that method once it stops listening to the reference location it is attached to
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    //if the data received is not null
                    if (tableID != null) {

                        tableDigit = Character.toString(tableID.charAt(5));
                        //search for the table location with the 6th index of the tableID
                        tablePlace = placeID.get(tableDigit);

                        occupied = (Boolean) dataSnapshot.child("Locations").child(tablePlace).child(tableID).child("Current Status").getValue(Boolean.class);
                        Log.i("Norman","hi"+String.valueOf(occupied));
                        booked = (Boolean) dataSnapshot.child("Locations").child(tablePlace).child(tableID).child("Availability").getValue(Boolean.class);
                        Log.i("Norman","bye"+String.valueOf(booked));
                        Occupant = dataSnapshot.child("Locations").child(tablePlace).child(tableID).child("Occupant").getValue(String.class);
                        Log.i("Norman","byebye"+Occupant);
                        Log.i("Norman","hihi"+username);

                        if ((booked)) {
                            if ((occupied)) {
                                if (username.equals(Occupant)) {  //if the table is booked, and the table is not occupied and the user matched, update current status to occupied
                                    Toast.makeText(getApplicationContext(), "Welcome " + Occupant, Toast.LENGTH_LONG).show();
                                    myRef.child("Locations").child(tablePlace).child(tableID).child("Current Status").setValue(false);

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
                                    myRef.child("Locations").child(tablePlace).child(tableID).child("Availability").setValue(false);  //false = table is not booked
                                    myRef.child("Locations").child(tablePlace).child(tableID).child("Occupant").setValue("Empty");
                                    myRef.child("Locations").child(tablePlace).child(tableID).child("Current Status").setValue(true); // table is not occupied
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
                        } else if ((!booked)) { //if the user scan an unbook table
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

        be=new backend();
        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalDetails=be.getPersonalData(dataSnapshot,phoneNumber);
                tableLocation=personalDetails.get(5);
                date = personalDetails.get(4);
                System.out.println(date);

                if (tableLocation.equals("Bugis")){
                    Log.i("in if statement" , tableLocation);
                    locationPic.setImageResource(R.drawable.bugis);
                }

                else if (tableLocation.equals("Orchard")){
                    locationPic.setImageResource(R.drawable.orchard);
                }


                //set location
                locationText.setText(tableLocation +  "\n" + date);


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

    //add your intents here edmund
    public void accountIntent (View view) {
        Intent intent =new Intent(HomePage.this, AccountPage.class);
        startActivity(intent);
    }

    public void addBookingIntent(View v) {
        Intent intent = new Intent(HomePage.this, AddBookingPage.class);
        startActivity(intent);
    }
    public void viewBookingIntent(View v) {
        Intent intent = new Intent(HomePage.this, ViewBookingPage.class);
        startActivity(intent);
    }
    public void cancelBookingIntent(View v) {
        Intent intent = new Intent(HomePage.this, CancelBookingPage.class);
        startActivity(intent);
    }

}
