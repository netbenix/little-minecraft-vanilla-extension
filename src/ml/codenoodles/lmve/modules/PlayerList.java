package ml.codenoodles.lmve.modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.event.Listener;

import ml.codenoodles.lmve.Main;

public class PlayerList implements Listener{

	private Main main;
	public PlayerList(Main main) {
		this.main = main;
	}
		
	public int getAmount() {
		int totalPlayers;
		Connection conn;
		String path = "jdbc:sqlite:" + main.getDataFolder().getAbsolutePath() + "/" + "Players.db";
		try { //Try Connection
			conn = DriverManager.getConnection(path);
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
			return 0;
		}
		
		try { //Count Players
			String query = "SELECT COUNT(Name) AS totalPlayers FROM tblPlayers;";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			totalPlayers = rs.getInt("totalPlayers");
			rs.close();
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
			return 0;
		}
		
		return totalPlayers;
	}
	
}
