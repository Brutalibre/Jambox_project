package com.val.databaseconnect_v2;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.val.databaseconnect_v2.JamActivity;

public class MainScreenActivity extends Activity {

	Button btnViewUsers;
	Button btnViewProfile;
	Button btnViewFriends;
	Button btnAddFriend;
	Button btnLogout;
    Button btnJam;
    Button btnJam2;
	
	TextView userData;
	
	// session manager : for logging out & adding friends
	SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		//Functions for the buttons
		btnViewUsers   = (Button) findViewById(R.id.btnViewUsers);
		btnViewProfile   = (Button) findViewById(R.id.btnViewProfile);
		btnViewFriends = (Button) findViewById(R.id.btnViewFriends);
		btnAddFriend = (Button) findViewById(R.id.btnAddFriend);
		btnLogout = (Button) findViewById(R.id.btnLogout);
        btnJam = (Button) findViewById(R.id.btnJam);
        btnJam2 = (Button) findViewById(R.id.btnJam2);
		
		userData = (TextView) findViewById(R.id.userData);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		
		final HashMap<String, String> user = session.getUserDetails();
		
		userData.setText(user.get(SessionManager.KEY_NAME) + " - " + user.get(SessionManager.KEY_EMAIL));
		
		// View Users click event
		btnViewUsers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launching "All users" activity
				Intent i = new Intent(getApplicationContext(), AllUsersActivity.class);
				startActivity(i);
			}
		});
		
		// View profile click event
		btnViewProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launching "All users" activity
				Intent in = new Intent(getApplicationContext(), DisplayUserActivity.class);
    			
    			in.putExtra("username", user.get(SessionManager.KEY_NAME));
    			
    			startActivity(in);
			}
		});
		
		// View friend click event
			btnViewFriends.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// Launching "All friends" activity
					Intent i = new Intent(getApplicationContext(), AllFriendsActivity.class);
					startActivity(i);
				}
			});
		
		// Add friend click event
		btnAddFriend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launching "create user" activity
				Intent i = new Intent(getApplicationContext(), AddFriendActivity.class);
				startActivity(i);
			}
		});
		
		// Logout click event
		btnLogout.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
            }
        });


        // Jam click event -> SERVER
        btnJam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launching "choose jammers" activity
//                Intent i = new Intent(getApplicationContext(), ChooseJammersActivity.class);
                // Launching Arduino Terminal activity
//                Intent i = new Intent(getApplicationContext(), ArduinoTerminalActivity.class);
                Intent i = new Intent(getApplicationContext(), JamActivity.class);
                startActivity(i);
            }
        });

        // Jam2 click event -> ARDUINO
        btnJam2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launching "choose jammers" activity
//                Intent i = new Intent(getApplicationContext(), ChooseJammersActivity.class);
                // Launching Arduino Terminal activity
                Intent i = new Intent(getApplicationContext(), JamActivity.class);
//                Intent i = new Intent(getApplicationContext(), JamActivity.class);
                startActivity(i);
            }
        });
	}
	
	
}
