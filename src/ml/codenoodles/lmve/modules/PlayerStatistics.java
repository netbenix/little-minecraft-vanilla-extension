package ml.codenoodles.lmve.modules;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ml.codenoodles.lmve.Main;
import ml.codenoodles.lmve.other.ConsoleColor;

public class PlayerStatistics implements Listener{

	private Main main;
	public PlayerStatistics(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void UpdateLastJoined(PlayerJoinEvent e) {
		UUID player_uuid = e.getPlayer().getUniqueId();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		File playerStats = new File(main.getDataFolder() + "/Players", player_uuid + ".yml");
		FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
		player_stat.set(player_uuid + ".LastJoined", dtf.format(now));
		try {
			player_stat.save(playerStats);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}	
	}
	
	@EventHandler
	private void SaveOnQuit(PlayerQuitEvent e) {
		Player p = (Player) e.getPlayer();
		UUID uuid = p.getUniqueId();
		
		File playerStats = new File(main.getDataFolder() + "/Players", uuid + ".yml");
		FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
		player_stat.set(uuid + ".General.DisplayName", p.getName());
		player_stat.set(uuid + ".General.Health", p.getHealth());
		player_stat.set(uuid + ".General.World", p.getWorld().getName());
		player_stat.set(uuid + ".General.Location.X", p.getLocation().getX());
		player_stat.set(uuid + ".General.Location.Y", p.getLocation().getY());
		player_stat.set(uuid + ".General.Location.Z", p.getLocation().getZ());
		float pTime = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
		pTime = pTime / 20;
		pTime = pTime / 60;
		pTime = pTime / 60;
		player_stat.set(uuid + ".TimePlayed", pTime);
		try {
			player_stat.save(playerStats);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	private void checkName(PlayerJoinEvent e) throws IOException {
		UUID uuid = e.getPlayer().getUniqueId();
		File pStatFile = new File(main.getDataFolder() + "/Players", uuid + ".yml");
		FileConfiguration pStat = YamlConfiguration.loadConfiguration(pStatFile);
		if(pStat.getString(uuid + ".DisplayName") != e.getPlayer().getName()) {
			String oldName = pStat.getString(uuid + ".DisplayName");
			String newName = e.getPlayer().getName();
			pStat.set(uuid + ".DisplayName", newName);
			
			File uRefFile = new File(main.getDataFolder(), "uuid_reference.yml");
			FileConfiguration uRef = YamlConfiguration.loadConfiguration(uRefFile);
			uRef.set(oldName, null);
			uRef.set(newName, uuid.toString());	
			try {
				pStat.save(pStatFile);
				uRef.save(uRefFile);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			
			Path pListPath = Paths.get(main.getDataFolder()  + "/playerList.txt");
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(pListPath), charset);
			content = content.replaceAll(("\\b" + oldName + "\\b"), newName);
			Files.write(pListPath, content.getBytes(charset));
		}
	}
	
	
	public void outputStats(CommandSender sender, UUID uuid) {
		
		Connection conn;
		int columnCountStats = 0;
		int columnCountStatsF = 0;
		int columnCountStatsN = 0;
		int columnCountStatsH = 0;
		try { //Try Connection
			conn = DriverManager.getConnection("jdbc:sqlite:" + main.getDataFolder().getAbsolutePath() + "/" + "Players.db");
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.PURPLE + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
			return;
		}
		
		String columnNameStats[];
		String columnNameStatsF[];
		String columnNameStatsN[];
		String columnNameStatsH[];
		
		try { //Get Columncounts
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM tblPlayerStats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCountStats = rsmd.getColumnCount() - 2;
			columnNameStats = new String[columnCountStats];
			for(int i = 1; i <= columnCountStats; i++) {
				columnNameStats[i-1] = rsmd.getColumnName(i+2);
			}
			rs.close();
			ResultSet rsF = stmt.executeQuery("SELECT * FROM tblPlayerFKills;");
			ResultSetMetaData rsmdF = rsF.getMetaData();
			columnCountStatsF = rsmdF.getColumnCount() - 2;
			columnNameStatsF = new String[columnCountStatsF];
			for(int i = 1; i <= columnCountStatsF; i++) {
				columnNameStatsF[i-1] = rsmdF.getColumnName(i+2);
			}
			rsF.close();
			ResultSet rsN = stmt.executeQuery("SELECT * FROM tblPlayerNKills;");
			ResultSetMetaData rsmdN = rsN.getMetaData();
			columnCountStatsN = rsmdN.getColumnCount() - 2;
			columnNameStatsN = new String[columnCountStatsN];
			for(int i = 1; i <= columnCountStatsN; i++) {
				columnNameStatsN[i-1] = rsmdN.getColumnName(i+2);
			}
			rsN.close();
			ResultSet rsH = stmt.executeQuery("SELECT * FROM tblPlayerHKills;");
			ResultSetMetaData rsmdH = rsH.getMetaData();
			columnCountStatsH = rsmdH.getColumnCount() - 2;
			columnNameStatsH = new String[columnCountStatsH];
			for(int i = 1; i <= columnCountStatsH; i++) {
				columnNameStatsH[i-1] = rsmdH.getColumnName(i+2);
			}
			rsH.close();
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
			return;
		}
		
		
		String columnValueStats[] = new String[columnCountStats];
		String columnValueStatsF[] = new String[columnCountStatsF];
		String columnValueStatsN[] = new String[columnCountStatsN];
		String columnValueStatsH[] = new String[columnCountStatsH];
		
		
		try { //Get Stats
			String query = "SELECT * FROM tblPlayerStats WHERE UUID = ?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, uuid.toString());
			ResultSet rs = pst.executeQuery();
			rs.next();
			sender.sendMessage(ChatColor.GRAY + "================");
			sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "STATS");
			sender.sendMessage(ChatColor.RED + "=== General ===");
			for(int i = 1; i <= columnCountStats; i++) {
				columnValueStats[i-1] = rs.getString(i+2);
				sender.sendMessage(ChatColor.YELLOW + columnNameStats[i-1] + ": " + ChatColor.AQUA + columnValueStats[i-1]);
			}	
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
		
		try { //Get F Kills
			String query = "SELECT * FROM tblPlayerFKills WHERE UUID = ?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, uuid.toString());
			ResultSet rs = pst.executeQuery();
			rs.next();
			sender.sendMessage(ChatColor.RED + "=== Kills ===");
			sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[Friendly]");
			for(int i = 1; i <= columnCountStatsF; i++) {
				columnValueStatsF[i-1] = rs.getString(i+2);
				sender.sendMessage(ChatColor.YELLOW + columnNameStatsF[i-1] + ": " + ChatColor.AQUA + columnValueStatsF[i-1]);
			}
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
		
		try { //Get N Kills
			String query = "SELECT * FROM tblPlayerNKills WHERE UUID = ?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, uuid.toString());
			ResultSet rs = pst.executeQuery();
			rs.next();
			sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[Neutral]");
			for(int i = 1; i <= columnCountStatsN; i++) {
				columnValueStatsN[i-1] = rs.getString(i + 2);
				sender.sendMessage(ChatColor.YELLOW + columnNameStatsN[i-1] + ": " + ChatColor.AQUA + columnValueStatsN[i-1]);
			}
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
		
		try { //Get H Kills
			String query = "SELECT * FROM tblPlayerHKills WHERE UUID = ?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, uuid.toString());
			ResultSet rs = pst.executeQuery();
			rs.next();
			sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[Hostile]");
			for(int i = 1; i <= columnCountStatsH; i++) {
				columnValueStatsH[i-1] = rs.getString(i + 2);
				sender.sendMessage(ChatColor.YELLOW + columnNameStatsH[i-1] + ": " + ChatColor.AQUA + columnValueStatsH[i-1]);
			}
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}

		sender.sendMessage(ChatColor.GRAY + "================");
	}
	
}
