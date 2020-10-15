package me.maanex.mcdiscord.mclistener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.maanex.mcdiscord.DiscordManager;
import me.maanex.mcdiscord.DiscordManager.SystemMessageType;

public class JoinLeave implements Listener {

	public JoinLeave() {
	}
	
	@EventHandler
	private void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		String message = buildJoinMessage(e.getPlayer().getName());
		Bukkit.broadcastMessage("§d> §b" + message);
		DiscordManager.onlineCountChanged(Bukkit.getOnlinePlayers().size());
		DiscordManager.systemMessage(SystemMessageType.PLAYER_LOGIN, message.replaceAll("§.", "**"));
	}
	
	@EventHandler
	private void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		String message = buildQuitMessage(e.getPlayer().getName());
		Bukkit.broadcastMessage("§d< §b" + message);
		DiscordManager.onlineCountChanged(Bukkit.getOnlinePlayers().size() - 1);
		DiscordManager.systemMessage(SystemMessageType.PLAYER_LOGOUT, message.replaceAll("§.", "**"));
	}
	
	//
	
	private String buildJoinMessage(String name) {
		String[] list = new String[] {
			"%s joined the madness",
			"Welcome, %s!",
			"Bonjour %s",
			"Leave guns by the door %s",
			"%s hacked his way in",
			"It's just %s",
			"Welcome back %s!",
			"Enjoy your stay %s :)"
		};
		return String.format(list[(int) Math.floor(Math.random() * list.length)], "§3" + name + "§b");
	}
	
	private String buildQuitMessage(String name) {
		String[] list = new String[] {
			"%s left the game",
			"%s dropped out. Bye bye!",
			"Good bye, %s!",
			"See you later %s!",
			"%s's gone. Crab rave."
		};
		return String.format(list[(int) Math.floor(Math.random() * list.length)], "§3" + name + "§b");
	}

}
