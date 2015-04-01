package com.val.databaseconnect_v2;

import android.app.Activity;
import android.content.Intent;
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
public class JamActivity extends Activity {

   /* public void onCreate(View v) {
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            ThreadClient threadClient = new ThreadClient(50005, addr);
            threadClient.start();
        }
        catch(UnknownHostException e){
            Log.d("Host not found", "Host not found");
        }

    }*/
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
                /*if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Line matching " + info + " not supported.");
                    return;
                }*/

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
    /*public void onClick(View v) {

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
