package com.example.android.provectus;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;

    int recursion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById (R.id.random_list);



        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText (MainActivity.this,"bitch wait",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            if (recursion < 8) {

            final String url = "https://randomuser.me/api/?inc=picture,name,email,nat,login,phone?format=json";

            HttpConnection http = new HttpConnection();

            // Making a request to url and getting response

            String jsonStr = http.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("results");

                    JSONObject c = contacts.getJSONObject(0);

                    JSONObject name = c.getJSONObject("name");
                    String firstName = name.getString("first");
                    String lastName = name.getString("last");

                    String mail = c.getString("email");

                    //String mobile = c.getJSONObject("phone").getString("phone");
//                        String mobile = phone.getString("phone");

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("name", (firstName + " " + lastName));
                    contact.put("email", mail);

                    // adding contact to contact list
                    contactList.add(contact);


                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } else {
//                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            recursion++;
                doInBackground();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter
                    (
                            MainActivity.this, contactList, R.layout.list,
                            new String[]{ "name","email"}, new int[]{R.id.name, R.id.mail}
                    );

            lv.setAdapter(adapter);
        }
    }
}
