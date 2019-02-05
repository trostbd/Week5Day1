package com.example.lawre.week5day1.view.mainactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lawre.week5day1.R;
import com.example.lawre.week5day1.view.messaging.Messaging;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
{
    EditText etUsername, etPassword;
    Button btSignin, btSignup;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btSignin = findViewById(R.id.btSignIn);
        btSignup = findViewById(R.id.btSignUp);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void onClick(final View view)
    {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        switch(view.getId())
        {
            case R.id.btSignIn:
                firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Log.d("TAG_", "signInWithEmail:success");
                                    firebaseUser = firebaseAuth.getCurrentUser();
                                    Intent intent = new Intent(view.getContext(), Messaging.class);
                                    intent.putExtra("user",firebaseUser);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Log.d("TAG_", "signInWithEmail:failure", task.getException());
                                }
                            }

                        });
                break;
            case R.id.btSignUp:
                firebaseAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG_", "createUserWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Intent intent = new Intent(view.getContext(), Messaging.class);
                                    intent.putExtra("user",firebaseUser);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG_", "createUserWithEmail:failure", task.getException());
                                }

                                // ...
                            }
                        });
                break;
        }
    }
}
