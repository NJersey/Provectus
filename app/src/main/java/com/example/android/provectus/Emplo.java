package com.example.android.provectus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Emplo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplo);

        TextView tName = (TextView) findViewById(R.id.name);
        TextView tPhone = (TextView) findViewById(R.id.phone);
        TextView tMail = (TextView) findViewById(R.id.mail);
        TextView tLogin = (TextView) findViewById(R.id.login);
        ImageView image = (ImageView) findViewById(R.id.userImage);

        Intent intent = getIntent();

        tName.setText(intent.getStringExtra("name"));
        tPhone.setText(intent.getStringExtra("phone"));
        tMail.setText(intent.getStringExtra("mail"));
        tLogin.setText(intent.getStringExtra("login"));

        int resID = getResources().getIdentifier(intent.getStringExtra("image") , "drawable", getPackageName());
        image.setImageResource(resID);
    }
}
