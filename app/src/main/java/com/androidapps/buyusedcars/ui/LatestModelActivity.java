package com.androidapps.buyusedcars.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androidapps.buyusedcars.R;
import com.androidapps.buyusedcars.utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatestModelActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    private RequestQueue mQueue;
    String selectedMake, selectedModel;
    List<String> modelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_latest_model);

        //we need to set toolbar as actionbar using  setSupportActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modelList = new ArrayList<String>();

        //then we need to get actionbar using getSupportActionBar to use actionbar methods
        // like setTitle(),setDisplayHomeAsUpEnabled
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
/*

            when you start this activity using up button getExtras() return null.to avoid null pointer exception
        up button create new instance of this activity.so all data erased.to avoid that and preserve last modified data
        we should make this activtie's launch mode single top.so that up button won't create new instance .instead of it will
       call already existing instance of this activity .

*/
        if (getIntent().getExtras() != null) {
            selectedMake = getIntent().getExtras().getString("brand");
            //we need to setTitle after get the intent value because title depends the intent.
            Log.d("brandName: ", selectedMake);
            ab.setTitle("Models In " + selectedMake);
        }
        listView = findViewById(R.id.model_result);

        listView.setOnItemClickListener(this);


        //this is the way we create  and send parse request to volley APi.
        // it will create more object for each request.
        //to avoid more instance, we need to create singleton class.
//        mQueue= Volley.newRequestQueue(this);

        //from singleton class
        mQueue = VolleySingleton.getInstance(this).getRequestQueue();


        jsonParse();


    }

    private void jsonParse() {

        String url = "https://api.jsonbin.io/b/5eb4b8ad8284f36af7b765d4";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            JSONArray Array_Car_model = response.getJSONArray("Car Model");

                            for (int i = 0; i < Array_Car_model.length(); i++) {
                                JSONObject object_in_car_model_array = Array_Car_model.getJSONObject(i);


                                String brand_in_array = object_in_car_model_array.getString("Brand");
                                Log.d("brandInCarModelArray", brand_in_array);


                                if (selectedMake.equals(brand_in_array)) {

                                    JSONArray models_array = object_in_car_model_array.getJSONArray("Models");


                                    Log.d(" models in " + selectedMake + ":", String.valueOf(models_array));

                                    for (int j = 0; j < models_array.length(); j++) {
                                        selectedModel = models_array.getString(j);

                                        Log.d("brands in" + selectedMake + ":", selectedModel);

                                        modelList.add(selectedModel);

                                    }

                                    Log.d("modelList", String.valueOf(modelList));
                                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(LatestModelActivity.this, android.R.layout.simple_list_item_1, modelList);
                                    listView.setAdapter(itemsAdapter);
                                    break;
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                map.put("secret-key", "$2b$10$0pSYdVvHThP2NzfP8wrvgOLDBVFIN9UPcN.bVGpXfCSsNYdezyO52");
             /* map.put("Accept", "application/json");
              map.put("app_id","90f10c6a");
              map.put("app_key","9d23d9250d9f2ee8aa49efda732e4d3d");*/
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_MAX_RETRIES));
        mQueue.add(request);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.model_result) {
            String selectedModelInList = listView.getItemAtPosition(i).toString();
            Intent intent = new Intent(this, SearchResultWithViewPager.class);
            intent.putExtra("make", selectedMake);
            intent.putExtra("model", selectedModelInList);
            startActivity(intent);


        }


    }
}


