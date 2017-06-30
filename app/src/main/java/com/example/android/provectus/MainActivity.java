package com.example.android.provectus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.random_list);

        new GetList().execute();
    }

    private class GetList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Retrieving contacts", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            final String url = "https://randomuser.me/api/?results=10&inc=picture,name,email,login,phone&format=json";

            HttpConnection http = new HttpConnection();
            String jsonStr = http.makeServiceCall(url);

            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("results");

                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject c = contacts.getJSONObject(i);

                        JSONObject name = c.getJSONObject("name");
                        String firstName = name.getString("first");
                        String lastName = name.getString("last");

                        String mail = c.getString("email");

                        JSONObject pic = c.getJSONObject("picture");
                        String large = pic.getString("large");
                        String image = pic.getString("medium");
                        String thumbnail = pic.getString("thumbnail");

                        JSONObject login = c.getJSONObject("login");
                        String username = login.getString("username");

                        String phone = c.getString("phone");


                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("name", (firstName + " " + lastName));
                        contact.put("email", mail);
                        contact.put("image", image);
                        contact.put("thumbnail", thumbnail);
                        contact.put("large", large);
                        contact.put("phone", phone);
                        contact.put("username", username);

                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Retrying..", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter
                    (
                            MainActivity.this, contactList, R.layout.list,
                            new String[]{"name", "email"}, new int[]{R.id.name, R.id.mail}
                    );

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, Emplo.class);

                    intent.putExtra("name", contactList.get(position).get("name"));
                    intent.putExtra("email", contactList.get(position).get("email"));
                    intent.putExtra("image", contactList.get(position).get("large"));
                    intent.putExtra("phone", contactList.get(position).get("phone"));
                    intent.putExtra("username", contactList.get(position).get("username"));

                    startActivity(intent);
                }
            });
            lv.setAdapter(adapter);
        }
    }
}