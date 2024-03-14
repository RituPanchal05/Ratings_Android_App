package com.example.ratings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    RatingBar academic, administrative, infra, campus, extracurricular, faculty, tech, cleanliness, social, parking;
    public String academicText;
    public String academicNum, administrativeNum, infraNum, campusNum, extracurricularNum, facultyNum, techNum, socialNum, cleanlinessNum, parkingNum;


    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RATINGBAR IDs

        setContentView(R.layout.activity_main);
        academic = findViewById(R.id.academic);
        administrative = findViewById(R.id.administrative);
        infra = findViewById(R.id.infra);
        campus = findViewById(R.id.campus);
        extracurricular= findViewById(R.id.extracurricular);
        faculty = findViewById(R.id.faculty);
        tech = findViewById(R.id.tech);
        social = findViewById(R.id.social);
        cleanliness = findViewById(R.id.cleanliness);
        parking = findViewById(R.id.parking);

        Button button = findViewById(R.id.addRating);


        // Initialize Button

        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    academicNum = String.valueOf(academic.getRating());
                    administrativeNum = String.valueOf(administrative.getRating());
                    infraNum = String.valueOf(infra.getRating());
                    campusNum = String.valueOf(campus.getRating());
                    extracurricularNum = String.valueOf(extracurricular.getRating());
                    facultyNum = String.valueOf(faculty.getRating());
                    techNum = String.valueOf(tech.getRating());
                    socialNum = String.valueOf(social.getRating());
                    cleanlinessNum = String.valueOf(cleanliness.getRating());
                    parkingNum = String.valueOf(parking.getRating());

                    addToDatabase(academicNum,administrativeNum,infraNum,campusNum,extracurricularNum,facultyNum,techNum,socialNum,cleanlinessNum,parkingNum);

                }
            });
        }
    }
    private void addToDatabase(String academicNum, String administrativeNum, String infraNum, String campusNum, String extracurricularNum, String facultyNum, String techNum, String socialNum, String cleanlinessNum, String parkingNum) {
        String url = "http://192.168.56.1/add_ratings.php";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);


        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "Your Response has been stored" , Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("academic", academicNum);
                params.put("administrative", administrativeNum);
                params.put("infra", infraNum);
                params.put("extracurricular", extracurricularNum);
                params.put("campus", campusNum);
                params.put("faculty", facultyNum);
                params.put("tech", techNum);
                params.put("social", socialNum);
                params.put("cleanliness", cleanlinessNum);
                params.put("parking", parkingNum);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);

    }

    public void openPieChartActivity(View view) {
        Intent intent = new Intent(this, PieChartActivity.class);
        startActivity(intent);
    }
}