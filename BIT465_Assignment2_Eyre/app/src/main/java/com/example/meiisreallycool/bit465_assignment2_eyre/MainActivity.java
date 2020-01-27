package com.example.meiisreallycool.bit465_assignment2_eyre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;

    private StringRequest weatherStringRequest(String city, String...country){
        final String API = "&appid=28dcd90ad413f0da37170181533095cd";
        final String URL_Prefix = "https://api.openweathermap.org/data/2.5/weather?q=";
        String Country_Code = "";

        if(country.length > 0){
            Country_Code = "," + country;
        }

        String url = URL_Prefix + city + Country_Code + API;

         final TextView results_view = (TextView) findViewById(R.id.response);

        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            String result = new JSONObject(response).getJSONObject("main").getString("temp");
                            results_view.setText(result);
                        }
                        catch (JSONException err){
                            Toast.makeText(MainActivity.this, err.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(MainActivity.this, "OpenWeatherAPI is not responding", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button search_btn = findViewById(R.id.button);
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                EditText city = (EditText) findViewById(R.id.cityEditText);
                EditText country_code = (EditText) findViewById(R.id.countryCodeEditText);
                StringRequest stringRequest = weatherStringRequest(city.getText().toString(), country_code.getText().toString());
                stringRequest.setTag(city.getText().toString());

                queue.add(stringRequest);
            }
        });
    }
}
