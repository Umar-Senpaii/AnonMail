package com.example.AnonMail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    TextInputLayout fullName, userName, userPhone, userPassword;
    Button btnGO, btnalreadyUser;
    FirebaseAuth fAuth;
    ProgressBar pbsignup;
    TextView tvWelcome, tvContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvContinue = findViewById(R.id.tvContinue);
        fullName = findViewById(R.id.tilFullName);
        userName = findViewById(R.id.tilEmailSignup);
        userPhone = findViewById(R.id.tilPhone);
        userPassword = findViewById(R.id.tilSetPassword);
        btnGO = findViewById(R.id.btnsignup);
        btnalreadyUser = findViewById(R.id.btnAlreadyAUser);
        fAuth = FirebaseAuth.getInstance();
        pbsignup = findViewById(R.id.pbSignup);

//        if (fAuth.getCurrentUser() != null)
//        {
//            startActivity(new Intent(getApplicationContext(),Dashboard.class));
//            finish();
//        }

        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String stFullname = fullName.getEditText().getText().toString().trim();
                String stUsername = userName.getEditText().getText().toString().trim();
                String stPassword = userPassword.getEditText().getText().toString().trim();
                String stPhonenumber = userPhone.getEditText().getText().toString().trim();

                pbsignup.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(stFullname))
                {
                    fullName.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(stUsername))
                {
                    userName.setError("Username is required");
                    return;
                }
                if(TextUtils.isEmpty(stPassword))
                {
                    userPassword.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(stPhonenumber))
                {
                    userPhone.setError("Phone Number is required");
                    return;
                }

                if (stPassword.length() < 8 )
                {
                    userPassword.setError("Password Must be greater or equal to 8 Characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(stUsername,stPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LogIn.class));
                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                            pbsignup.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btnalreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    Intent intent = new Intent(SignUp.this, LogIn.class);

                    startActivity(intent);

            }
        });
    }
}