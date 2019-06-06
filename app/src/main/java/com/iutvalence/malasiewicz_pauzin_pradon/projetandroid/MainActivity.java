package com.iutvalence.malasiewicz_pauzin_pradon.projetandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Spinner platformSpinner, regionSpinner;
    String[] plaftorms = { "pc", "ps4", "xbo"};
    String[] regions = { "eu", "us", "asia"};
    TextView battletag;
    ProgressBar logoCharge;
    String btTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        battletag = findViewById(R.id.battleTagTV);
        logoCharge = findViewById(R.id.logoCharge);
        initSpinners();

        Button test = findViewById(R.id.button);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestStr = "https://ow-api.com/v1/stats/"+platformSpinner.getSelectedItem().toString()+"/"+regionSpinner.getSelectedItem().toString()+"/"+getBattleTag()+"/complete";
                Log.e("request", requestStr);
                new getAsync().execute(requestStr);
                logoCharge.setVisibility(View.VISIBLE);
            }
        });

        SharedPreferences prefs = getSharedPreferences("BattleTagSave", MODE_PRIVATE);
        String restoredBattleTag = prefs.getString("Battletag", null);
        if (restoredBattleTag != null) {
            battletag.setText(restoredBattleTag);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        logoCharge.setVisibility(View.GONE);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences("BattleTagSave", MODE_PRIVATE).edit();
        editor.putString("Battletag", btTag);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences("BattleTagSave", MODE_PRIVATE).edit();
        editor.putString("Battletag", btTag);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            startAboutActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initSpinners()
    {
        platformSpinner = findViewById(R.id.platformSpinner);
        ArrayAdapter<String> platformAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, plaftorms);

        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        platformSpinner.setAdapter(platformAdapter);


        regionSpinner = findViewById(R.id.regionSpinner);
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, regions);

        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);

    }

    private String getBattleTag()
    {
        btTag = String.valueOf(battletag.getText());
        return btTag.replace("#","-");
    }

    private void startAboutActivity()
    {
        this.startActivity(new Intent(this, AboutActivity.class));
    }

    public class getAsync extends AsyncTask<String , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("Response", "" + server_response);
            if(server_response.contains("error"))
            {
                View constraintLayout = findViewById(R.id.constraintLayout);
                Snackbar snackbar = Snackbar.make(constraintLayout, "Error, Player not found", 5000);
                snackbar.show();
                logoCharge.setVisibility(View.GONE);
            }
            else if(server_response!=null)
            {
                Intent detailsIntent = new Intent(getBaseContext(), ProfileDetailsActivity.class);
                detailsIntent.putExtra("API_REQUEST_RESPONSE", server_response);
                detailsIntent.putExtra("USER_REGION", regionSpinner.getSelectedItem().toString());
                detailsIntent.putExtra("USER_PLATFORM", platformSpinner.getSelectedItem().toString());
                detailsIntent.putExtra("USER_BATTLETAG", getBattleTag());
                startActivity(detailsIntent);
            }
            else
            {
                View constraintLayout = findViewById(R.id.constraintLayout);
                Snackbar snackbar = Snackbar.make(constraintLayout, "Error, couldn't contact API", 5000);
                snackbar.show();
                logoCharge.setVisibility(View.GONE);
            }

        }


        // Converting InputStream to String

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }


    }
}
