package com.example.sepsisdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class hospitalDashboardActivity extends AppCompatActivity {
    
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();



    ImageView patientsImageView;
    ImageView addImageView;
    TextView patientsTextView;
    TextView addTextView;

    String hospitalId = getIntent().getStringExtra("hospitalId");


    public void goToPatientsList(View view){
        Intent intent = new Intent(hospitalDashboardActivity.this, patientListActivity.class);
        intent.putExtra("hospitalId", hospitalId);
        startActivity(intent);
    }

    public void addPatient(View view){
        startActivity(new Intent(hospitalDashboardActivity.this, AddNewPatientActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);

        patientsImageView = (ImageView) findViewById(R.id.patientsImageView);
        addImageView = (ImageView) findViewById(R.id.addImageView);
        patientsTextView = findViewById(R.id.patientsList);
        addTextView = findViewById(R.id.addTextView);

        myAPI api;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        api = retrofit.create(myAPI.class);

        Call<ResponseBody> call = api.getPatientsList(hospitalId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("This is what you got: ", string);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("API didnt work!", t.getMessage());
                t.printStackTrace();
            }
        });

//        Log.i("Patients List", patientList[0].name);



    }
}
