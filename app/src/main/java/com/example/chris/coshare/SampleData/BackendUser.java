package com.example.chris.coshare.SampleData;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chris on 12/12/2017.
 */

public class BackendUser implements data {
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    @Override
    public DatabaseReference initialise() {
        database= FirebaseDatabase.getInstance();
        DBrefUsers = database.getReference().child("Users");

        return DBrefUsers;
    }

    public String getName(String phoneNumber) {
        String name = DBrefUsers.child(phoneNumber).child("Owner Name").toString();
        return name;
    }

    public ArrayList<String> getPersonalData(DataSnapshot dataSnapshot, String phoneNumber) {
        ArrayList<String> personalinfo = new ArrayList<>();
        String name = dataSnapshot.child(phoneNumber).child("Owner Name").getValue().toString();
        String PersonaltableLocation = dataSnapshot.child(phoneNumber).child("Table Info").child("Location").getValue().toString();
        String PersonaltableID = dataSnapshot.child(phoneNumber).child("Table Info").child("TableID").getValue().toString();
        String PersononalBookingStatus = dataSnapshot.child(phoneNumber).child("Booking Status").getValue().toString();
        String LatestBookingDate = dataSnapshot.child(phoneNumber).child("Date").getValue().toString();
        String LatestBookingLocation = dataSnapshot.child(phoneNumber).child("Latest Location").getValue().toString();
        personalinfo.add(name);
        personalinfo.add(PersonaltableLocation);
        personalinfo.add(PersonaltableID);
        personalinfo.add(PersononalBookingStatus);
        personalinfo.add(LatestBookingDate);
        personalinfo.add(LatestBookingLocation);
        return personalinfo;
    }

    public HashMap<String,Long> getLocationsVisited(DataSnapshot dataSnapshot, String phoneNumber){
        HashMap<String,Long> loc=new HashMap();
        Iterable<DataSnapshot> locations=dataSnapshot.child(phoneNumber).child("Locations").getChildren();
        Log.i("check","Triggered");
        for(DataSnapshot dsp : locations){
            String key=dsp.getKey().toString();
            Log.i("check",key);
            Long value=(Long) dsp.getValue();
            loc.put(key,value);
        }
        return loc;
    }
    public long getUserPoints(DataSnapshot dataSnapshot,String phoneNumber){
        long points =(long) dataSnapshot.child(phoneNumber).child("Points").getValue();

        return points;
    }
}
