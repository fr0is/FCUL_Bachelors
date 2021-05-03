package oochess.app.discordintegration;

import com.chavetasfechadas.JDAMock;
import com.chavetasfechadas.JDAMockBuilder;

import net.padroesfactory.Discord4JMock;

public class JDAAdapter implements DiscordAdapter{

	private JDAMock jdaMock;

	public JDAAdapter(String token) {
		this.jdaMock = new JDAMockBuilder().createDefault(token).disableCache(true).setCompression(true).setActivity("Playing chess").build();
	}
	
	@Override
	public void enviaPartida(String discordId, String msg) {
		this.jdaMock.sendMessage(discordId, msg);
	}
}
