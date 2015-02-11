package com.val.databaseconnect_v2;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	// Username & password EditTexts
	EditText txtUsername, txtPassword;
	
	// login jam_button
	Button btnLogin;
	Button btnAddUser;
	
	// Session Manager
	SessionManager session;
	
	// JSON Parser 
	JSONParser jParser = new JSONParser();
	
	// JSON array for user data
	JSONArray user = null;
	
	// url to get all the products list
	private static String url_connect_user= "http://" + Global.host + "/jambox/userConnect.php";
	
	// JSON Nodes names
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERS = "user";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		session = new SessionManager(getApplicationContext());
		
		// get inputs for username & pwd
		txtUsername = (EditText) findViewById(R.id.inputUsername);
		txtPassword = (EditText) findViewById(R.id.inputPwd);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnAddUser = (Button) findViewById(R.id.btnCreateUser);
		
		// Login jam_button click event
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get username + pwd from EditText
				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
				
				// check if fields are filled
				if(username.trim().length() > 0 && password.trim().length() > 0){
					ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

					NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

					if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {

					  boolean wifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;

					  Log.d("NetworkState", "L'interface de connexion active est du Wifi : " + wifi);
					  new CheckConnection().execute();
					}
					
					else {
						// user didn't enter username or pwd
						Toast.makeText(getApplicationContext(), "No Internet connection !", Toast.LENGTH_LONG).show();
					}
					
				}
				
				else {
					// user didn't enter username or pwd
					Toast.makeText(getApplicationContext(), "Username or password missing", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		// Create User click event
		btnAddUser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launching "new user" activity
				Intent i = new Intent(getApplicationContext(), NewUserActivity.class);
				startActivity(i);
			}
		});
	}
	
	
	/** 
     * Background Asynchronous task to load all users, by making HTTP Request
     */
    class CheckConnection extends AsyncTask<String, String, String> {
    	 
        
        /**
         * logging in
         * */
        protected String doInBackground(String... args) {
        	String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
 
            // getting JSON Object
            // Note that create user url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_connect_user,
                    "POST", params);
 
            // check log cat for response
            Log.d("Create Response", json.toString());
            
            
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully connected

            		user = json.getJSONArray(TAG_USERS);
                	JSONObject c = user.getJSONObject(0);
        			
        			String thisUsername = c.getString("username");
        			String thisEmail = c.getString("email");
        			
        			
                    // Create user in login session
					session.createLoginSession(thisUsername, thisEmail);
					
					// start MainScreenActivity
					Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
					startActivity(i);
					finish();
                } 
                
                else {
                    // failed to connect user
                	runOnUiThread(new Runnable() {
                		public void run() {
                			Toast.makeText(getApplicationContext(), "Wrong username/password combination", Toast.LENGTH_LONG).show();
                    	}
            		});
                }
            } 
            
            catch (Exception e) {
            	runOnUiThread(new Runnable() {
	        		public void run() {
	        			Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
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
            // dismiss the dialog after getting all products
//            pDialog.dismiss();
        }
    }
}