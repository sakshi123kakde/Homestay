package com.example.page1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.page1.adapters.DataAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Data2 extends AppCompatActivity implements DataAdapter.OnDeleteClickListener {
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private DatabaseReference databaseReference;
    private List<DataModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data2);

        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        dataList = new ArrayList<>();
        adapter = new DataAdapter(this, dataList);
        adapter.setOnDeleteClickListener(this);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("data");
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataModel data = snapshot.getValue(DataModel.class);
                    if (data != null) {
                        String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                        if (imageUrl != null) {
                            data.setImageUrl(imageUrl);
                        }
                        dataList.add(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data2", "Database error: " + databaseError.getMessage());
                Toast.makeText(Data2.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(DataModel dataModel) {
        // Delete the record from Firebase
        deleteRecord(dataModel);
    }

    private void deleteRecord(DataModel dataModel) {
        databaseReference.orderByChild("title").equalTo(dataModel.getTitle())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                        Toast.makeText(Data2.this, "Record deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Data2", "Delete error: " + databaseError.getMessage());
                        Toast.makeText(Data2.this, "Failed to delete record.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
