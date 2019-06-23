package com.example.beerbudz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private ProgressDialog loadingBar;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.tbEmail);
        password = findViewById(R.id.tbPassword);
        loadingBar = new ProgressDialog(this);

    }

    public void signUpUser(View view){

        sendUserToRegister();

    }

    private void sendUserToRegister() {

        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    /**
     * onclick method for login button
     * @param view
     */
    public void loginUser(View view){
        String _email = email.getText().toString();
        String _password = password.getText().toString();

        if(_email.isEmpty()){
            email.setError("Please provide your email address");
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
            email.setError("Invalid email address entered");
        }

        else if(_password.isEmpty()){
            email.setError("Please provide a password");
        }

        else
        {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Logging you in.....");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.signInWithEmailAndPassword(_email,_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                sendUserToHomepage();

                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Invalid Email and/or password.\nPlease click Sign Up to start new Account", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }

    }

    /**
     * method to send user to HomepageActivity
     */
    private void sendUserToHomepage() {
        Intent homeIntent = new Intent(LoginActivity.this, HomepageActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}
