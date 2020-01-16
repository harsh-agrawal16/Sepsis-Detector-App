package com.example.sepsisdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class trainActivity extends AppCompatActivity {

    WebView trainWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        trainWebView = findViewById(R.id.trainWebview);
        trainWebView.getSettings().setJavaScriptEnabled(true);
        trainWebView.setWebViewClient(new WebViewClient());
        trainWebView.loadUrl("https://www.healthline.com/health/sepsis");
    }
}
