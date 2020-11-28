package com.example.splashscreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    // Display splash screen for 3 seconds
    private static int SPLASH_TIME_OUT = 3000;

    // Widgets
    TextView quoteField;
    TextView attributionField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        setWidgetReferences();

        // Get quote stuff
//        Quote quoteSource = new Quote();
//        quoteField.setText(quoteSource.getQuote());
//        attributionField.setText(quoteSource.getAttribution());

        new GetDailyQuote().execute();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void setWidgetReferences() {
        quoteField = findViewById(R.id.quote_field);
        attributionField = findViewById(R.id.attribution_field);
    }

    class GetDailyQuote extends AsyncTask<Void, Void, Quote> {

        @Override
        protected Quote doInBackground(Void... voids) {
            return new Quote();
        }

        @Override
        protected void onPostExecute(Quote quote) {
            super.onPostExecute(quote);
            if (quote != null) {
                quoteField.setText(quote.getQuote());
                attributionField.setText(quote.getAttribution());
            }
        }
    }

}
