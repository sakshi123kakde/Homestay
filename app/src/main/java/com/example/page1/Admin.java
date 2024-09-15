package com.example.page1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Objects;

public class Admin extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference adminRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ImageView backArrow = findViewById(R.id.backarrow); // Replace "backarrow" with the ID of your ImageView

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to perform when the back arrow is clicked
                // For example, you can finish the current activity to navigate back
                finish();
            }
        });
        // Initialize Firebase and the Realtime Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        adminRef = firebaseDatabase.getReference("Admin");

        // Find the UI elements
        usernameEditText = findViewById(R.id.id);
        passwordEditText = findViewById(R.id.pass);
        loginButton = findViewById(R.id.book);

        // Set up the login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = usernameEditText.getText().toString().trim();
                String enteredPassword = passwordEditText.getText().toString().trim();



                // Retrieve the admin credentials from the Realtime Database
                adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String databaseUsername = dataSnapshot.child("username").getValue(String.class);
                            String databasePassword = dataSnapshot.child("password").getValue(String.class);

                            // Compare the entered credentials with the database values
                            if (enteredUsername.equals(databaseUsername) && enteredPassword.equals(databasePassword)) {
                                // Login successful, proceed to the next activity or perform other actions
                                // Toast.makeText(Admin.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Admin.this, Data.class);
                                startActivity(intent);
                                finish();
                                // Start a new activity or perform other login-related tasks
                            } else {
                                // Login failed, display an error message
                                Toast.makeText(Admin.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // No admin credentials found in the database
                            Toast.makeText(Admin.this, "No admin credentials found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(Admin.this, "Failed to retrieve admin credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}















