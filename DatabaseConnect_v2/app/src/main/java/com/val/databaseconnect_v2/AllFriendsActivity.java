package com.val.databaseconnect_v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class AllFriendsActivity extends ListActivity {
	// JSON Parser 
	JSONParser jParser = new JSONParser();
	
	ArrayList<HashMap<String,String>> usersList;
	
	SessionManager session;
	
	// url to get all the products list
	private static String url_all_friends= "http://" + Global.host + "/jambox/displayAllFriends.php";
	
	// JSON Nodes names
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERS = "users";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_USERNAME = "username";
    
    // users JSONArray
    JSONArray users = null;
    
    @Override
    public void onCreate (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.all_users);
    	
    	// HashMap for ListView
    	usersList = new ArrayList<HashMap<String,String>>();

		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		
    	// Loading users in background thread
    	new LoadAllFriends().execute();
    	
    	// get listview
    	ListView lv = getListView();
    	
    	// on selecting single user, launching View User screen
    	lv.setOnItemClickListener(new OnItemClickListener() {
    		
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			//getting values from selected ListItem
    			String username = ((TextView) view.findViewById(R.id.username)).getText().toString();
    			
    			Intent in = new Intent(getApplicationContext(), DisplayUserActivity.class);
    			
    			in.putExtra(TAG_USERNAME, username);
    			
    			startActivity(in);
    		}
    	});
    }
    
    /** 
     * Background Asynchronous task to load all users, by making HTTP Request
     */
    class LoadAllFriends extends AsyncTask<String, String, String> {
    	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
        	// get current username
            String cUser = session.getUserDetails().get(SessionManager.KEY_NAME);
        	
            Log.d("username", cUser);
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", cUser));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_friends, "GET", params);
            
            
            if(json != null) {
	            // Check your log cat for JSON reponse
	            Log.d("All Friends: ", json.toString());
	        
	            try {
	            	// check for SUCCESS_TAG
	            	int success = json.getInt(TAG_SUCCESS);
	            	
	            	if(success==1) {
	            		// users found, getting array of users
	            		users = json.getJSONArray(TAG_USERS);
	            		
	            		// loop through users
	            		for(int i=0; i<users.length(); i++){	            			
	            			JSONObject c = users.getJSONObject(i);
	            			
	            			String username = c.getString(TAG_USERNAME);
	            			String email = c.getString(TAG_EMAIL);
	            			
	            			// new hashmap
	            			HashMap<String, String> map = new HashMap<String, String>();
	            			
	            			// add each child node to HashMap key => value
	            			map.put(TAG_USERNAME, username);
	            			map.put(TAG_EMAIL, email);
	            			
	            			// add HashList to ArrayList
	            			usersList.add(map);
	            		}
	            	}
	            	
	            	else {
	            		// No friend found, launch Add Friend Activity
	            		Intent i = new Intent(getApplicationContext(), AddFriendActivity.class);
	            		
	            		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            		startActivity(i);
	            	}
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
            }
            return null;
        }
        
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllFriendsActivity.this, usersList,
                            R.layout.list_user, new String[] { TAG_USERNAME,
                                    TAG_EMAIL},
                            new int[] { R.id.username, R.id.email });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
        }
    }
}