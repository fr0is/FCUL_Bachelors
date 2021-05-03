package net.padroesfactory;

public class Channel {
	String username;
	public Channel(String u) {
		username = u;
	}
	
	public void sendMessage(String msg) {
		System.out.println("Sent via Discord4J to " + username  + ": " + msg);
		
	}
}
