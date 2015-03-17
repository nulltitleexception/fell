package com.monolc.fell.net;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
	long lastMessage;
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String recieved;
	ClientStatus status;
	public Client(Socket s) {
		in = null;
		out = null;
		socket = s;
		try {
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		recieved = "";
		status = new ClientStatus();
		lastMessage = System.currentTimeMillis();
	}
	public boolean connected() {
		return getDisconnectMessage() == null;
	}
	public String getDisconnectMessage() {
		if (System.currentTimeMillis() - lastMessage > 5000) {
			return "Inactive for 5 seconds";
		}
		return null;
	}
	public void delete() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		return socket.getInetAddress() + "";
	}
	public void send(String message) {
		out.println(message.replace('\n', ';'));
	}
	public boolean messageAvailable() {
		try {
			while (in.ready()) {
				int val = in.read();
				if (val != -1) {
					recieved += (char) val;
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recieved.contains("\n");
	}
	public String recieve() {
		String ret = recieved.substring(0, recieved.indexOf("\n"));
		recieved = recieved.substring(recieved.indexOf("\n") + 1);
		lastMessage = System.currentTimeMillis();
		return ret;
	}
	public ClientStatus getStatus() {
		return status;
	}
}
