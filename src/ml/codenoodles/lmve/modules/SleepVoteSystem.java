package ml.codenoodles.lmve.modules;

import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import ml.codenoodles.lmve.Main;

public class SleepVoteSystem implements Listener{
	
	
	private int playersInBed = 0;
	private Player players[];
	private Main main;
	public SleepVoteSystem(Main main) {
		this.main = main;
		players = new Player[main.getServer().getMaxPlayers()];
	}
	
	@EventHandler
	private void EnterBed(PlayerBedEnterEvent e) {
		World world = e.getPlayer().getWorld();
		if((world.getTime() > 12540) || (world.isThundering() == true)) {
			int pib = playersInBed;
			float onlinePlayers = main.getServer().getOnlinePlayers().size();
			pib = pib + 1;
			playersInBed = pib;
			main.getServer().broadcastMessage(ChatColor.GOLD + e.getPlayer().getName() + ChatColor.GRAY + " is now sleeping!");
			double calc = ((onlinePlayers / 100.0)  * main.getConfig().getDouble("Sleep.min-perc"));
			int neededPlayers = (int) calc;
			players[playersInBed - 1] = e.getPlayer();
			if(playersInBed >= neededPlayers) {
				for(int i = 0; i < playersInBed; i++) {
					players[i].setBedSpawnLocation(players[i].getLocation());
					players[i].setStatistic(Statistic.TIME_SINCE_REST, 0);
				}
				main.getServer().broadcastMessage(ChatColor.GRAY + "Time was set to Day!");
				e.getPlayer().getWorld().setTime(1000);
				e.getPlayer().getWorld().setStorm(false);
				e.getPlayer().getWorld().setThundering(false);
				playersInBed = 0;
				for(int i = 0; i < main.getServer().getMaxPlayers(); i++) {
					players[i] = null;
				}
			} else {
				main.getServer().broadcastMessage(ChatColor.GOLD + "" + (neededPlayers - playersInBed) + ChatColor.GRAY + " more Players are needed to change Time to day!");
			}
		} else {
			e.getPlayer().sendMessage(ChatColor.GRAY + "You can only sleep at Night or during a Thunderstorm");
		}
	}
	
	@EventHandler
	private void BedLeave(PlayerBedLeaveEvent e) {
		main.getServer().broadcastMessage(ChatColor.GOLD + e.getPlayer().getName() + ChatColor.GRAY + " is no longer sleeping!");
	}
	
}
