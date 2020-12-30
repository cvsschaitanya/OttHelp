package com.example.otthelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminActivity extends AppCompatActivity {

    EditText titleField,platFormField;
    DatabaseReference titlesRef,platformsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        titlesRef = dRef.child("titles");
        platformsRef = dRef.child("platforms");

        titleField = findViewById(R.id.titleField);
        platFormField = findViewById(R.id.platFormField);

    }

    public void add(View view){
        String titleName = titleField.getText().toString();
        String platformName = platFormField.getText().toString();

        titlesRef.child(titleName.toLowerCase()).child("name").setValue(titleName);
        titlesRef.child(titleName.toLowerCase()).child("availableOn").push().setValue(platformName);

        platformsRef.child(platformName.toLowerCase()).child("name").setValue(platformName);
        platformsRef.child(platformName.toLowerCase()).child("availableTitles").push().setValue(titleName);

        onBackPressed();
    }
}