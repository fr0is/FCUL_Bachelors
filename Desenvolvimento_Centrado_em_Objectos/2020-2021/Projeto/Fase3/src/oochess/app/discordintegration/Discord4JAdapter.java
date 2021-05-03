package oochess.app.discordintegration;

import net.padroesfactory.Channel;
import net.padroesfactory.Discord4JMock;

public class Discord4JAdapter implements DiscordAdapter{
	
	private Discord4JMock disc4JM;	
	
	public Discord4JAdapter(String token) {
		this.disc4JM = new Discord4JMock(token);
	}

	@Override
	public void enviaPartida(String discordId,String msg) {
		Channel canalDoDestinatario = this.disc4JM.getChannel(discordId);
		canalDoDestinatario.sendMessage(msg);
	}
}
