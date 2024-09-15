package com.example.page1;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;


	import android.view.View;
	import android.widget.TextView;
	import android.widget.ImageView;

	import androidx.constraintlayout.widget.ConstraintLayout;

	public class splash_activity extends Activity {


		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setContentView(R.layout.splash);

			ConstraintLayout mainLayout = findViewById(R.id.splash);
			mainLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Start the next activity when clicked
					startActivity(new Intent(splash_activity.this, login_activity.class));
				}
			});


		}
	}