package com.example.chris.coshare.SampleData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Michelle on 12/13/2017.
 */

public class BackendWhole implements data {
    FirebaseDatabase database;
    DatabaseReference DBrefWhole;
    @Override
    public DatabaseReference initialise() {
        database= FirebaseDatabase.getInstance();
        DBrefWhole = database.getReference();  //fetch whole database

        return DBrefWhole;
    }


    ////////////////////
    //Add booking page//
    ////////////////////
    public String getBookingStatus (DataSnapshot dataSnapshot, String phoneNumber){
        String bookingstatus = dataSnapshot.child("Users").child(phoneNumber).child("Booking Status").getValue().toString();
        return bookingstatus;
    }


    public String getBooleanAvailability (DataSnapshot dataSnapshot, String locationselectedbyuser, String tableidselectedbyuser){
        String avail =dataSnapshot.child("Locations").child(locationselectedbyuser).child(tableidselectedbyuser).child("Availability").getValue().toString();
        return avail;
    }

    public String getName(DataSnapshot dataSnapshot, String phoneNumber) {
        String name = dataSnapshot.child("Users").child(phoneNumber).child("Owner Name").getValue().toString();
        return name;
    }

    public String getDate (DataSnapshot dataSnapshot, String phoneNumber) {
        String name = dataSnapshot.child("Users").child(phoneNumber).child("Date").getValue().toString();
        return name;
    }

    public String getBookingTableID (DataSnapshot dataSnapshot, String phoneNumber) {
        String name = dataSnapshot.child("Users").child(phoneNumber).child("Latest BookingTableID").getValue().toString();
        return name;
    }

    public String getLatestLocation (DataSnapshot dataSnapshot, String phoneNumber) {
        String name = dataSnapshot.child("Users").child(phoneNumber).child("Latest Location").getValue().toString();
        return name;
    }


    //USERS
    public void setDate (String phoneNumber, String date){
        DBrefWhole.child("Users").child(phoneNumber).child("Date").setValue(date);
    }

    public void setBookingStatus (String phoneNumber, String status){
        DBrefWhole.child("Users").child(phoneNumber).child("Booking Status").setValue(status);
    }

    public void setBookingTableID (String phoneNumber, String tableID){
        DBrefWhole.child("Users").child(phoneNumber).child("Latest BookingTableID").setValue(tableID);
    }

    public void setLatestLocation (String phoneNumber, String locationselectedbyuser){
        DBrefWhole.child("Users").child(phoneNumber).child("Latest Location").setValue(locationselectedbyuser);
    }
    //USERS


    //LOCATIONS

    public void setOccupantName (String locationselectedbyuser, String tableidselectedbyuser, String nameofOccupant){
        DBrefWhole.child("Locations").child(locationselectedbyuser).child(tableidselectedbyuser).child("Occupant").setValue(nameofOccupant);
    }

    public void setLocationsToFalse (String locationselectedbyuser, String tableidselectedbyuser, Boolean truefalse){
        DBrefWhole.child("Locations").child(locationselectedbyuser).child(tableidselectedbyuser).child("Availability").setValue(truefalse);

    }



//    //set date
//                    DBrefUsers.child(phoneNumber).child("Date").setValue(dateforDB);
//    //set booking status
//                    DBrefUsers.child(phoneNumber).child("Booking Status").setValue("Booked");
//    //set latest tableid
//                    DBrefUsers.child(phoneNumber).child("Latest BookingTableID").setValue(tableidselectedbyuser);
//    //set latest location
//                    DBrefUsers.child(phoneNumber).child("Latest Location").setValue(locationselectedbyuser);
//    //set occupant's name
//                    DBrefLocations.child(locationselectedbyuser).child(tableidselectedbyuser).child("Occupant").setValue(nameofOccupant);
//
//    //set location to false
//                    DBrefLocations.child(locationselectedbyuser).child(tableidselectedbyuser).child("Availability").setValue(false);


    ////////////////////
    //Add booking page//
    ////////////////////

}
