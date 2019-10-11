package com.project.rnbgopartner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Register Room");


        // Binding MainActivity.java with
        // activity_main.xml file
        setContentView(R.layout.activity_main);

        // Find the WebView by its unique ID
        WebView w = (WebView) findViewById(R.id.web_view);

        // loading http://www.google.com url in the the WebView.
        w.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSc7rEi0FZFhTiFwu_VccgRmm6JozziUb8oLTpcuybkwPQFonQ/viewform?usp=sf_link");

        // this will enable the javascipt.
        w.getSettings().setJavaScriptEnabled(true);

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        w.setWebViewClient(new WebViewClient());




    }
}
