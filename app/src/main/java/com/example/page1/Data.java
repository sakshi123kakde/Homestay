package com.example.page1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.page1.R;
import com.example.page1.adapters.ThirdAdapter;

public class Data extends AppCompatActivity implements ThirdAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ThirdAdapter adapter;

    // Sample data for RecyclerView
    private String[] titles = {"Add Homestay", "Available HomeStay", "Booking Request", "Add Places", "Add Most Visited"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass 'this' as the listener to the adapter constructor
        adapter = new ThirdAdapter(this, titles, this);
        recyclerView.setAdapter(adapter);
    }

    // Implement the onItemClick method from the adapter's interface
    @Override
    public void onItemClick(int position) {
        // Start the corresponding activity based on the clicked position
        switch (position) {
            case 0:
                startActivity(new Intent(Data.this, Data1.class));
                break;
            case 1:
                startActivity(new Intent(Data.this, Data2.class));
                break;
            case 2:
                startActivity(new Intent(Data.this, Hrequest.class));
                break;
            case 3:
                startActivity(new Intent(Data.this, AddPlaces.class));
                break;
            case 4:
                startActivity(new Intent(Data.this, AddMostVisited.class));
                break;
            default:
                break;
        }
    }
}
