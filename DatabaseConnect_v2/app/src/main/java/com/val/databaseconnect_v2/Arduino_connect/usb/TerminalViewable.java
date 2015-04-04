/* Copyright (C) 2011 Circuits At Home, LTD. All rights reserved.

This software may be distributed and modified under the terms of the GNU
General Public License version 2 (GPL2) as published by the Free Software
Foundation and appearing in the file GPL2.TXT included in the packaging of
this file. Please note that GPL2 Section 2[b] requires that all works based
on this software must also be made publicly available under the terms of
the GPL2 ("Copyleft").

Contact information
-------------------

Circuits At Home, LTD
Web      :  http://www.circuitsathome.com
e-mail   :  support@circuitsathome.com
*/
package com.val.databaseconnect_v2.Arduino_connect.usb;

import com.val.databaseconnect_v2.Arduino_connect.usb.interfaces.Connectable;
import com.val.databaseconnect_v2.Arduino_connect.usb.interfaces.Viewable;
import com.val.databaseconnect_v2.JamActivity;
import com.val.databaseconnect_v2.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/**
* Implementation of Viewable interface that emulates Terminal.
* Delegates all Activity-specific operations to the Activity.
*/
public class TerminalViewable implements Viewable {

    private static final String TAG = "TerminalViewable";

    private Activity activity_;
    private Button quitButton;

    /** Called when the activity is first created. */
    @Override
    public void setActivity(Activity activity) {
        if (activity_ == activity) {
            return;
        }

        if (activity_ != null) {
            saveState();
        }

        activity_ = activity;
        activity_.setContentView(R.layout.jam);

        quitButton  = (Button) activity_.findViewById(R.id.quitButton);

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launching "choose jammers" activity
//                Intent i = new Intent(getApplicationContext(), ChooseJammersActivity.class);
                // Launching Arduino Terminal activity
//                Intent i = new Intent(getApplicationContext(), ArduinoTerminalActivity.class);
                activity_.finish();
            }
        });
    }

    public void signalToUi(int type, Object data) {
        Runnable runnable = null;
        if (type == Viewable.CONNECTION_ACTION) {
            if (activity_ instanceof UsbActivity) {
                runnable = new Runnable() {
                    public void run() {
                        ((UsbActivity) activity_).onConnectMenu();
                    }
                };
            }
        }else if (type == Viewable.DISABLE_CONTROLS_TYPE) {
            runnable = new Runnable() {
                public void run() {
                    enableUiControls(false);
                }
            };
        } else if (type == Viewable.CHAR_SEQUENCE_TYPE) {
            if (data == null || ((CharSequence) data).length() == 0) {
                return;
            }
            final CharSequence tmpData = (CharSequence) data;
            runnable = new Runnable() {
                public void run() {
                    addToHistory(tmpData);
                }
            };
        }  else if (type == Viewable.BYTE_SEQUENCE_TYPE) {
            if (data == null || ((byte[]) data).length == 0) {
                return;
            }
            final byte[] byteArray = (byte[]) data;
            runnable = new Runnable() {
                public void run() {
                    addToHistory(byteArray);
                }
            };
        }

        if (runnable != null) {
            activity_.runOnUiThread(runnable);
        }

    }

    @Override
    public void saveState() {

    }

    @Override
    public void readState() {

    }

    private void addToHistory(CharSequence text) {

    }

    private void addToHistory(byte[] data) {

    }

    private void enableUiControls(boolean enable) {
    }

    void scrollDown1() {
    }

    void scrollDownWithDelay(int delay) {
    }

    void scrollDown2() {
    }

    public void close() {
        // Do nothing here for now
    }

}