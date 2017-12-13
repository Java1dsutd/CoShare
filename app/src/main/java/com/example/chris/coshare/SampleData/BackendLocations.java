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

public class BackendLocations implements data {
    FirebaseDatabase database;
    DatabaseReference DBrefLocations;
    @Override
    public DatabaseReference initialise() {
        database= FirebaseDatabase.getInstance();
        DBrefLocations = database.getReference().child("Locations");

        return DBrefLocations;
    }

    public class tableData{
        public Boolean availability;
        public Boolean currentStatus;
        public String occupant;

        public tableData(Boolean availability, Boolean currentStatus, String occupant){
            this.availability = availability;
            this.currentStatus = currentStatus;
            this.occupant = occupant;
        }
    }

    public HashMap<ArrayList<String>,tableData> getEntireTable(DataSnapshot dataSnapshot) {
        HashMap<ArrayList<String>,tableData> entiretable = new HashMap();
        Iterable<DataSnapshot> locations = dataSnapshot.getChildren();
        for(DataSnapshot location:locations){
            String key1 = location.getKey().toString();
            Iterable<DataSnapshot> tables = location.getChildren();
            for (DataSnapshot table:tables) {
                String key2 = table.getKey().toString();
                ArrayList key = new ArrayList<String>();
                key.add(key1); //locations
                key.add(key2); //table number

                //get information of the table
                Boolean value1 = (Boolean) table.child("Availability").getValue(Boolean.class);//status of the table number
                Boolean value2 = (Boolean) table.child("Current Status").getValue(Boolean.class);
                String value3 = (String)table.child("Occupant").getValue(String.class);

                tableData Data = new tableData(value1,value2,value3);

                entiretable.put(key,Data);
            }
        }
//        for (ArrayList<String> name: entiretable.keySet()){
//            for(String i:name){
//                Log.i("check",i);
//            }
//            String value = entiretable.get(name).toString();
//            Log.i("check",value);
//        }
        return entiretable;
    }

    public ArrayList<String> getDbLocation(DataSnapshot ds){
        ArrayList result = new ArrayList<>();
        Iterable<DataSnapshot> locations = ds.getChildren();
        for(DataSnapshot location:locations){
            String myKey1 = location.getKey().toString();
            Iterable<DataSnapshot> tables = location.getChildren();
            result.add(myKey1);
//            for(DataSnapshot table:tables){
//                String myKey = table.getKey().toString();
//            }
        }
        return result;
    }

    public ArrayList<String> getDbTable(DataSnapshot ds){
        ArrayList result = new ArrayList<>();
        Iterable<DataSnapshot> locations = ds.getChildren();
        for(DataSnapshot location:locations){
//            String myKey1 = location.getKey().toString();
            Iterable<DataSnapshot> tables = location.getChildren();
            for(DataSnapshot table:tables){
//                if((boolean)ds.child("Locations").child(location.toString()).child(table.toString()).child("Availability").getValue()==true){
                Object check = table.child("Availability").getValue();
                Log.i("CHECK", ""+check);
//                if(check){
                String myKey = table.getKey().toString();
                result.add(myKey);
//                }
            }
        }
        return result;
    }

    //snapshot is with reference to DatabaseReference locationName=DBref.child("Locations");
    public String getOwnTableCurrentUser(DataSnapshot dataSnapshot, String phoneNumber, String tableLocation, String tableID, String PersonalBookingStatus) {
        String currentUser = null;
        boolean currentStatus = (boolean) dataSnapshot.child(phoneNumber).child(tableLocation).child(tableID).child("Availability").getValue();
        if (PersonalBookingStatus == "booked" && currentStatus == true) {
            currentUser = dataSnapshot.child(phoneNumber).child(tableLocation).child(tableID).child("Occupant").getValue().toString();
        }
        return currentUser;

    }



    public String getRecommendation(DataSnapshot dataSnapshot, String phoneNumber,String bookedLocation){

        HashMap<String,Long> loc=new HashMap();
        String mostVisitedLocation="";
        Long timesVisited=0L;
        Long bookedlocationValue;
        String notVisited="";
        String returnMessage="";
        Iterable<DataSnapshot> locations=dataSnapshot.child(phoneNumber).child("Locations").getChildren();
        for(DataSnapshot dsp : locations){
            String key=dsp.getKey().toString();
            Log.i("check",key);
            Long value=(Long) dsp.getValue();
            loc.put(key,value);

            mostVisitedLocation=key;
            timesVisited=value;

            if (value==0L) {
                notVisited += ", " + key;
            }
        }
        bookedlocationValue=loc.get(bookedLocation);
        if ( bookedlocationValue==0){
            returnMessage=" Good choice!";
        }
        else if(bookedLocation.equalsIgnoreCase(mostVisitedLocation)){
            returnMessage="You have Visited "+mostVisitedLocation+" "+timesVisited+" times, how about trying something new?\n "
                    +notVisited+" are available";
        }

        return returnMessage;
    }
}
