package me.maanex.mcdiscord.mclistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import me.maanex.mcdiscord.DiscordManager;
import me.maanex.mcdiscord.DiscordManager.SystemMessageType;

public class Achievements implements Listener {

	public Achievements() {
	}
	
	@EventHandler
	private void onAchievement(PlayerAdvancementDoneEvent e) {
		String[] parts = e.getAdvancement().getKey().getKey().split("/")[1].split("_");
		String advancementName = ""; 
		for (String part : parts)
				advancementName += " " + part.substring(0, 1).toUpperCase() + part.substring(1);
		String message = String.format("**%s** has made the advancement **%s**", e.getPlayer().getName(), advancementName.substring(1));
		DiscordManager.systemMessage(SystemMessageType.ACHIEVEMENT, message);
	}

}
