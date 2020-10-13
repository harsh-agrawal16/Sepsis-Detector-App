package com.example.sepsisdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class patientListActivity extends AppCompatActivity {

    static ArrayList<String> patients = new ArrayList<String>();
    static ArrayAdapter<String> arrayAdapter;
    ListView patientsListView ;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.train:
                Intent i = new Intent(getApplicationContext(), trainActivity.class);
                startActivity(i);
                return true;
            case R.id.about:
                Intent j = new Intent(getApplicationContext(), aboutActivity.class);
                startActivity(j);
                return true;
            case R.id.symptoms:
                Intent k = new Intent(getApplicationContext(), symptomsActivity.class);
                startActivity(k);
                return true;
            case R.id.logout:
                mFirebaseAuth.getInstance().signOut();
                startActivity(new Intent(patientListActivity.this, MainActivity.class));
            default:
                return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        //patients.clear();

        patients.add("ADD A NEW PATIENT.");
        patients.add("Patient 1");
        patients.add("Patient 2");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,patients);
        patientsListView = findViewById(R.id.patientsListView);
        patientsListView.setAdapter(arrayAdapter);

        patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Intent intent = new Intent(patientListActivity.this,patientDetailsActivity.class);
                    intent.putExtra("PatientId", patients.get(position));
                    startActivity(intent);
                }else{
                    startActivity(new Intent(patientListActivity.this,AddNewPatientActivity.class));
                }
            }
        });

        //arrayAdapter.notifyDataSetChanged();
        //arrayAdapter = new ArrayAdapter(patientListActivity.this, android.R.layout.simple_list_item_1,patients);


    }
}
