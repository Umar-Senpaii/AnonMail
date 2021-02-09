package com.example.AnonMail;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    TextInputLayout tilUsername, tilPassword;
    Button btnGO, btnForgotPassword, btnNewUser;
    TextView tvWelcome, tvContinue;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvContinue = findViewById(R.id.tvContinue);
        tilUsername = findViewById(R.id.tilEmailSignup);
        tilPassword = findViewById(R.id.tilPassword);
        btnGO = findViewById(R.id.btnGO);
        btnNewUser = findViewById(R.id.btnNewUser);
        fAuth = FirebaseAuth.getInstance();
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        progressBar = findViewById(R.id.progressBar3);

        if (fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            finish();
        }
        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String username = tilUsername.getEditText().getText().toString().trim();
                String password = tilPassword.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(username))
                {
                    tilUsername.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    tilPassword.setError("Password is required");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                btnGO.setVisibility(View.GONE);
                fAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(LogIn.this, Dashboard.class));
                            finish();
                        }

                        else
                        {
                            Toast.makeText(LogIn.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogIn.this, SignUp.class);
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View,String>(tvWelcome, "transWelcome");
                pairs[1] = new Pair<View,String>(tvContinue, "transContinue");
                pairs[2] = new Pair<View,String>(tilUsername, "transUsername");
                pairs[3] = new Pair<View,String>(tilPassword, "transPassword");
                pairs[4] = new Pair<View,String>(btnGO, "transGO");
                pairs[5] = new Pair<View,String>(btnNewUser, "transOtherScreen");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LogIn.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });
        btnForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your Email");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LogIn.this, "Reset Link Sent to your Email", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(LogIn.this, "Error"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

                passwordResetDialog.create().show();


            }
        });

    }
}