package com.example.AnonMail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    CardView cvSendMail,cvLogout;
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        cvSendMail = findViewById(R.id.cvSendMail);
        cvLogout = findViewById(R.id.cvLogout);
        fauth = FirebaseAuth.getInstance();


        cvSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(),OnSend.class));
            }
        });

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fauth.signOut();
                startActivity(new Intent(getApplicationContext(),LogIn.class));
                finish();
            }
        });
    }
}