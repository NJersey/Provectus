package com.example.android.provectus;

import android.util.Log;

import java.io.*;
import java.net.*;

// https://randomuser.me/api/?inc=picture,name,email,nat,login,phone,location?format=json

public class HttpConnection {

    public String makeServiceCall(String reqUrl) {
        String response = null;

        try {

            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            while ((line = reader.readLine()) != null)
                sb.append(line).append('\n');


        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}