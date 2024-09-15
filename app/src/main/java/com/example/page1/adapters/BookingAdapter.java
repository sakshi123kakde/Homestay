package com.example.page1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.page1.Booking;
import com.example.page1.R;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private BookingClickListener bookingClickListener;

    public BookingAdapter(List<Booking> bookingList, BookingClickListener bookingClickListener) {
        this.bookingList = bookingList;
        this.bookingClickListener = bookingClickListener;
    }

    public interface BookingClickListener {
        void onAcceptClicked(Booking booking);
        void onRejectClicked(Booking booking);
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.titleTextView.setText("Request for " + booking.getTitle());
        holder.nameTextView.setText("Name: " + booking.getName());
        holder.emailTextView.setText("Email: " + booking.getEmail());
        holder.phoneTextView.setText("City: " + booking.getCity());
        holder.cityTextView.setText("Phone: " + booking.getPhone());
        holder.spinner1TextView.setText("No of Child: " + booking.getSpinnerData1());
        holder.spinner2TextView.setText("No of Adults: " + booking.getSpinnerData2());
        holder.spinner3TextView.setText("No of Rooms: " + booking.getSpinnerData3());

        if (booking.getCheckInDate() != null) {
            holder.checkInDateTextView.setText("Check-In: " + dateFormat.format(booking.getCheckInDate()));
        } else {
            holder.checkInDateTextView.setText("Check-In: N/A");
        }

        if (booking.getCheckOutDate() != null) {
            holder.checkOutDateTextView.setText("Check-Out: " + dateFormat.format(booking.getCheckOutDate()));
        } else {
            holder.checkOutDateTextView.setText("Check-Out: N/A");
        }

        holder.acceptButton.setOnClickListener(v -> bookingClickListener.onAcceptClicked(booking));
        holder.rejectButton.setOnClickListener(v -> bookingClickListener.onRejectClicked(booking));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, nameTextView, emailTextView, phoneTextView, cityTextView, spinner1TextView, spinner2TextView, spinner3TextView, checkInDateTextView, checkOutDateTextView;
        Button acceptButton, rejectButton;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titletxt);
            nameTextView = itemView.findViewById(R.id.nametxt);
            emailTextView = itemView.findViewById(R.id.emailtxt);
            phoneTextView = itemView.findViewById(R.id.phonetxt);
            cityTextView = itemView.findViewById(R.id.citytxt);
            spinner1TextView = itemView.findViewById(R.id.spinner1txt);
            spinner2TextView = itemView.findViewById(R.id.spinner2txt);
            spinner3TextView = itemView.findViewById(R.id.spinner3txt);
            checkInDateTextView = itemView.findViewById(R.id.checkInDatetxt);
            checkOutDateTextView = itemView.findViewById(R.id.checkOutDatetxt);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }
    }
}
