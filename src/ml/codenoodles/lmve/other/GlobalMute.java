package ml.codenoodles.lmve.other;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ml.codenoodles.lmve.Main;

public class GlobalMute implements Listener{
	
	private Main main;
	public GlobalMute(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void GlobalChatMute(AsyncPlayerChatEvent e) {
		if(main.getConfigFile().getBoolean("Chat.global-mute")) {
			e.getPlayer().sendMessage(ChatColor.GOLD + "Chat is disabled on this server.");
			e.setCancelled(true);
		}
	}
}
