import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import static java.util.Arrays.copyOfRange;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Server {

//    public static int byteArrayToLong(byte[] b) 
//{
//    int value = 0;
//    for (int i = 0; i < 8; i++) {
//        int shift = (4 - 1 - i) * 8;
//        value += (b[i] & 0x000000FF) << shift;
//    }
//    return value;
//}
    
    public static long bytesToLong(byte[] bytes) {
    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    buffer.put(bytes);
    buffer.flip();//need flip 
    return buffer.getLong();
}
    
    AudioInputStream audioInputStream;
    static AudioInputStream ais;
    static AudioFormat format;
    static boolean status = true;
    static int port = 50005;
    static int sampleRate = 48000;

    static DataLine.Info dataLineInfo;
    static SourceDataLine sourceDataLine;
    static InetAddress addresseClient;

    public static void main(String args[]) throws Exception 
    {
        System.out.println("Server started at port:" +port);

        DatagramSocket serverSocket = new DatagramSocket(port);

        /**
         * Formula for lag = (byte_size/sample_rate)*2
         * Byte size 9728 will produce ~ 0.45 seconds of lag. Voice slightly broken.
         * Byte size 1400 will produce ~ 0.06 seconds of lag. Voice extremely broken.
         * Byte size 4000 will produce ~ 0.18 seconds of lag. Voice slightly more broken then 9728.
         */

        byte[] receiveData = new byte[2048];

        format = new AudioFormat(sampleRate, 16, 2, true, true);
        dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(format);
        sourceDataLine.start();

        //FloatControl volumeControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        //volumeControl.setValue(1.00f);
//System.out.println(receivePacket.getAddress());
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        //System.out.println(receivePacket.getAddress());
        
        

        ByteArrayInputStream baiss = new ByteArrayInputStream(copyOfRange(receivePacket.getData(), 0, 128));
       // long timeExe =System.currentTimeMillis();
        //int packetNumber=0;
        //long time=0;
        //boolean started=false;
        boolean firstPacket=true;
        
        while (status == true) 
        {
           
           // packetNumber++;
            //timeExe = System.currentTimeMillis();
            serverSocket.receive(receivePacket);
//            if(firstPacket){
//                firstPacket=false;
//                addresseClient= receivePacket.getAddress();
//            }
//                DatagramPacket sentPacket = new DatagramPacket(receiveData, receiveData.length, addresseClient, port);
//               //System.out.println("Avant renvoi");
//            
//            serverSocket.send(sentPacket);
            //System.out.println("Après renvoi");
           
//            byte[] buffer = ByteBuffer.allocate(32*4);
//            buffer(receivePacket.getData().array());
//            timeExe = receivePacket[31];
           // receiveData = receivePacket.getData().copyOfRange;
            //receiveData = copyOfRange(receivePacket.getData(), 0, 128);
            
           // time = bytesToLong(copyOfRange(receivePacket.getData(), 128, 136));
           // System.out.println("Packet : "+packetNumber+"\nTemps d'envoi : "+time);
            //receiveData
            ais = new AudioInputStream(baiss, format, receivePacket.getLength());
            
            toSpeaker(receivePacket.getData());
            
            
            
        }

        sourceDataLine.drain();
        sourceDataLine.close();
        serverSocket.close();
    }

    public static void toSpeaker(byte soundbytes[]) {
        //long timeExe=0;
        //Date dateServer = new Date();
        try 
        {
            
            //System.out.println("At the speaker");
            
            //long timeExe = System.currentTimeMillis();
            sourceDataLine.write(soundbytes, 0, soundbytes.length);
            //timeExe = System.currentTimeMillis() - time;
            //timeExe=dateServer.getTime() - time;
           // System.out.println("Temps de reception : "+timeExe);
           
            
        } catch (Exception e) {
            System.out.println("Not working in speakers...");
            e.printStackTrace();
        }
    }
}
