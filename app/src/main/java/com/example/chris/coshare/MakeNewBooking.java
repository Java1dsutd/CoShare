package com.example.chris.coshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MakeNewBooking extends AppCompatActivity {
    ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makenewbooking);

        add = (ImageButton)findViewById(R.id.addnewbooking);
    }

    public void makenewbooking (View view){
        Intent intent = new Intent (MakeNewBooking.this, Addbookingpage.class);
        startActivity (intent);
    }
}
