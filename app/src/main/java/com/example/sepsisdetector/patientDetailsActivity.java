package com.example.sepsisdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class patientDetailsActivity extends AppCompatActivity {

    Button enterButton;
    TextView patienttextView;
//    FirebaseAuth mFirebaseAuth;
    EditText heartbeatEditText;
    EditText tempEditText;
    EditText breathRateEditText;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public void sendEmail(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "agrwl.harsh16@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Critical Situation of Patient");
        intent.putExtra(Intent.EXTRA_TEXT, "Please attend the patient soon.");
        startActivity(intent);
    }

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
//                mFirebaseAuth.getInstance().signOut();
                startActivity(new Intent(patientDetailsActivity.this, MainActivity.class));
            default:
                return false;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        patienttextView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String patientId = intent.getStringExtra("PatientId");
        patienttextView.setText(patientId + " Details");


        heartbeatEditText = findViewById(R.id.heartbeatEditText);
        tempEditText = findViewById(R.id.tempEditText);
        breathRateEditText = findViewById(R.id.breathRateEditText);
        enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer temp = Integer.parseInt(tempEditText.getText().toString());
                Integer heartbeat = Integer.parseInt(heartbeatEditText.getText().toString());
                Integer breathRate =  Integer.parseInt(breathRateEditText.getText().toString());

                if(temp > 101 || heartbeat >90 || breathRate > 20){
                    //Toast.makeText(patientDetailsActivity.this, "Symptom of Sepsis Detected.",Toast.LENGTH_SHORT).show();
                    Toast.makeText(patientDetailsActivity.this, "Symptom of Sepsis Detected.Triggering notification to doctor.",Toast.LENGTH_SHORT).show();
                    sendEmail();
                    //startActivity(new Intent(patientDetailsActivity.this, patientListActivity.class));
                }else{
                    Toast.makeText(patientDetailsActivity.this, "No Abnormality detected!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(patientDetailsActivity.this, patientListActivity.class));
                }
            }
        });


    }
}
