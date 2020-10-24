package me.maanex.mcdiscord;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.maanex.mcdiscord.dclistener.Messages;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordManager {

	private static JDA client;

	private DiscordManager() {
	}
	
	public static void login(String token) throws LoginException {
		client = JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
	        .addEventListeners(new Messages())
	        .setActivity(buildActivity(Bukkit.getOnlinePlayers().size()))
	        .build();
	}
	
	public static void destroy() {
		client.removeEventListener(client.getEventManager().getRegisteredListeners());
		client.shutdownNow();
		client = null;
	}
	
	//
	
	public static void onlineCountChanged(int count) {
		client.getPresence().setActivity(buildActivity(count));
	}
	
	public static void systemMessage(SystemMessageType type, String message) {
		switch (type) {
			case PLAYER_LOGIN:
				message = "<:user_join:536593880271814656> " + message;
				break;

			case PLAYER_LOGOUT:
				message = "<:user_quit:536593879940595732> " + message;
				break;

			case ACHIEVEMENT:
				message = "<:message:536593879865098251> " + message;
				break;
				
			case DEATH:
				message = ":skull: " + message;
				break;
				
			case BLANK: break;
		}

		TextChannel channel = (TextChannel) client.getGuildById(Main.guildId).getGuildChannelById(Main.channelId);
		channel.sendMessage(message).queue();
	}
	
	public static void userMessage(Player player, String message) {
		String json = String.format("{\"content\": \"%s\",\"username\": \"%s\",\"avatar_url\": \"https://crafatar.com/avatars/%s\"}", message, player.getName(), player.getUniqueId().toString());
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		try {
			HttpPost req = new HttpPost(Main.webhookUrl);
			StringEntity params = new StringEntity(json);
			req.addHeader("Content-Type", "application/json; charset=UTF-8");
			req.setEntity(params);
			client.execute(req);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//
	
	private static Activity buildActivity(final int onlineCount) {
		return new Activity() {
			public boolean isRich() {
				return false;
			}
			
			public String getUrl() {
				return null;
			}
			
			public ActivityType getType() {
				return ActivityType.WATCHING;
			}
			
			public Timestamps getTimestamps() {
				return null;
			}
			
			public String getName() {
				return String.format("%s Online", onlineCount + "");
			}
			
			public Emoji getEmoji() {
				return null;
			}
			
			public RichPresence asRichPresence() {
				return null;
			}
		};
	}
	
	//
	
	public static enum SystemMessageType {
		PLAYER_LOGIN,
		PLAYER_LOGOUT,
		ACHIEVEMENT,
		BLANK,
		DEATH
	}

}
