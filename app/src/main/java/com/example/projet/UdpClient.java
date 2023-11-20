package com.example.projet;
import android.app.Activity;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpClient {
    private DatagramSocket udpSocket;
    private byte[] buffer;
    private Activity activity;
    private OnDataReceivedListener onDataReceivedListener; // Ajout de l'interface

    // Ajout du constructeur prenant l'interface en paramètre
    public UdpClient(Activity activity, MainActivity mainActivity) {
        this.activity = activity;
        this.onDataReceivedListener = onDataReceivedListener;
        buffer = new byte[1024];
    }

    public void sendMessage(String command) {
    }


    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }

    public void startListening() {
        try {
            udpSocket = new DatagramSocket(10000);  // Le port doit correspondre au port de la passerelle
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);
                final String data = new String(packet.getData(), 0, packet.getLength(), "UTF-8");

                // Notifier l'activité Android de la réception de nouvelles données
                if (onDataReceivedListener != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onDataReceivedListener.onDataReceived(data);
                        }
                    });
                }

                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
                Log.d("UdpClient", "Received data: " + data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (udpSocket != null && !udpSocket.isClosed()) {
                udpSocket.close();
            }
        }
    }
}