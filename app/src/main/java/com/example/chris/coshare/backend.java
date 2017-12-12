package com.example.chris.coshare;

import android.provider.ContactsContract;
import android.support.constraint.solver.widgets.Snapshot;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chris on 5/12/2017.
 */

public class backend {
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefLocations;


    public backend() {
        database = FirebaseDatabase.getInstance();
        DBrefUsers = database.getReference().child("Users");
        DBrefLocations = database.getReference().child("Locations");
    }

    public String getName(String phoneNumber) {
        String name = DBrefUsers.child(phoneNumber).child("Owner Name").toString();
        return name;
    }

    public Boolean Book(DataSnapshot snapshot, String location, String tableid,String phoneno) { //snapshot at database
        if ((boolean)snapshot.child("Locations").child(location).child(tableid).child("Availability").getValue() == true) { //if table available
            DBrefLocations.child(location).child(tableid).child("Availability").setValue(false);  //not available anymore
            DBrefLocations.child(location).child(tableid).child("Occupant").setValue(DBrefUsers.child(phoneno).toString()); //fill in booker
            DBrefUsers.child(phoneno).child("Booking Status").setValue("booked"); //set user booking status to booked
            int locationcount = Integer.parseInt(snapshot.child("Users").child(phoneno).child("Locations").child(location).getValue().toString());//retrieve location count
            locationcount += 1; //increase by 1
            DBrefUsers.child(phoneno).child("Locations").child(location).setValue(locationcount);//update locationcount
            return true;
        } else {
            return false; //seperate function to notify user "oh no it has booked"
        }
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

    public HashMap<ArrayList<String>,Boolean> getEntireTable(DataSnapshot dataSnapshot) {
        HashMap<ArrayList<String>,Boolean> entiretable = new HashMap();
        Iterable<DataSnapshot> locations = dataSnapshot.getChildren();
        for(DataSnapshot location:locations){
            String key1 = location.getKey().toString();
            Iterable<DataSnapshot> tables = location.getChildren();
            for (DataSnapshot table:tables) {
                String key2 = table.getKey().toString();
                ArrayList key = new ArrayList<String>();
                key.add(key1); //locations
                key.add(key2); //table number
                Boolean value = (Boolean) table.child("Availability").getValue(); //status of the table number
                entiretable.put(key,value);
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

//    public ArrayList<String> getSomeTable(DataSnapshot dataSnapshot) {
//        HashMap<ArrayList<String>,Boolean> entiretable = new HashMap();
//        ArrayList key = new ArrayList<String>();
//        Iterable<DataSnapshot> locations = dataSnapshot.getChildren();
//        for(DataSnapshot location:locations){
//            String key1 = location.getKey().toString();
//            Iterable<DataSnapshot> tables = location.getChildren();
//            for (DataSnapshot table:tables) {
//                String key2 = table.getKey().toString();
//
//                key.add(key1); //locations
//                key.add(key2); //table number
//                Boolean value = (Boolean) table.child("Availability").getValue(); //status of the table number
//                entiretable.put(key,value);
//            }
//        }
////        for (ArrayList<String> name: entiretable.keySet()){
////            for(String i:name){
////                Log.i("check",i);
////            }
////            String value = entiretable.get(name).toString();
////            Log.i("check",value);
////        }
//        return key;
//    }


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
    //snapshot is with reference to DatabaseReference locationName=DBref.child("Users");
    public long getUserPoints(DataSnapshot dataSnapshot,String phoneNumber){
        long points =(long) dataSnapshot.child(phoneNumber).child("Points").getValue();

        return points;
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