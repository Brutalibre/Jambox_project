package com.val.databaseconnect_v2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.val.databaseconnect_v2.Arduino_connect.usb.TerminalViewable;
import com.val.databaseconnect_v2.Arduino_connect.usb.UsbActivity;
import com.val.databaseconnect_v2.Arduino_connect.usb.interfaces.Viewable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/*
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
*/

/**
 * Created by Val on 19/02/2015.
 */
/*public class JamActivity extends Activity {

   *//* public void onCreate(View v) {
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            ThreadClient threadClient = new ThreadClient(50005, addr);
            threadClient.start();
        }
        catch(UnknownHostException e){
            Log.d("Host not found", "Host not found");
        }

    }*//*
    public byte[] buffer;
    private int port;
    //static AudioInputStream ais;

    public void onCreate(View v) {
        new Thread(new Runnable(){
            public void run() {
                //TargetDataLine line;
                DatagramPacket dgp;
                port = 50005;
                InetAddress addr;

                //AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);

                // DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                *//*if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Line matching " + info + " not supported.");
                    return;
                }*//*

                try {
                    addr = InetAddress.getByName("192.168.43.82");
                    // line = (TargetDataLine) AudioSystem.getLine(info);

                    //TOTALLY missed this.
                    //int buffsize = line.getBufferSize() / 5;
                    int buffsize = 0;
                    buffsize += 512;

                    // line.open(format);

                    //line.start();

                    int numBytesRead;
                    byte[] data = new byte[buffsize];

                    final int λ = 256;
                    ByteBuffer buffer = ByteBuffer.allocate(λ * 8);


                    // LocalHost : passer en réseau local

                    Log.d("Envoi", "Avant envoi serveur");
                    try(DatagramSocket socket = new DatagramSocket()) {
                        while (true) {
		                    for(byte b : data) System.out.print(b + " ");

		                    // Read the next chunk of data from the TargetDataLine.
//		                  numBytesRead = line.read(data, 0, data.length);

		                    for(int i = 0; i < 64; i++) {
		                        byte b = data[i];
		                        System.out.print(b + " ");
		                    }
		                    System.out.println();

                            // Save this chunk of data.
                            //for(int j = 0; j < 2; j++) {
                            buffer.clear();
                            for(double i = 0.0; i < λ; i++) {
                                //System.out.println(j + " " + i);
                                //once for each sample
                                buffer.putShort((short)(Math.sin(Math.random()*4 * (λ/i)) * Short.MAX_VALUE));
                                buffer.putShort((short)(Math.sin(Math.random()*4 * (λ/i)) * Short.MAX_VALUE));
                            }
                            //}
                            runOnUiThread(new Runnable() {

                                public void run() {

                                    Log.d("Envoi", "Envoi OK");

                                }

                            });

                            data = buffer.array();
                            dgp = new DatagramPacket(data, data.length, addr, port);


                            socket.send(dgp);
                        }
                    }


                } catch (UnknownHostException e) {
                    Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_LONG).show();
                    // TODO: handle exception
                } catch (SocketException e) {
                    Toast.makeText(getApplicationContext(), "Socket error", Toast.LENGTH_LONG).show();
                    // TODO: handle exception
                } catch (IOException e2) {
                    Toast.makeText(getApplicationContext(), "Input / Output error", Toast.LENGTH_LONG).show();
                    // TODO: handle exception
                }


            }
        }).start();

        //thread.start();

    }
}
    *//*public void onClick(View v) {

        new Thread(new Runnable() {

            public void run() {

                int caractere = hamlet.indexOf("Être ou ne pas être");

                runOnUiThread(new Runnable() {

                    public void run() {

                        v.setText("Cette phrase se trouve au " + caractere + " ème caractère.");

                    }

                });

            }

        }).start();

    }*/
public class JamActivity extends UsbActivity {

    private String TAG = "StartServiceActivity";

    private boolean mPermissionRequestPending;
    private PendingIntent mPermissionIntent;
    private UsbManager mUsbManager;

