package com.example.strontium.lamont_v1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    ArrayList<HashMap<String, String>> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final TextView txtT = findViewById(R.id.txtT);
        //txtC = findViewById(R.id.txtC);
        String url = "http://lamontlive.com/wp-json/wp/v2/posts?fields=title,content";
        //String url = "http://lamontlive.com/wp-json/wp/v2/posts/191?fields=title,content";
        postList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


        final JsonArrayRequest jsAryRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    String jsonResponseT = "";
                    String jsonResponseC = "";

                    for (int i = 0; i < response.length(); i++){
                        JSONObject post = (JSONObject) response.get(i);
                        JSONObject title = post.getJSONObject("title");
                        String renderedT = title.getString("rendered");
                        JSONObject content = post.getJSONObject("content");
                        String renderedC = content.getString("rendered");


                        HashMap<String, String> blogpost = new HashMap<>();



                        blogpost.put("renderedT", renderedT);
                        blogpost.put("renderedC", renderedC);

                        postList.add(blogpost);

                        //jsonResponseT += "" + renderedT + "\n\n";
                        //jsonResponseT += "" + renderedC + "\n\n";
                        //jsonResponseC += renderedT;
                        //TextView txtT = findViewById(R.id.txtT);
                        //txtT.setText(renderedT);
                        System.out.println(renderedT);
                    }
                    //listView.setOnItemClickListener(this);
                    //System.out.println(jsonResponseC+"\n\n");

                    //txtT.setText(jsonResponseT);
                    //txtC.setText(jsonResponseC);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsAryRequest);
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, postList,
                R.layout.list_item, new String[]{ "renderedT","renderedC"},
                new int[]{R.id.title, R.id.content});
        lv.setAdapter(adapter);

        /*JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject title = response.getJSONObject("title");
                            String renderedT = title.getString("rendered");
                            JSONObject content = response.getJSONObject("content");
                            String renderedC = content.getString("rendered");

                            String jsonResponseT = "";
                            String jsonResponseC = "";
                            jsonResponseT += "" + renderedT + "\n\n";
                            jsonResponseC += "" + renderedC + "\n\n";

                            txtT.setText(jsonResponseT);
                            txtC.setText(jsonResponseC);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);*/
    }
}
