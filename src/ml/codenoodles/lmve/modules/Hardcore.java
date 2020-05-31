package ml.codenoodles.lmve.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerLoadEvent;

import ml.codenoodles.lmve.Main;

public class Hardcore implements Listener{
	
	private Main main;
	
	public Hardcore(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void DisableMessage(ServerLoadEvent e) {
		if(!main.getConfigFile().getBoolean("Hardcore.enabled")) {
			for(Player players : Bukkit.getOnlinePlayers()){
				players.playSound(players.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 10, 0.8f);
				players.sendMessage(ChatColor.YELLOW + "ATTENTION! Hardcore Mode: Disabled.");
			}	
		}
	}
	
	@EventHandler
	private void onPlayerDamage(EntityDamageEvent e) {
		if(main.getConfigFile().getBoolean("Hardcore.enabled")) {
			Entity entity = e.getEntity();
			if (entity instanceof Player) {
			   e.setDamage(e.getDamage() * 2);
			}
		}
	}
	
	@EventHandler
	private void WarnMessageOnJoin(PlayerJoinEvent e) {
		if(main.getConfigFile().getBoolean("Hardcore.enabled")) {
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PHANTOM_AMBIENT, 10, 0);
			e.getPlayer().sendMessage(ChatColor.RED + "WARNING! HARCORE IS ENABLED!");
			e.getPlayer().sendMessage(ChatColor.RED + "The damage you get is doubled!");
		}
	}
	
	@EventHandler
	private void EnableMessage(ServerLoadEvent e) {
		if(main.getConfigFile().getBoolean("Hardcore.enabled")) {
			System.out.println("[VMVE]HARDCORE MODE ACTIVATED.");
			for(Player players : Bukkit.getOnlinePlayers()){
				players.playSound(players.getLocation(), Sound.ENTITY_PHANTOM_AMBIENT, 10, 0);
				players.sendMessage(ChatColor.RED + "WARNING! HARCORE IS ENABLED!");
				players.sendMessage(ChatColor.RED + "The damage you get is doubled!");
			}	
		}
	}
	
}
