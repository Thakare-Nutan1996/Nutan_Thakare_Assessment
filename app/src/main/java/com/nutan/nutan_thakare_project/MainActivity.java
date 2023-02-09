package com.nutan.nutan_thakare_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView imageList;
    VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageList = findViewById(R.id.imageList);
        imageList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter();
        imageList.setAdapter(adapter);
        
        getJsonData();
    }

    private void getJsonData() {
        String URL = "https://api.tvmaze.com/singlesearch/shows?q=girls&embed=episodes";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d(TAG,"onResponse: "+response);
                try {
                    JSONArray episode = response.getJSONArray("episodes");
                    JSONObject episodeData = episode.getJSONObject(5);
                    JSONArray images = episodeData.getJSONArray("image");

//                    Log.d(TAG,"onResponse"+images);

                    for (int i=0; i<images.length();i++){
                        JSONObject image = images.getJSONObject(i);
                        Log.d(TAG,"onResponse"+image.getString("name"));
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"onErrorResponse: " + error.getMessage());
            }
        });

        requestQueue.add(objectRequest);
    }
}