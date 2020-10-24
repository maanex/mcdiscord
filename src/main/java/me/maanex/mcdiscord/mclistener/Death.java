package me.maanex.mcdiscord.mclistener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.maanex.mcdiscord.DiscordManager;
import me.maanex.mcdiscord.DiscordManager.SystemMessageType;

public class Death implements Listener {

	public Death() {
	}
	
	@EventHandler
	private void onDie(PlayerDeathEvent e) {
		String message = String.format(e.getDeathMessage());
		DiscordManager.systemMessage(SystemMessageType.DEATH, message.replace(e.getEntity().getName(), "**" + e.getEntity().getName() + "**"));
	}

}
