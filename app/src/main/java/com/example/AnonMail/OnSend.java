package com.example.AnonMail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class OnSend extends AppCompatActivity {

    Button btn_send;
    TextInputLayout fromEm, fromNa, toEm, emsub, emMessage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_send);
        fromEm = findViewById(R.id.fromEmail);
        fromNa = findViewById(R.id.fromName);
        toEm = findViewById(R.id.toEmail);
        emsub = findViewById(R.id.subject);
        emMessage = findViewById(R.id.messagebody);
        btn_send = findViewById(R.id.btnsignup);
        progressBar = findViewById(R.id.progressBar);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                progressBar.setVisibility(View.VISIBLE);
                btn_send.setVisibility(View.GONE);
                sendmail(fromNa.getEditText().getText().toString().trim(), fromEm.getEditText().getText().toString().trim(),
                        toEm.getEditText().getText().toString().trim(), emsub.getEditText().getText().toString().trim(),
                        emMessage.getEditText().getText().toString().trim());
            }

        });
    }

    public void sendmail(String fromName, String fromEmail, String toEmail, String subject, String message)
    {
        try
        {
            SendMail sm = new SendMail(fromName, fromEmail,toEmail, subject,  message);
            sm.execute();
            Toast.makeText(OnSend.this, "Email Sent Successfully", Toast.LENGTH_SHORT).show();
            finish();

        }
        catch (Exception e)
        {

        }

    }
}