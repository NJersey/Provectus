package com.example.android.provectus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Emplo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplo);

        TextView tName = (TextView) findViewById(R.id.name);
        TextView tPhone = (TextView) findViewById(R.id.phone);
        TextView tMail = (TextView) findViewById(R.id.mail);
        TextView tLogin = (TextView) findViewById(R.id.login);
        ImageView iView = (ImageView) findViewById(R.id.userImage);

        Intent intent = getIntent();

        tName.setText(intent.getStringExtra("name"));
        tPhone.setText(intent.getStringExtra("phone"));
        tMail.setText(intent.getStringExtra("email"));
        tLogin.setText(intent.getStringExtra("username"));

        Picasso.with(this)
                .load(intent.getStringExtra("image"))
                //.resize()
                .into(iView);

    }
}
