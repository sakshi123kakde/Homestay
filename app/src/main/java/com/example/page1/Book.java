package com.example.page1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Book extends AppCompatActivity {
    private Spinner dropdownSpinner1, dropdownSpinner2, dropdownSpinner3;
    private EditText checkInDateEditText, checkOutDateEditText, txtname, txtemail, txtmobile, txtcity;
    private Calendar checkInDateCalendar = Calendar.getInstance();
    private Calendar checkOutDateCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Button btn;

    DatabaseReference mref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        ImageView backArrow = findViewById(R.id.backarrow2); // Replace "backarrow" with the ID of your ImageView

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to perform when the back arrow is clicked
                // For example, you can finish the current activity to navigate back
                finish();
            }
        });

        txtname = findViewById(R.id.name);
        txtemail = findViewById(R.id.email);
        txtcity = findViewById(R.id.city);
        txtmobile = findViewById(R.id.mobile);
        dropdownSpinner1 = findViewById(R.id.dropdown_spinner);
        dropdownSpinner2 = findViewById(R.id.dropdown2_spinner);
        dropdownSpinner3 = findViewById(R.id.dropdown3_spinner);
        checkInDateEditText = findViewById(R.id.checkInDate);
        checkOutDateEditText = findViewById(R.id.checkOutDate);
        btn = findViewById(R.id.book);

        // Retrieve the title from the intent
        String title = getIntent().getStringExtra("title");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    createBooking(title);
                }
            }
        });



        checkInDateEditText = findViewById(R.id.checkInDate);
        checkOutDateEditText = findViewById(R.id.checkOutDate);

        // Initially disable checkout date field
        checkOutDateEditText.setEnabled(false);

        // Set OnClickListener for check-in date EditText
        checkInDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(checkInDateEditText, checkInDateCalendar);
            }
        });

        // Set OnClickListener for check-out date EditText
        checkOutDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(checkOutDateEditText, checkOutDateCalendar);
            }
        });
    }

    private boolean validateInputs() {
        String name = txtname.getText().toString().trim();
        String email = txtemail.getText().toString().trim();
        String mobile = txtmobile.getText().toString().trim();
        String city = txtcity.getText().toString().trim();
        String checkInDate = checkInDateEditText.getText().toString().trim();
        String checkOutDate = checkOutDateEditText.getText().toString().trim();
        String spinner1Value = dropdownSpinner1.getSelectedItem().toString();
        String spinner2Value = dropdownSpinner2.getSelectedItem().toString();
        String spinner3Value = dropdownSpinner3.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)) {
            txtname.setError("Name is required");
            txtname.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtemail.setError("Valid email is required");
            txtemail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mobile) || !mobile.matches("^[0-9]{10}$")) {
            txtmobile.setError("Valid 10-digit mobile number is required");
            txtmobile.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(city)) {
            txtcity.setError("City is required");
            txtcity.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(checkInDate)) {
            checkInDateEditText.setError("Check-in date is required");
            checkInDateEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(checkOutDate)) {
            checkOutDateEditText.setError("Check-out date is required");
            checkOutDateEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(spinner1Value)) {
            Toast.makeText(this, "Please select a value from the first spinner", Toast.LENGTH_SHORT).show();
            dropdownSpinner1.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(spinner2Value)) {
            Toast.makeText(this, "Please select a value from the second spinner", Toast.LENGTH_SHORT).show();
            dropdownSpinner2.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(spinner3Value)) {
            Toast.makeText(this, "Please select a value from the third spinner", Toast.LENGTH_SHORT).show();
            dropdownSpinner3.requestFocus();
            return false;
        }

        return true;
    }

    private void createBooking(String title) {
        String name = txtname.getText().toString();
        String email = txtemail.getText().toString();
        String city = txtcity.getText().toString();
        String mobile = txtmobile.getText().toString();
        String selectedItemSpinner1 = dropdownSpinner1.getSelectedItem().toString();
        String selectedItemSpinner2 = dropdownSpinner2.getSelectedItem().toString();
        String selectedItemSpinner3 = dropdownSpinner3.getSelectedItem().toString();

        // Retrieve text from Check-in Date EditText
        String checkInDateString = checkInDateEditText.getText().toString();
        // Retrieve text from Check-out Date EditText
        String checkOutDateString = checkOutDateEditText.getText().toString();
        Date checkInDate = parseDate(checkInDateString);
        Date checkOutDate = parseDate(checkOutDateString);

        // Get user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a Booking object with all necessary data including the title
        Booking book = new Booking(name, email, city, mobile, selectedItemSpinner1, selectedItemSpinner2, selectedItemSpinner3, checkInDate, checkOutDate, title, userId, "pending");
        mref = FirebaseDatabase.getInstance().getReference("Booking");

        String BookId = mref.push().getKey();
        mref.child(BookId).setValue(book);

        Toast.makeText(this, "Book saved", Toast.LENGTH_SHORT).show();
    }

    class Booking {
        public String name, email, phone, city, spinnerData1, spinnerData2, spinnerData3, title;
        public Date checkInDate, checkOutDate;

        public Booking(String name, String email, String phone, String city, String spinnerData1, String spinnerData2, String spinnerData3, Date checkInDate, Date checkOutDate, String title, String userId, String pending) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.city = city;
            this.spinnerData1 = spinnerData1;
            this.spinnerData2 = spinnerData2;
            this.spinnerData3 = spinnerData3;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.title = title;
        }

        public Booking() {
            // Default constructor required for calls to DataSnapshot.getValue(Booking.class)
        }
    }


    private Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error appropriately
        }
    }

    private void showDatePicker(final EditText editText, final Calendar selectedDateCalendar) {
        int year = selectedDateCalendar.get(Calendar.YEAR);
        int month = selectedDateCalendar.get(Calendar.MONTH);
        int dayOfMonth = selectedDateCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    selectedDateCalendar.set(Calendar.YEAR, year1);
                    selectedDateCalendar.set(Calendar.MONTH, monthOfYear);
                    selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth1);

                    String selectedDate = dateFormat.format(selectedDateCalendar.getTime());
                    editText.setText(selectedDate);

                    // If check-in date is selected, enable checkout date field
                    if (selectedDateCalendar == checkInDateCalendar) {
                        checkOutDateEditText.setEnabled(true);
                    }
                },
                year, month, dayOfMonth);

        // Set minimum date to current date for check-in date,
        // and set minimum date to the selected check-in date for check-out date
        if (selectedDateCalendar == checkOutDateCalendar) {
            datePickerDialog.getDatePicker().setMinDate(checkInDateCalendar.getTimeInMillis() + 86400000); // Set minimum date to the day after check-in date
        } else {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set minimum date to current date
        }

        datePickerDialog.show();
    }
}