    static final String ACTION_USB_PERMISSION = "com.val.databaseconnect_v2.Arduino_connect.usb.StartServiceActivity.action.USB_PERMISSION";
    static final String ACTION_STOP_SERVICE = "com.val.databaseconnect_v2.Arduino_connect.usb.StartServiceActivity.action.STOP_SERVICE";

    private Intent startServiceIntent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAndSetViews();

//        short value = (short)(Math.sin(Math.PI*4 * (λ/i)) * Short.MAX_VALUE);

//        short value = (short) 44000;

        /* for testing purpose!!

        byte data[] ;

        final int λ = 256;
        //34, 8 bytes en plus pour le temps
        ByteBuffer buffer = ByteBuffer.allocate(34*4);

        for(double i = 0.0; i < 32; i++) {
            //System.out.println(j + " " + i);
            //once for each sample
            buffer.putShort((short)(Math.sin(Math.PI*4 * (λ/i)) * Short.MAX_VALUE));
            buffer.putShort((short)(Math.sin(Math.PI*4 * (λ/i)) * Short.MAX_VALUE));
        }
        data = buffer.array();

        Log.i("blabla", data.toString());
        */

        setServices();
        //new SendData().execute();
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    //UsbAccessory accessory = UsbManager.getAccessory(intent);

                    UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        startService(startServiceIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "permission denied for accessory "
                                + accessory, Toast.LENGTH_LONG).show();
                    }
                    mPermissionRequestPending = false;
                }
                unregisterReceiver(mUsbReceiver);
                finish();
            } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
            }
        }
    };


    protected void createAndSetViews() {
        currentViewable_ = new TerminalViewable();
        currentViewable_.setActivity(this);
        //this.setContentView(R.layout.jam);

        signalToUi(Viewable.DISABLE_CONTROLS_TYPE, null);
    }

    private void setServices(){
        //        Toast.makeText(getApplicationContext(), "onCreate entered", Toast.LENGTH_LONG).show();
        //mUsbManager = UsbManager.getInstance(this);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        startServiceIntent = new Intent(this, com.val.databaseconnect_v2.Arduino_connect.usb.ArduinoUsbService.class);

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        registerReceiver(mUsbReceiver, filter);



        UsbAccessory[] accessories = mUsbManager.getAccessoryList();
        UsbAccessory accessory = (accessories == null ? null : accessories[0]);
        if (accessory != null) {
            if (mUsbManager.hasPermission(accessory)) {
                startService(startServiceIntent);
                unregisterReceiver(mUsbReceiver);
                finish();
            } else {
                synchronized (mUsbReceiver) {
                    if (!mPermissionRequestPending) {
                        mUsbManager.requestPermission(accessory,
                                mPermissionIntent);
                        mPermissionRequestPending = true;
                    }
                }
            }
        } else {
//            Toast.makeText(getApplicationContext(), "mAccessory is null", Toast.LENGTH_LONG).show();
        }
//        Toast.makeText(getApplicationContext(), "onCreate exited", Toast.LENGTH_LONG).show();
    }

//    public void signalToUi(int type, Object data) {
//        Runnable runnable = null;
//
//        if (type == Viewable.CHAR_SEQUENCE_TYPE) {
//            if (data == null || ((CharSequence) data).length() == 0) {
//                return;
//            }
//            final CharSequence tmpData = (CharSequence) data;
//            runnable = new Runnable() {
//                public void run() {
//                    addToHistory(tmpData);
//                }
//            };
//        } else if (
//                ( type == Viewable.DEBUG_MESSAGE_TYPE || type == Viewable.INFO_MESSAGE_TYPE ) &&
//                        ( type <= messageLevel_ )
//                ) {
//            if (data == null || ((CharSequence) data).length() == 0) {
//                return;
//            }
//            final CharSequence tmpData = (CharSequence) data;
//            runnable = new Runnable() {
//                public void run() {
//                    addToHistory(tmpData);
//                }
//            };
//        } else if (type == Viewable.BYTE_SEQUENCE_TYPE) {
//            if (data == null || ((byte[]) data).length == 0) {
//                return;
//            }
//            final byte[] byteArray = (byte[]) data;
//            runnable = new Runnable() {
//                public void run() {
//                    addToHistory(byteArray);
//                }
//            };
//        }
//
//        if (runnable != null) {
//            activity_.runOnUiThread(runnable);
//        }
//
//    }
}




