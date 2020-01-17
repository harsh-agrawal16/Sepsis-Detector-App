package com.example.sepsisdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewPatientActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText numberEdittext;
    DatabaseReference myRef;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient);

        myRef = FirebaseDatabase.getInstance().getReference("patients");
        addButton = findViewById(R.id.addButton);
        nameEditText = findViewById(R.id.nameEditText);
        numberEdittext = findViewById(R.id.numberEditText);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }


    private void createAccount()
    {
        String name = nameEditText.getText().toString();
        String number = numberEdittext.getText().toString();
        if(TextUtils.isEmpty(name)){
            nameEditText.setError("Please Enter Name.");
            nameEditText.requestFocus();
        }
        else if(TextUtils.isEmpty(number)){
            numberEdittext.setError("Please Enter a number.");
            numberEdittext.requestFocus();
        }
        else{
            myRef = FirebaseDatabase.getInstance().getReference("Patients");
            String id = myRef.push().getKey();
            Patient patient = new Patient(id,name,number);
            myRef.child(id).setValue(patient);
        }

    }
}
