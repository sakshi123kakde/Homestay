package com.example.page1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class home_info extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleTextView;
    private TextView addressTextView;
    private TextView activitiesTextView;
    private TextView facilityTextView;
    private TextView foodTextView;
    private TextView howTextView;
    private TextView localTextView;
    private TextView tariffTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_info);

        ImageView backArrow = findViewById(R.id.backarrow2); // Replace "backarrow" with the ID of your ImageView

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to perform when the back arrow is clicked
                // For example, you can finish the current activity to navigate back
                finish();
            }
        });

        Button button = findViewById(R.id.book);

        imageView = findViewById(R.id.placeimg);
        titleTextView = findViewById(R.id.toolbar_title);
        addressTextView = findViewById(R.id.destxt);
        activitiesTextView = findViewById(R.id.activityTxt);
        facilityTextView = findViewById(R.id.facilityTxt);
        foodTextView = findViewById(R.id.foodtxt);
        howTextView = findViewById(R.id.howto);
        localTextView = findViewById(R.id.Localtxt);
        tariffTextView = findViewById(R.id.tarifftxt);

        // Retrieve data from intent
        String title = getIntent().getStringExtra("title");
        String address = getIntent().getStringExtra("address");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String activities = getIntent().getStringExtra("activities");
        String facility = getIntent().getStringExtra("facility");
        String food = getIntent().getStringExtra("food");
        String how = getIntent().getStringExtra("how");
        String local = getIntent().getStringExtra("local");
        String tariff = getIntent().getStringExtra("tariff");

        // Set the data to views
        titleTextView.setText(title);
        addressTextView.setText(address);
        activitiesTextView.setText(activities);
        facilityTextView.setText(facility);
        foodTextView.setText(food);
        howTextView.setText(how);
        localTextView.setText(local);
        tariffTextView.setText(tariff);

        // Use Glide or any other image loading library to load the image
        Glide.with(this).load(imageUrl).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_info.this, Book.class);
                intent.putExtra("title", title); // Pass the title to the Book activity
                startActivity(intent);
            }
        });
    }
}
