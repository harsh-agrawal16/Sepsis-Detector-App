package com.example.sepsisdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpActivity extends AppCompatActivity {


    EditText patientidEditText;
    EditText passwordEditText;
    Button signUpButton;
    TextView loginTextView;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = findViewById(R.id.signUpButton);
        patientidEditText = findViewById(R.id.patientIdEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mFirebaseAuth = FirebaseAuth.getInstance();
        loginTextView = findViewById(R.id.loginTextView);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientId = patientidEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(patientId.isEmpty()){
                    patientidEditText.setError("Provide a valid patientId.");
                    patientidEditText.requestFocus();
                }else if(password.isEmpty()){
                    passwordEditText.setError("Provide a valid password");
                    passwordEditText.requestFocus();
                }else if(patientId.isEmpty() && password.isEmpty()){
                    Toast.makeText(signUpActivity.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }else if(!(patientId.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(patientId,password).addOnCompleteListener(signUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(signUpActivity.this,"Sign Up unsuccessful!",Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(signUpActivity.this,"Sign Up Successful!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(signUpActivity.this, patientListActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUpActivity.this,MainActivity.class));
            }
        });
    }
}
