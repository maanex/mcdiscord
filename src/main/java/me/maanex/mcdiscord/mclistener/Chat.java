package me.maanex.mcdiscord.mclistener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.maanex.mcdiscord.DiscordManager;

public class Chat implements Listener {

	public Chat() {
	}
	
	@EventHandler
	private void onChat(AsyncPlayerChatEvent e) {
		DiscordManager.userMessage(e.getPlayer(), e.getMessage());
		e.setCancelled(true);
		Bukkit.broadcastMessage(formatChatMessage(e.getPlayer().getDisplayName(), e.getMessage(), ChatMessageSource.MINECRAFT));
	}
	
	//
	
	public static String formatChatMessage(String author, String message, ChatMessageSource source) {
		return String.format("§7%s §e%s§7: §r%s", source.icon, author, message);
	}
	
	public static enum ChatMessageSource {
		MINECRAFT("M"),
		DISCORD("D"),
		
		;
		
		public final String icon;
		
		private ChatMessageSource(String icon) {
			this.icon = icon;
		}
	}

}
