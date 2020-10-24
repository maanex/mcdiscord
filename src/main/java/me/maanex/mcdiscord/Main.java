package me.maanex.mcdiscord;

import javax.security.auth.login.LoginException;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.maanex.mcdiscord.mclistener.Chat;
import me.maanex.mcdiscord.mclistener.Death;
import me.maanex.mcdiscord.mclistener.JoinLeave;

public class Main extends JavaPlugin {

	public static String botToken = "";
	public static long guildId = 0;
	public static long channelId = 0;
	public static String webhookUrl = "";

	public Main() {
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		System.out.println("Beep boop, starting up Discord bot.");
		
		saveDefaultConfig();

		loadConfig();
		
		registerListeners();
		
		loginDiscord();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		DiscordManager.destroy();
		
		System.out.println("Boop, shutdown. Beep.");
	}
	
	//
	
	private void loadConfig() {
		botToken = getConfig().getString("bot.token");
		guildId = getConfig().getLong("bot.guild");
		channelId = getConfig().getLong("bot.channel");
		webhookUrl = getConfig().getString("bot.webhook");
	}
	
	private void registerListeners() {
		PluginManager m = getServer().getPluginManager();
		m.registerEvents(new JoinLeave(), this);
//		m.registerEvents(new Achievements(), this); -- DONT, IT SPAMS
		m.registerEvents(new Chat(), this);
		m.registerEvents(new Death(), this);
	}
	
	private void loginDiscord() {
		try {
			DiscordManager.login(botToken);
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

}
