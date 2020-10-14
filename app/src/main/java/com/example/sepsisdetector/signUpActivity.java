package com.example.sepsisdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signUpActivity extends AppCompatActivity {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    EditText hospitalIdEditText;
    EditText hospitalNameEditText;
    EditText hospitalAddressEditText;
    EditText passwordEditText;
    Button signUpButton;
    TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = findViewById(R.id.signUpButton);
        hospitalIdEditText = findViewById(R.id.hospitalIdEditText);
        hospitalNameEditText = findViewById(R.id.hospitalNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginTextView = findViewById(R.id.loginTextView);
        hospitalAddressEditText = findViewById(R.id.hospitalAddressEditText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        final myAPI api = retrofit.create(myAPI.class);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hospitalId = hospitalIdEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String address = hospitalAddressEditText.getText().toString();
                String name = hospitalNameEditText.getText().toString();

                Hospital currHospital = new Hospital(name, address, hospitalId, password);

                if(hospitalId.isEmpty()){
                    hospitalIdEditText.setError("Provide a valid hospitalId.");
                    hospitalIdEditText.requestFocus();
                }
                else if(password.isEmpty()) {
                    passwordEditText.setError("Provide a valid password");
                    passwordEditText.requestFocus();
                }
                else if(address.isEmpty()){
                    hospitalAddressEditText.setError("Provide valid address!");
                    hospitalAddressEditText.requestFocus();
                }
                else if(name.isEmpty()){
                    hospitalNameEditText.setError("Provide a name");
                    hospitalNameEditText.requestFocus();
                }
                else if(hospitalId.isEmpty() && password.isEmpty() && name.isEmpty() && address.isEmpty()){
                    Toast.makeText(signUpActivity.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Call<ResponseBody> call = api.hospitalSignUp(currHospital);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String message = null;
                            try {
                                message = response.body().string();
//                                Log.i("Message: ", response.body().string())
                                if(message == "Sorry"){
                                    Toast.makeText(signUpActivity.this, "Already Exists", Toast.LENGTH_SHORT);
                                }
                                else{
                                    Toast.makeText(signUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT);
                                    Intent intent = new Intent(signUpActivity.this, hospitalDashboardActivity.class);
                                    intent.putExtra("hospitalId", hospitalId);
                                    startActivity(intent);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

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
