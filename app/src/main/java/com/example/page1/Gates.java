package com.example.page1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.page1.adapters.CategoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gates extends AppCompatActivity implements CategoryAdapter.OnItemClickListener {

    private RecyclerView categoryRecyclerView;
    private DatabaseReference categoryRef;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gates);

        ImageView backArrow = findViewById(R.id.backarrow); // Replace "backarrow" with the ID of your ImageView

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to perform when the back arrow is clicked
                // For example, you can finish the current activity to navigate back
                finish();
            }
        });

        // Initialize the RecyclerView
        categoryRecyclerView = findViewById(R.id.category2);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initialize the list and adapter
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categoryList, this);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Reference to the Firebase node with a query to filter 'adventure' category
        categoryRef = FirebaseDatabase.getInstance().getReference("Places");
        Query adventureQuery = categoryRef.orderByChild("category").equalTo("Gates");

        adventureQuery.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    CategoryModel model = ds.getValue(CategoryModel.class);
                    categoryList.add(model);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(Gates.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(CategoryModel model) {
        Intent intent = new Intent(Gates.this, Places.class);
        intent.putExtra("title", model.getTitle());
        intent.putExtra("address", model.getAddress());
        intent.putExtra("description", model.getDescription());
        intent.putExtra("imageUrl", model.getImageUrl());
        startActivity(intent);
    }

}
