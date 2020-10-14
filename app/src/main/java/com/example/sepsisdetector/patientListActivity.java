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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class patientListActivity extends AppCompatActivity {


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    static ArrayList<String> patients = new ArrayList<String>();
    static ArrayAdapter<String> arrayAdapter;
    ListView patientsListView ;
    String hospitalId = getIntent().getStringExtra("hospitalId");

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
                startActivity(new Intent(patientListActivity.this, MainActivity.class));
            default:
                return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        patients.add("ADD A NEW PATIENT.");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        myAPI api = retrofit.create(myAPI.class);

        Call<ResponseBody> call = api.getPatientsList(hospitalId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ResponseBody jsonObject = response.body();
                    for(int i=0;i<jsonObject.contentLength();i++){
                        patients.add(jsonObject[i].name);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("This is what you got: ", response.body().string());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("API didnt work!", t.getMessage());
                t.printStackTrace();
            }
        });

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

        arrayAdapter.notifyDataSetChanged();
        //arrayAdapter = new ArrayAdapter(patientListActivity.this, android.R.layout.simple_list_item_1,patients);
        
    }
}
