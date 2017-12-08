package com.example.chris.coshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signuppage extends AppCompatActivity {

    EditText fullname;
    EditText email;
    EditText username;
    EditText password;
    EditText phoneno;

    Button submitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);
        fullname = (EditText)findViewById(R.id.namedata);
        email = (EditText)findViewById(R.id.emaildata);
        username = (EditText) findViewById(R.id.usernamedata);
        password = (EditText)findViewById(R.id.passworddata);
        password = (EditText)findViewById(R.id.passworddata);
        phoneno = (EditText)findViewById(R.id.phonedata);

        submitbutton = (Button)findViewById(R.id.submitbutton);

    }

    public void clickMe(View view) {

        String myname = fullname.getText().toString().trim();
        String myemail = email.getText().toString().trim();
        String myusername = username.getText().toString().trim();
        String mypassword = password.getText().toString().trim();
        String myphone = phoneno.getText().toString().trim();

        if (myname.length() == 0 | myemail.length() == 0 | myusername.length() == 0 | mypassword.length() == 0 | myphone.length() == 0) {
            emptyDialog();
        }

        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(myphone);

            myRef.child("Name").setValue(myname);
            myRef.child("Email").setValue(myemail);
            myRef.child("Username").setValue(myusername);
            myRef.child("Password").setValue(mypassword);


            successDialog();


        }



    }

    public void emptyDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setMessage("Empty fields present");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        alertDialogBuilder.show();

    }

    public void successDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Success");
        alertDialogBuilder.setMessage("Account successfully created.");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {


                        Intent intent = new Intent(getBaseContext(), LoginPage.class);;
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.show();

    }




}
