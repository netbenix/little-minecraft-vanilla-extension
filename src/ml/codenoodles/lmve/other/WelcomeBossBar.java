package ml.codenoodles.lmve.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import ml.codenoodles.lmve.Main;

public class WelcomeBossBar implements Listener{
	
	private Main main;
	public WelcomeBossBar(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void PlayerWelcome(PlayerJoinEvent e) {
		NamespacedKey joinbossbar = new NamespacedKey(main, "joinbossbar_" + e.getPlayer().getUniqueId());
		Bukkit.createBossBar(joinbossbar,ChatColor.GREEN + "Welcome on " + ChatColor.GOLD + main.getConfigFile().getString("server-name"), BarColor.PURPLE, BarStyle.SEGMENTED_10).addPlayer(e.getPlayer());
		new BukkitRunnable() {
			int times = 0;
			@Override
			public void run() {
				if(times < 5) {
            		Bukkit.getBossBar(joinbossbar).setProgress(Bukkit.getBossBar(joinbossbar).getProgress() + 0.19f);
            	} 
				if(times == 5) {
					Bukkit.getBossBar(joinbossbar).setProgress(1.0f);
				}
            	if(times == 6) {
            		Bukkit.getBossBar(joinbossbar).setTitle(ChatColor.GREEN + "Have Fun!");
            	}
      
                if(times == 8) {
                	Bukkit.getBossBar(joinbossbar).removePlayer(e.getPlayer());
                	Bukkit.removeBossBar(joinbossbar);
                	this.cancel();
                }
                times++;
			}
		}.runTaskTimer(main, 0L, 20L);
	        
	}
	
	
	
}
