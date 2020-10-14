package com.example.sepsisdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.protobuf.Api;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    EditText hospitalIdEditText;
    EditText passwordEditText;
    Button loginButton;
    TextView signUpTextView;

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
            default:
                return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hospitalIdEditText = findViewById(R.id.hospitalIdEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        final myAPI api = retrofit.create(myAPI.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hospitalId = hospitalIdEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Hospital newHospital = new Hospital(hospitalId , password);

                if(hospitalId.isEmpty()){
                    hospitalIdEditText.setError("Provide a valid patientId.");
                    hospitalIdEditText.requestFocus();
                }else if(password.isEmpty()){
                    passwordEditText.setError("Provide a valid password");
                    passwordEditText.requestFocus();
                }else if(hospitalId.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are empty!",Toast.LENGTH_SHORT);
                }else{
                    Call<ResponseBody> call = api.hospitalLogin(newHospital);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String message = null;
                            try {
                                Log.i("This is what you got: ", response.body().string());
                                message = response.body().string();
                                if(message == "Sorry"){
                                    Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT);
                                    startActivity(new Intent(MainActivity.this, hospitalDashboardActivity.class));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signUpActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        startActivity(new Intent(MainActivity.this, hospitalDashboardActivity.class));
    }
}
