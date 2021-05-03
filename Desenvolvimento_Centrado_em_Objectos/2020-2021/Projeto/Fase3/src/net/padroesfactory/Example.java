package net.padroesfactory;

public class Example {

	public static void main(String[] args) {
		Discord4JMock c = new Discord4JMock("3423134134");
		Channel canalDoDestinatario = c.getChannel("JoaoRock");
		canalDoDestinatario.sendMessage("Vou dar Xeque-Mate!");
	}
}
