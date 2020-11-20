package ml.codenoodles.lmve.sounds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import ml.codenoodles.lmve.Main;
import ml.codenoodles.lmve.other.ConsoleColor;

public class ChatNotification implements Listener{

	private Main main;
	public ChatNotification(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void ChatNotifiy(AsyncPlayerChatEvent e) {
		if(!main.getConfigFile().getBoolean("Chat.global-mute")) {
			for(Player players : Bukkit.getOnlinePlayers()){
				Connection conn = null;
				UUID uuid = e.getPlayer().getUniqueId();
				String path = "jdbc:sqlite:" + main.getDataFolder().getAbsolutePath() + "/Players.db";
				Boolean isEnabled = false;
				try {
					conn = DriverManager.getConnection(path);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT ChatNotify AS chatnotify FROM tblPlayerSettings WHERE UUID = '" + uuid.toString() + "';");
					rs.next();
					isEnabled = rs.getBoolean(1);
					rs.close();
				} catch(SQLException sqlEx) {
					System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
				}
				if(isEnabled) {
					if(players.getPlayer().getName() != e.getPlayer().getName()) {
						players.playNote(players.getLocation(), Instrument.BANJO, Note.natural(1, Tone.C));
					}
				}
			}
		}
	}
	
}
