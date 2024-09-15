package com.example.page1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.page1.adapters.MostVisitedAdapter;
import com.example.page1.adapters.SecondAdapter;
import com.example.page1.MostVisitedModel;
import com.example.page1.SecondModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home_activity extends Activity implements SecondAdapter.OnItemClickListener, MostVisitedAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

	private NavigationView navigationView;
	private DrawerLayout drawer;
	private RecyclerView secondRecyclerView;
	private SecondAdapter secondAdapter;
	private RecyclerView mostVisitedRecyclerView;
	private MostVisitedAdapter mostVisitedAdapter;
	private DatabaseReference mref, databaseReference2, mostVisitedRef;
	private List<SecondModel> dataList;
	private List<MostVisitedModel> mostVisitedList;
	private ListView listdata;
	private AutoCompleteTextView txtSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		LinearLayout adventureLayout = findViewById(R.id.adventure);
		LinearLayout gatesLayout = findViewById(R.id.gates);
		LinearLayout gardenLayout = findViewById(R.id.garden);
		LinearLayout historicalLayout = findViewById(R.id.historical);
		LinearLayout templeLayout = findViewById(R.id.temple);
		LinearLayout lakeLayout = findViewById(R.id.lake);


		mref = FirebaseDatabase.getInstance().getReference("Places");
		listdata = findViewById(R.id.listData);
		txtSearch = findViewById(R.id.search);

		ValueEventListener event = new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				populateSearch(snapshot);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				// Handle database error
			}
		};
		mref.addListenerForSingleValueEvent(event);

		// Set up the second RecyclerView
		secondRecyclerView = findViewById(R.id.secondRecyclerView);
		secondRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

		dataList = new ArrayList<>();
		secondAdapter = new SecondAdapter(this, this, dataList);
		secondRecyclerView.setAdapter(secondAdapter);

		databaseReference2 = FirebaseDatabase.getInstance().getReference().child("data");
		databaseReference2.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				dataList.clear();
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					SecondModel data = snapshot.getValue(SecondModel.class);
					String imageUrl = snapshot.child("imageUrl").getValue(String.class);
					if (data != null) {
						data.setImageUrl(imageUrl);
						dataList.add(data);
					}
				}
				secondAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				// Handle database error
			}
		});

		// Initialize the MostVisited RecyclerView
		mostVisitedRecyclerView = findViewById(R.id.recyclerView);
		mostVisitedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

		// Initialize the list and adapter for MostVisited
		mostVisitedList = new ArrayList<>();
		mostVisitedAdapter = new MostVisitedAdapter(this, mostVisitedList, this);
		mostVisitedRecyclerView.setAdapter(mostVisitedAdapter);

		// Reference to the MostVisited node in Firebase
		mostVisitedRef = FirebaseDatabase.getInstance().getReference("MostVisited");
		mostVisitedRef.addValueEventListener(new ValueEventListener() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				mostVisitedList.clear();
				for (DataSnapshot ds : snapshot.getChildren()) {
					MostVisitedModel model = ds.getValue(MostVisitedModel.class);
					mostVisitedList.add(model);
				}
				mostVisitedAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				// Handle database error
				Toast.makeText(home_activity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});

		// Navigation setup
		drawer = findViewById(R.id.drawer_layout);
		navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


		adventureLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(home_activity.this, Adventure.class); // replace AdventureActivity with your target activity
				startActivity(intent);
			}
		});

		gatesLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(home_activity.this, Gates.class); // replace AdventureActivity with your target activity
				startActivity(intent);
			}
		});

		gardenLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(home_activity.this, Garden.class); // replace AdventureActivity with your target activity
				startActivity(intent);
			}
		});

		historicalLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(home_activity.this, Historical.class); // replace AdventureActivity with your target activity
				startActivity(intent);
			}
		});

		templeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(home_activity.this, Temple.class); // replace AdventureActivity with your target activity
				startActivity(intent);
			}
		});

		lakeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(home_activity.this, Lakes.class); // replace AdventureActivity with your target activity
				startActivity(intent);
			}
		});
	}

	private void populateSearch(DataSnapshot snapshot) {
		ArrayList<String> names = new ArrayList<>();
		if (snapshot.exists()) {
			for (DataSnapshot ds : snapshot.getChildren()) {
				String name = ds.child("title").getValue(String.class);
				names.add(name);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
			txtSearch.setAdapter(adapter);
			txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					String name = txtSearch.getText().toString();
					searchUser(name);
				}
			});
		} else {
			Log.d("users", "No data found");
		}
	}

	private void searchUser(String title) {
		Query query = mref.orderByChild("title").equalTo(title);
		query.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()) {
					for (DataSnapshot ds : snapshot.getChildren()) {
						String title = ds.child("title").getValue(String.class);
						String address = ds.child("address").getValue(String.class);
						String imageUrl = ds.child("imageUrl").getValue(String.class);
						String description = ds.child("description").getValue(String.class); // Retrieve description

						Intent intent = new Intent(home_activity.this, Places.class);
						intent.putExtra("title", title);
						intent.putExtra("address", address);
						intent.putExtra("imageUrl", imageUrl);
						intent.putExtra("description", description); // Pass description
						startActivity(intent);
					}
				} else {
					Log.d("users", "No data found");
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				Toast.makeText(home_activity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}


	@Override
	public void onSecondItemClick(int position) {
		SecondModel selectedModel = dataList.get(position);

		Intent intent = new Intent(home_activity.this, home_info.class);
		intent.putExtra("title", selectedModel.getTitle());
		intent.putExtra("address", selectedModel.getAddress());
		intent.putExtra("imageUrl", selectedModel.getImageUrl());
		intent.putExtra("activities", selectedModel.getActivities());
		intent.putExtra("facility", selectedModel.getFacility());
		intent.putExtra("food", selectedModel.getFood());
		intent.putExtra("how", selectedModel.getHow());
		intent.putExtra("local", selectedModel.getLocal());
		intent.putExtra("tariff", selectedModel.getTariff());

		startActivity(intent);
	}

	@Override
	public void onItemClick(MostVisitedModel model) {
		Intent intent = new Intent(home_activity.this, Places.class);
		intent.putExtra("title", model.getTitle());
		intent.putExtra("address", model.getAddress());
		intent.putExtra("imageUrl", model.getImageUrl());
		intent.putExtra("description", model.getHow());
		startActivity(intent);
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.nav_sign_out) {
			signOut();
			return true;
		}
		else if (item.getItemId() == R.id.notification) {
			startActivity(new Intent(home_activity.this, Notification.class));
			return true;
		}
		else if (item.getItemId() == R.id.about) {
			startActivity(new Intent(home_activity.this, About.class));
			return true;
		}
		else if (item.getItemId() == R.id.admin) {
			startActivity(new Intent(home_activity.this, Admin.class));
			return true;
		}

		return false;
	}

	private void signOut() {
		FirebaseAuth.getInstance().signOut();
		Intent intent = new Intent(home_activity.this, splash_activity.class);
		startActivity(intent);
		finish(); // Optional, to ensure the user can't navigate back to the home_activity
	}

	public void toggleNavigationDrawer(View view) {
		if (drawer.isDrawerOpen(GravityCompat.END)) {
			drawer.closeDrawer(GravityCompat.END);
		} else {

			drawer.openDrawer(GravityCompat.END);
		}
	}
}


class User {
	public User(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return description;
	}

	public String name;
	public String description;

}








