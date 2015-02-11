package com.val.databaseconnect_v2;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
//import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class DisplayUserActivity extends Activity {

	TextView username;
	TextView email;
	
	String displayUsername;
	String displayEmail;
	
	String uName;
	
	// progress dialog
//	private ProgressDialog pDialog;
	
	JSONParser jsonParser = new JSONParser();
	
	private static final String url_get_user_details = "http://" + Global.host + "/jambox/displayUserProfile.php";
	
	// JSON node names
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "user";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_USERNAME = "username";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_user);
		
		// getting user details from intent
        Intent i = getIntent();
 
        // getting user id (pid) from intent
        uName = i.getStringExtra(TAG_USERNAME);
 
        // Getting complete user details in background thread
        new GetUserDetails().execute();
	}
	
	/**
     * Background Async Task to Get complete user details
     * */
    class GetUserDetails extends AsyncTask<String, String, String> {
        
        /**
         * Getting user details in background thread
         * */
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
//            runOnUiThread(new Runnable() {
//                public void run() {
                    // Check for success tag
                    int success;
                    
                    try {
                        // Building Parameters
                        List<NameValuePair> param = new ArrayList<NameValuePair>();
                        param.add(new BasicNameValuePair("username", uName));
 
                        // getting user details by making HTTP request
                        // Note that user details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                        		url_get_user_details, "GET", param);
                        
                        // check your log for json response
                        Log.d("Single User Details", json.toString());
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        
                        if (success == 1) {
                            // successfully received user's details
                            JSONArray userObj = json
                                    .getJSONArray(TAG_USER); // JSON Array
 
                            // get first user object from JSON Array
                            JSONObject user = userObj.getJSONObject(0);
                            displayUsername = user.getString(TAG_USERNAME);
                            displayEmail = user.getString(TAG_EMAIL);
                            
                        }
                        
                        else{
                            // user with username not found
                        }
                    }
                    
                    catch(Exception e){
                    	e.printStackTrace();
                    }
//                }
//            });
            
            return null;
        }
        
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
        	// user with this username found
            // Edit Text
            username = (TextView) findViewById(R.id.username);
            email = (TextView) findViewById(R.id.email);

            // display user data in EditText
            username.setText(displayUsername);
            email.setText(displayEmail);
        }
    }        
}
