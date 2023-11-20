package com.example.projet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;


public class MyThread extends Thread {
    private BlockingQueue<String> queue;
    private DatagramSocket UDPSocket;
    private InetAddress address;
    private final int port=10000;
    public MyThread(BlockingQueue<String> queue) {
        this.queue = queue;
        try {
            UDPSocket = new DatagramSocket();
            address = InetAddress.getByName("192.168.242.233");
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void run() {
        int i = 0;
        String message;
        while (i < 5) {
            try {
                message = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                byte[] data = message.getBytes();
                DatagramPacket packet = new DatagramPacket(data,data.length, address, port);
                UDPSocket.send(packet);
            } catch (IOException e) { e.printStackTrace(); }
            i++;
        }
    }
}
