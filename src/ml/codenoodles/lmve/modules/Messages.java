package ml.codenoodles.lmve.modules;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ml.codenoodles.lmve.Main;

public class Messages implements Listener{

	private Main main;
	public Messages(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void JoinMessage(PlayerJoinEvent e) {
		if(main.getConfigFile().getBoolean("JoinMessage.enabled") == true) {
			e.setJoinMessage(ChatColor.GREEN + "[+]" + ChatColor.GRAY + e.getPlayer().getDisplayName() + " joined the Server!");
		}
	}
	
	@EventHandler
	private void QuitMessage(PlayerQuitEvent e) {
		if(main.getConfigFile().getBoolean("QuitMessage.enabled")) {
			e.setQuitMessage(ChatColor.RED + "[-]" + ChatColor.GRAY + e.getPlayer().getDisplayName() + " left the Server!");
		}
	}
}
