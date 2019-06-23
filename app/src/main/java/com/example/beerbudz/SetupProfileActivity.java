package com.example.beerbudz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupProfileActivity extends AppCompatActivity {

    private EditText userName, firstName, lastName;
    private Spinner listCities;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    String userId, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
        email = mAuth.getCurrentUser().getEmail();

        userName = findViewById(R.id.edit_username);
        firstName = findViewById(R.id.edit_first_name);
        lastName = findViewById(R.id.edit_last_name);
        loadingBar = new ProgressDialog(this);

        listCities = (Spinner) findViewById(R.id.edit_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listCities.setAdapter(adapter);


    }

    public void createProfile(View view){

        String un = userName.getText().toString();
        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();
        String cn = listCities.getSelectedItem().toString();

        if(fn.isEmpty()){
            firstName.setError("Please provide your first name");
        }
        else if(ln.isEmpty()){
            lastName.setError("Please provide your last name");
        }
        else if(un.isEmpty()){
            userName.setError("Please provide a userName");
        }
        else if(listCities.getSelectedItem().toString().trim().equals("Choose City")){
            listCities.setPrompt("Please select your city");
        }
        else{
            loadingBar.setTitle("Profile");
            loadingBar.setMessage("Saving your Profile...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();

            final User user = new User(un, fn, ln, cn, email);

            userMap.put("userName", un);
            userMap.put("firstName", fn);
            userMap.put("lastName", ln);
            userMap.put("city", cn);
            userMap.put("email", email);
            userRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){

                        Toast.makeText(SetupProfileActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
                        sendUserToHome(user);
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(SetupProfileActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }
    }
    private void sendUserToHome(User user) {
        Intent homeIntent = new Intent(SetupProfileActivity.this, HomepageActivity.class);
        homeIntent.putExtra("USER", user);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}
