package me.maanex.mcdiscord.dclistener;

import org.bukkit.Bukkit;

import me.maanex.mcdiscord.Main;
import me.maanex.mcdiscord.mclistener.Chat;
import me.maanex.mcdiscord.mclistener.Chat.ChatMessageSource;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Messages extends ListenerAdapter {

	public Messages() {
	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getGuild().getIdLong() != Main.guildId) return;
        if (msg.getChannel().getIdLong() != Main.channelId) return;
        if (msg.getAuthor().isBot() || msg.getAuthor().isFake()) return;
        
        Bukkit.broadcastMessage(Chat.formatChatMessage(event.getAuthor().getName(), event.getMessage().getContentRaw(), ChatMessageSource.DISCORD));
    }

}
