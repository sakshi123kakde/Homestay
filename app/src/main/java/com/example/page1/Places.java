package com.example.page1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class Places extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        ImageView backArrow = findViewById(R.id.backarrow); // Replace "backarrow" with the ID of your ImageView

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to perform when the back arrow is clicked
                // For example, you can finish the current activity to navigate back
                finish();
            }
        });

        // Initialize views
        TextView titleTxt = findViewById(R.id.title);
        TextView addressTxt = findViewById(R.id.adesTxt);
        TextView descriptionTxt = findViewById(R.id.desTxt);
        ImageView imageView = findViewById(R.id.bibi);

        // Retrieve data passed via intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            String address = extras.getString("address");
            String description = extras.getString("description");
            String imageUrl = extras.getString("imageUrl");

            // Set data to corresponding views
            titleTxt.setText(title);
            addressTxt.setText(address);
            descriptionTxt.setText(description);
            Glide.with(this).load(imageUrl).into(imageView); // Load image using Glide library
        }
    }
}
