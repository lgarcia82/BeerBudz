package com.example.beerbudz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    private EditText userEmail, userPassword, userConfirmPass;
    private CheckBox userAgeConfirm;
    private ProgressDialog loadingBar;
    private TextView test;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        userEmail = (EditText)findViewById(R.id.register_email);
        userPassword = (EditText)findViewById(R.id.register_password);
        userConfirmPass = (EditText) findViewById(R.id.register_password_confirm);
        userAgeConfirm = (CheckBox) findViewById(R.id.register_checkBox);
        loadingBar = new ProgressDialog(this);

    }

    public void createNewAccount(View view) {
        final String email = userEmail.getText().toString();
        final String password = userPassword.getText().toString();
        String passConf = userConfirmPass.getText().toString();
        boolean over21 = userAgeConfirm.isChecked();

        if(email.isEmpty()){
            userEmail.setError("Please provide an email address");
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Invalid email address entered");
        }

        else if(password.isEmpty()){
            userPassword.setError("Please provide a password");
        }

        else if(passConf.isEmpty()){
            userConfirmPass.setError("Please confirm your password");
        }

        else if(!over21){
            userAgeConfirm.setError("You must confirm you are of legal age");
        }

        else if(!password.equals(passConf)) {
            Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else{

            loadingBar.setTitle("Creating new account");
            loadingBar.setMessage("Please wait, while we create your account.....");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                SendUserToCreateProfileActivity( email, password);

                                Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Error occurred: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void SendUserToCreateProfileActivity(String email, String password) {
        Intent setupIntent = new Intent(RegisterActivity.this, SetupProfileActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        setupIntent.putExtra("EMAIL", email);
        setupIntent.putExtra("PASSWORD",password);
        startActivity(setupIntent);
        finish();
    }

}
