package ml.codenoodles.lmve.sounds;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ml.codenoodles.lmve.Main;

public class ChatNotification implements Listener{

	private Main main;
	public ChatNotification(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void ChatNotifiy(AsyncPlayerChatEvent e) {
		if(!main.getConfigFile().getBoolean("Chat.global-mute")) {
			for(Player players : Bukkit.getOnlinePlayers()){
				File playerStats = new File(main.getDataFolder() + "/Players", players.getUniqueId() + ".yml");
				FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
				if(player_stat.getBoolean(players.getUniqueId() + ".Settings.Sounds.ChatNotify")) {
					if(players.getPlayer().getName() != e.getPlayer().getName()) {
						players.playNote(players.getLocation(), Instrument.BANJO, Note.natural(1, Tone.C));
					}
				}
			}
		}
	}
	
}
