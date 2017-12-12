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

import java.util.ArrayList;

public class CancelBookingPage extends AppCompatActivity {
    ImageView locationPic;

    TextView locationText;

    String location;
    TextView addressText;
    TextView dateText;


    FirebaseDatabase database;
    DatabaseReference DBrefLocations;
    DatabaseReference DBrefUsers;
    DatabaseReference DBwhole;
    backend be;

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

        be=new backend();
        DBrefLocations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalDetails=be.getPersonalData(dataSnapshot,phoneNumber);
                tableLocation=personalDetails.get(5);
                date = personalDetails.get(4);

                if (tableLocation.equals("Bugis")){
                    Log.i("in if statement" , tableLocation);
                    locationPic.setImageResource(R.drawable.bugis);
                    location = tableLocation + " Junction Tower";
                    locationText.setText(location );
                    addressText.setText("230 Victoria Street 188024");
                    dateText.setText(date);


                    cancelbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelBooking(v);
                        }
                    });
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
        database= FirebaseDatabase.getInstance();
        DBwhole = database.getReference();
        DBrefUsers = database.getReference("Users").child(phoneNumber);
        DBrefLocations = database.getReference("Locations");
        DBwhole.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                DBrefUsers.child("Date").setValue("");

                DBrefUsers.child("Booking Status").setValue("");
                String tableid = datasnapshot.child("Users").child(phoneNumber).child("Latest BookingTableID").getValue().toString();
                String location = datasnapshot.child("Users").child(phoneNumber).child("Latest Location").getValue().toString();
                String avail = datasnapshot.child("Locations").child(location).child(tableid).child("Availability").getValue().toString();
                if (avail.equals("false")) {

                    DBrefUsers.child("Latest Location").setValue("");
                    DBrefUsers.child("Latest BookingTableID").setValue("");

                    DBrefLocations.child(location).child(tableid).child("Availability").setValue(true);
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
