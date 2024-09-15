package com.example.page1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.page1.adapters.BookingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Hrequest extends AppCompatActivity implements BookingAdapter.BookingClickListener {

    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;
    private List<Booking> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsrequest);

        ImageView backArrow = findViewById(R.id.backarrow2); // Replace "backarrow" with the ID of your ImageView

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to perform when the back arrow is clicked
                // For example, you can finish the current activity to navigate back
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(bookingList, this);
        recyclerView.setAdapter(bookingAdapter);

        fetchBookings();
    }

    private void fetchBookings() {
        FirebaseDatabase.getInstance().getReference("Booking")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bookingList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Booking booking = snapshot.getValue(Booking.class);
                            if (booking != null) {
                                booking.setId(snapshot.getKey()); // Set the booking ID
                                bookingList.add(booking);
                            }
                        }
                        bookingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("BookingListActivity", "Failed to read bookings", databaseError.toException());
                    }
                });
    }

    @Override
    public void onAcceptClicked(Booking booking) {
        // Retrieve the Google ID of the user who made the booking
        String userGoogleId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Update the booking status to "accepted" and set the userId
        acceptBooking(booking, userGoogleId);
    }

    @Override
    public void onRejectClicked(Booking booking) {
        // Retrieve the Google ID of the user who made the booking
        String userGoogleId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Update the booking status to "rejected" and set the userId
        rejectBooking(booking, userGoogleId);
    }

    private void acceptBooking(Booking booking, String userId) {
        // Perform any additional logic if needed
        updateBookingStatusAndUserId(booking.getId(), "accepted", userId);
    }

    private void rejectBooking(Booking booking, String userId) {
        // Perform any additional logic if needed
        updateBookingStatusAndUserId(booking.getId(), "rejected", userId);
    }

    private void updateBookingStatusAndUserId(String bookingId, String status, String userId) {
        DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference("Booking").child(bookingId);
        bookingRef.child("status").setValue(status); // Update status
        bookingRef.child("userId").setValue(userId); // Update userId
    }
}