package com.example.chris.coshare;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michelle on 12/5/2017.
 */

public class    AccountPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefLocations;
    backend be;

    String name;
    String tableLocation;
    String tableID;
    long userPoints;
    ArrayList<String> personalDetails;
    HashMap<String,Long> location;
    String phoneNumber;
    TextView welcomeMsg;
    TextView points;
    TextView locationsVisited;

    TextView rewards;
    Button claim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phoneNumber="83423995";
        database= FirebaseDatabase.getInstance();
        DBrefLocations = database.getReference().child("Users");
        personalDetails=new ArrayList<>();
        // location=new HashMap<>();

        welcomeMsg=findViewById(R.id.welcomeUser);
        points=findViewById(R.id.totalPointsNum);
        locationsVisited=findViewById(R.id.tablesSharedNum);

        rewards = findViewById(R.id.rewardsTV);
        claim  = findViewById(R.id.claimReward);

        be=new backend();
        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalDetails=be.getPersonalData(dataSnapshot,phoneNumber);
                name=personalDetails.get(0);
                tableLocation=personalDetails.get(1);
                tableID=personalDetails.get(2);
                Toast.makeText(getApplicationContext(), "" + name, Toast.LENGTH_SHORT).show();

                //set user's points
                userPoints=be.getUserPoints(dataSnapshot,phoneNumber);

                //set hello message
                welcomeMsg.setText("Hello "+name);

                points.setText(Long.toString(userPoints));

                location=be.getLocationsVisited(dataSnapshot,phoneNumber);
                Long totalLocations=0L;
                for(String i:location.keySet()){
                    totalLocations=totalLocations+location.get(i);
                }
                locationsVisited.setText(Long.toString(totalLocations));


                claim.setVisibility(View.INVISIBLE);

                if (userPoints == 1000){
                    //button show
                    rewards.setText("Claim your rewards!");
                    claim.setVisibility(View.VISIBLE);
                    claim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog alertDialog = new AlertDialog.Builder(AccountPage.this).create();
                            LayoutInflater factory = LayoutInflater.from(AccountPage.this);
                            final View view = factory.inflate(R.layout.alertdialog, null);
                            alertDialog.setView(view);
                            alertDialog.setTitle("Congratulations!");
                            alertDialog.setMessage("Show this e-voucher to claim your free Starbucks!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();



                        }
                    });

                    //reset to zero

                } else if (userPoints < 1000){
                    Integer userPts = (int) (long) userPoints;
                    int remainingPts = 1000 - userPts;
                    rewards.setText(remainingPts + " points \n more to claim your reward!");
                } else if (userPoints > 1000){
                    Integer userPts = (int) (long) userPoints;
                    int remainingPts = 2000 - userPts;
                    rewards.setText(remainingPts + " points \n more to claim your reward!");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

