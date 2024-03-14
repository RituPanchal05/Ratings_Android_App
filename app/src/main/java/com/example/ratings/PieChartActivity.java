package com.example.ratings;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    int[] colors = ColorTemplate.MATERIAL_COLORS;

    private static final String TAG = PieChartActivity.class.getSimpleName();

    private static final String FETCH_URL = "http://192.168.56.1/fetch_data.php";

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        pieChart = findViewById(R.id.pieChart);

        fetchChartData();
    }

    private void fetchChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>(); // Initialize entries ArrayList
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                FETCH_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        double academicAvg = response.optDouble("academic_avg", 0.0);
                        double administrativeAvg = response.optDouble("administrative_avg", 0.0);
                        double infraAvg = response.optDouble("infra_avg", 0.0);
                        double extracurricularAvg = response.optDouble("extracurricular_avg", 0.0);
                        double campusAvg = response.optDouble("campus_avg", 0.0);
                        double facultyAvg = response.optDouble("faculty_avg", 0.0);
                        double techAvg = response.optDouble("tech_avg", 0.0);
                        double socialAvg = response.optDouble("social_avg", 0.0);
                        double cleanlinessAvg = response.optDouble("cleanliness_avg", 0.0);
                        double parkingAvg = response.optDouble("parking_avg", 0.0);

                        entries.add(new PieEntry((float) academicAvg, "Academic"));
                        entries.add(new PieEntry((float) administrativeAvg, "Administrative"));
                        entries.add(new PieEntry((float) infraAvg, "Infra"));
                        entries.add(new PieEntry((float) extracurricularAvg, "Extracurricular"));
                        entries.add(new PieEntry((float) campusAvg, "Campus"));
                        entries.add(new PieEntry((float) facultyAvg, "Faculty"));
                        entries.add(new PieEntry((float) techAvg, "Tech"));
                        entries.add(new PieEntry((float) socialAvg, "Social"));
                        entries.add(new PieEntry((float) cleanlinessAvg, "Cleanliness"));
                        entries.add(new PieEntry((float) parkingAvg, "Parking"));

                        PieDataSet dataSet = new PieDataSet(entries, "Average Ratings");
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        PieData data = new PieData(dataSet);
                        pieChart.setData(data);
                        pieChart.animateY(1000);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error", error);
                        Toast.makeText(PieChartActivity.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonObjectRequest);
    }


}

