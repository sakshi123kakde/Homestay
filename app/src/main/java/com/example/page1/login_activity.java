
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		login
	 *	@date 		Thursday 11th of April 2024 05:32:51 AM
	 *	@title 		Page 1
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package com.example.page1;

	import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.TextView;
	import android.widget.Toast;

	import androidx.annotation.NonNull;
	import androidx.annotation.Nullable;

	import com.google.android.gms.auth.api.signin.GoogleSignIn;
	import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
	import com.google.android.gms.auth.api.signin.GoogleSignInClient;
	import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
	import com.google.android.gms.common.api.ApiException;
	import com.google.android.gms.tasks.OnCompleteListener;
	import com.google.android.gms.tasks.Task;
	import com.google.firebase.auth.AuthCredential;
	import com.google.firebase.auth.AuthResult;
	import com.google.firebase.auth.FirebaseAuth;
	import com.google.firebase.auth.FirebaseUser;
	import com.google.firebase.auth.GoogleAuthProvider;

public class login_activity extends Activity {
	private TextView textView;
	private GoogleSignInClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		textView=findViewById(R.id.signInWithGoogle);
		GoogleSignInOptions options= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.build();
		client= GoogleSignIn.getClient(this, options);

		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=client.getSignInIntent();
				startActivityForResult(i,1234);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1234)
		{
			Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				GoogleSignInAccount account=task.getResult(ApiException.class);
				AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
				FirebaseAuth.getInstance().signInWithCredential(credential)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if(task.isSuccessful())
								{
									Intent intent =new Intent(getApplicationContext(),home_activity.class);
									startActivity(intent);
								}
								else {
									Toast.makeText(login_activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
								}
							}
						});
			} catch (ApiException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
		if(user!= null)
		{
			Intent intent=new Intent(this,home_activity.class);
			startActivity(intent);
		}
	}
}

	



		
		
		//custom code goes here
	


	
	