///* Copyright (C) 2011 Circuits At Home, LTD. All rights reserved.
//
//This software may be distributed and modified under the terms of the GNU
//General Public License version 2 (GPL2) as published by the Free Software
//Foundation and appearing in the file GPL2.TXT included in the packaging of
//this file. Please note that GPL2 Section 2[b] requires that all works based
//on this software must also be made publicly available under the terms of
//the GPL2 ("Copyleft").
//
//Contact information
//-------------------
//
//Circuits At Home, LTD
//Web      :  http://www.circuitsathome.com
//e-mail   :  support@circuitsathome.com
//*/
//package com.val.databaseconnect_v2.Arduino_connect.usb;
//
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
//import android.os.Bundle;
//import android.preference.CheckBoxPreference;
//import android.preference.EditTextPreference;
//import android.preference.ListPreference;
//import android.preference.Preference;
//import android.preference.PreferenceActivity;
//import android.preference.PreferenceManager;
//import android.util.Log;
//
//import com.val.databaseconnect_v2.R;
//
///**
// * Preferences class that handles user menus.
// */
//public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {
//    private static final String TAG 		= "Preferences";
//    private static final String DEF_VALUE 	= "Not Set";
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.preferences);
//    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        CharSequence value = DEF_VALUE;
//        Preference pref = findPreference(key);
//        updatePrefSummary(key, pref);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Setup the initial values
//        setAllSummaries();
//
//        // Set up a listener whenever a key changes
//        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // Unregister the listener whenever a key changes
//        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
//    }
//
//    private void setAllSummaries() {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Map<String, ?> map = sharedPreferences.getAll();
//        Set<String> keySet = map.keySet();
//        Iterator<String> it = keySet.iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            Preference pref = findPreference(key);
//            if (pref != null) {
//                updatePrefSummary(key, pref);
//            }
//        }
//    }
//
//    private void updatePrefSummary(String key, Preference p){
//        CharSequence value 		= DEF_VALUE;
//        CharSequence summary	= getSummaryForKey(key);
//
//        if (p instanceof ListPreference) {
//            ListPreference listPref = (ListPreference) p;
//            value = listPref.getEntry();
//        } else if (p instanceof CheckBoxPreference) {
//            // Do not show value for CheckBoxPreference
//            //CheckBoxPreference checkBoxPref = (CheckBoxPreference) p;
//            //boolean bo = checkBoxPref.isChecked();
//            //value = (bo) ? "True" : "False";
//            value = null;
//        } else if (p instanceof EditTextPreference) {
//            EditTextPreference editTextPref = (EditTextPreference) p;
//            value = editTextPref.getText();
//        }
//
//        // Do not show value if it is NULL
//        if (value == null) {
//            p.setSummary(summary);
//        } else {
//            p.setSummary(summary + ": " + value);
//        }
//    }
//
//    private String getSummaryForKey(String key) {
//        String summary = null;
//
//
//        return summary;
//    }
//}
