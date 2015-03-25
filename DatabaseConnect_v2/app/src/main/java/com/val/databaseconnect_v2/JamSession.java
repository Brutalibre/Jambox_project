package com.val.databaseconnect_v2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Val on 19/02/2015.
 */
public class JamSession {

    public String url_check_online = "http://" + Global.host + "/displayAllFriends.php";
    public HashMap<String, Integer> users;

    public JamSession(HashMap<String, Integer> users){
        this.users = users;
    }

    public void add(String username){
        this.users.put(username, 50);
    }

    public void setVolume(String username, int volume){
        this.users.put(username, volume);
    }

    public boolean sendRequest(String username){
        // functions to send a Jam request to the other user


        return true;
    }

//    public ArrayList<Boolean> checkAllOnline(){
//        ArrayList<Boolean> areOnline = new ArrayList<>();
//        for(int i=0; i<users.size(); i++){
//            areOnline.add(checkOnline(users.get(i)));
//        }
//
//        return areOnline;
//    }
//
//    public boolean checkOnline(String username){
//        return true;
//    }
}
