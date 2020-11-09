package ml.codenoodles.lmve.modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ml.codenoodles.lmve.Main;

public class UUIDReference implements Listener{

	private Main main;
	public UUIDReference(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void CreateEntry(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		String dbPath = main.getDataFolder().getAbsolutePath();
		String playerName = p.getName();
		String uuid = p.getUniqueId().toString();
		
		Connection conn = null;
		boolean entryExists = false;
		String path = "jdbc:sqlite:" + dbPath + "/" + "Players.db";
		try { //Try Connection
			conn = DriverManager.getConnection(path);
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
			return;
		}
		
		try { //Check if Player is already listed
			String query = "SELECT (COUNT(*) > 0) FROM tblPlayers WHERE UUID = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, uuid);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				boolean found = rs.getBoolean(1);
				if(found) {
					entryExists = true;
				} else {
					entryExists = false;
				}
			}
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
			return;
		}
		
		if(entryExists) {	
			
			try { //Change Existing Entry
				String query = "UPDATE tblPlayers SET Name = ? WHERE UUID = ?";
				PreparedStatement pst = conn.prepareStatement(query);
				pst.setString(1, playerName);
				pst.setString(2, uuid);
				pst.execute();
			}catch(SQLException sqlEx) {
				System.out.println("[LMVE]" + sqlEx.getMessage());
				return;
			}
		} else {
			int totalPlayers = 0;
			try { //Get Total Players
				Statement stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM tblPlayers");
				
				res.next();
				totalPlayers = res.getInt("rowcount");
				res.close();
			}catch(SQLException sqlEx) {
				System.out.println("[LMVE]" + sqlEx.getMessage());
				return;
			}
			
			try { //Add Entry for new Player
				Statement stmt = conn.createStatement();
				String query = "INSERT INTO tblPlayers VALUES ("
						+ (totalPlayers + 1) + ","
						+ "'" + playerName + "',"
						+ "'" + uuid + "');";
				stmt.execute(query);
			}catch(SQLException sqlEx) {
				System.out.println("[LMVE]" + sqlEx.getMessage());
			}
			
			try { //Close Connection
				conn.close();
			}catch(SQLException sqlEx) {
				System.out.println("[LMVE]" + sqlEx.getMessage());
			}
		}
	}
	
	public UUID getUUID(String username) {
		UUID uuid;
		Connection conn;
		String finalPath = "jdbc:sqlite:" + main.getDataFolder().getAbsolutePath() + "/" + "Players.db";
		try { //Try connection
			conn = DriverManager.getConnection(finalPath);
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
			return null;
		}
		
		try { //Get Player
			String query = "SELECT UUID AS uuid FROM tblPlayers WHERE Name = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, username);
			ResultSet res = pst.executeQuery();
			res.next();
			uuid = UUID.fromString(res.getString("uuid"));
			res.close();
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
			return null;
		}
		
		try { //Close connection
			conn.close();
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx);
		}
		
		return uuid;
	}
	
}