// AsyncTask used to communicate with the distant server.
//class SendData extends AsyncTask<String, String, String> {
//
//
//    /**
//     * Communicating with the server in background thread
//     * */
//    public byte[] buffer;
//    private int port;
//
//    public SendData(){
//        super();
//    }
//
//    protected void onPreExecute() {
//        // user with this username found
//        // Edit Text
//       /* username = (TextView) findViewById(R.id.username);
//        email = (TextView) findViewById(R.id.email);
//
//        // display user data in EditText
//        username.setText(displayUsername);
//        email.setText(displayEmail);*/
//
//    }
//
//    protected String doInBackground(String... params) {
//
//        //TargetDataLine line;
//        Date dateDebut = new Date();
//
//        DatagramPacket dgp;
//        port = 50005;
//        InetAddress addr;
//        int countPacket=0;
//        Log.e("thread lance", "Thread OK");
//        long timeExe=0;
//
//
//        try {
//            addr = InetAddress.getByName("192.168.43.82");
//
//            int buffSize = 32*4;
//            //Test sur le temps de reception
//            buffSize+=8;
//
//            byte data[] ;
//            data= new byte[buffSize];
//
//            final int λ = 256;
//            //34, 8 bytes en plus pour le temps
//            ByteBuffer buffer = ByteBuffer.allocate(34*4);
//
//
//            // LocalHost : passer en réseau local
//
//            Log.e("Socket", "Avant creation Socket");
//            try(DatagramSocket socket = new DatagramSocket()) {
//                while (countPacket <100) {
//                    //timeExe = System.currentTimeMillis();
//                    timeExe=dateDebut.getTime();
//                    buffer.clear();
//                    for(double i = 0.0; i < 32; i++) {
//                        //System.out.println(j + " " + i);
//                        //once for each sample
//                        buffer.putShort((short)(Math.sin(Math.PI*4 * (λ/i)) * Short.MAX_VALUE));
//                        buffer.putShort((short)(Math.sin(Math.PI*4 * (λ/i)) * Short.MAX_VALUE));
//                    }
//                    //}
//                   /* runOnUiThread(new Runnable() {
//
//                        public void run() {
//
//                            Log.d("Envoi", "Envoi OK");
//
//                        }
//
//                    });*/
//                    buffer.putLong(timeExe);
//                    data = buffer.array();
//                    dgp = new DatagramPacket(data, data.length, addr, port);
//                    //Log.d("Socket", "Avant envoi Socket");
//
//                    socket.send(dgp);
//
//                    countPacket++;
//                    Log.e("Envoi packet "+ countPacket, "Envoi OK");
////                    try {
////                        Thread.sleep(1);
////                    } catch(InterruptedException ex) {
////                        Thread.currentThread().interrupt();
////                    }
//                }
//            }
//
//
//        } catch (UnknownHostException e) {
//            // Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_LONG).show();
//            // TODO: handle exception
//            Log.e("Host", "Unknown Host");
//        } catch (SocketException e) {
//            // Toast.makeText(getApplicationContext(), "Socket error", Toast.LENGTH_LONG).show();
//            // TODO: handle exception
//            Log.e("Socket", "Socket Exception");
//        } catch (IOException e2) {
//            // Toast.makeText(getApplicationContext(), "Input / Output error", Toast.LENGTH_LONG).show();
//            // TODO: handle exception
//        }
//
//
//        catch(Exception e){
//            e.printStackTrace();
//        }
////                }
////            });
//
//        return null;
//    }
//
//    /**
//     * After completing background task Dismiss the progress dialog
//     * **/
//    protected void onPostExecute() {
//        Log.e("Envoi", "Envoi OK");
//        //Toast.
//        // user with this username found
//        // Edit Text
//       /* username = (TextView) findViewById(R.id.username);
//        email = (TextView) findViewById(R.id.email);
//
//        // display user data in EditText
//        username.setText(displayUsername);
//        email.setText(displayEmail);*/
//    }
//}