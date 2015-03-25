package com.val.databaseconnect_v2;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
//import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends Activity {
//	private ProgressDialog pDialog;
	
	JSONParser jsonParser = new JSONParser();
	EditText inputUsername;
	EditText inputEmail;
	EditText inputPassword;
	
	// url to create new user
	private static String url_create_user = "http://" + Global.host + "/createUser.php";
	
	// JSON node names
	private static final String TAG_SUCCESS = "success";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user);
		
		// EditText
		inputUsername = (EditText) findViewById(R.id.inputUsername);
		inputEmail = (EditText) findViewById(R.id.inputEmail);
		inputPassword = (EditText) findViewById(R.id.inputPwd);
		
		// create Button
		Button btnCreateUser = (Button) findViewById(R.id.btnCreateUser);
		
		// jam_button click event
		btnCreateUser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// create new user in bg thread
				new CreateNewUser().execute();
				
			}
		});
	}
	
	/**
     * Background Async Task to Create new product
     * */
    class CreateNewUser extends AsyncTask<String, String, String> {
 
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
            String password = inputPassword.getText().toString();
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("email", email));
 
            // getting JSON Object
            // Note that create user url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "POST", params);
 
            // check log cat for response
            Log.d("Create Response", json.toString());
            
            
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created user
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                } 
                
                else if (json.getInt("message") == 1062){
                    // failed to create user
                	runOnUiThread(new Runnable() {
                		public void run() {

                			Toast toast = Toast.makeText(getApplicationContext(), "Username or email address already existing. Please choose another.", Toast.LENGTH_LONG);
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
