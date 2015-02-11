package com.val.databaseconnect_v2;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendActivity extends Activity {
	JSONParser jsonParser = new JSONParser();
	
	EditText inputUsername;
	EditText inputEmail;
	
	SessionManager session;
	
	// url to add a new friend
	private static String url_add_friend = "http://" + Global.host + "/jambox/addFriend.php";
	
	// JSON node names
	private static final String TAG_SUCCESS = "success";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friend);
		
		// EditText
		inputUsername = (EditText) findViewById(R.id.inputUsername);
		inputEmail = (EditText) findViewById(R.id.inputEmail);
		
		// session
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		
		// create Button
		Button btnAddFriend= (Button) findViewById(R.id.btnCreateUser);
		
		// jam_button click event
		btnAddFriend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// create new user in bg thread
				new AddFriend().execute();
				
			}
		});
	}
	/**
     * Background Async Task to Create new product
     * */
    class AddFriend extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(NewUserActivity.this);
//            pDialog.setMessage("Creating User..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }
        
        /**
         * Creating user
         * */
        protected String doInBackground(String... args) {
            String username = inputUsername.getText().toString();
            String email = inputEmail.getText().toString();
            String cUsername = session.getUserDetails().get(SessionManager.KEY_NAME);
            
            Log.d("debug", cUsername);
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("currentUser", cUsername));
 
            // getting JSON Object
            // Note that create user url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_friend,
                    "POST", params);
 
            // check log cat for response
            Log.d("Create Response", json.toString());
            
            
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created user
                    Intent i = new Intent(getApplicationContext(), AllFriendsActivity.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                } 
                
                else if (json.getString("message") == "Yourself"){
                    // failed to create user
                	runOnUiThread(new Runnable() {
                		public void run() {

                			Toast toast = Toast.makeText(getApplicationContext(), "Cannot be friends with yourself !", Toast.LENGTH_LONG);
                        	toast.show();
                    	}
            		});
                }
                
                else {
                	// failed to create user
                	
                	// this displays a Toast on User Interface
                	runOnUiThread(new Runnable() {
                		public void run() {

                			Toast toast = Toast.makeText(getApplicationContext(), "An error occured... Please try again.", Toast.LENGTH_LONG);
                        	toast.show();
                    	}
            		});
                }
            } 
            
            catch (Exception e) {
            	runOnUiThread(new Runnable() {
            		public void run() {

            			Toast toast = Toast.makeText(getApplicationContext(), "An error occured... Please try again.", Toast.LENGTH_LONG);
                    	toast.show();
                	}
        		});
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
//            pDialog.dismiss();
        }
 
    }
}

