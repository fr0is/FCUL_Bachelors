package net.padroesfactory;

/*
 * Inspirado no verdadeiro Discord4J
 * https://github.com/Discord4J/Discord4J
 */
public class Discord4JMock {
	public Discord4JMock(String token) {
		
	}
	
	public Channel getChannel(String username) {
		return new Channel(username);
	}
	
}	
