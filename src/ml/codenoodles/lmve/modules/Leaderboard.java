package ml.codenoodles.lmve.modules;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ml.codenoodles.lmve.Main;
import ml.codenoodles.lmve.other.ConsoleColor;

public class Leaderboard {
	
	private Main main;
	public Leaderboard(Main main) {
		this.main = main;
	}
	
	
	public void show(CommandSender sender, int totalPlayers, String[] args) {

		Connection conn;
		int playerAmount = totalPlayers;
		int unknown = 0;
		String player[] = new String[playerAmount];
		UUID uuid[] = new UUID[playerAmount];
		String path = "jdbc:sqlite:" + main.getDataFolder().getAbsolutePath() + "/" + "Players.db";
		try {//Try Connection
			conn = DriverManager.getConnection(path);
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
			return;
		}
		for(int i = 1; i <= totalPlayers; i++) {
			try { //Get Players
				String query = "SELECT Name AS playerName, UUID AS uuid FROM tblPlayers WHERE ID = ?";
				PreparedStatement pst = conn.prepareStatement(query);
				pst.setLong(1, i);
				ResultSet rs = pst.executeQuery();
				rs.next();
				player[i-1] = rs.getString("playerName");
				uuid[i-1] = UUID.fromString(rs.getString("uuid"));
				rs.close();
			}catch(SQLException sqlEx) {
				System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
				return;
			}
		}
		
		float value[] = new float[playerAmount];
		sender.sendMessage(ChatColor.GRAY + "=====[ LEADERBOARD ]=====");
		switch(args[1]){
			case "time-played":{
				sender.sendMessage(ChatColor.GRAY + "Category: " + ChatColor.AQUA + "Time Played" + ChatColor.GRAY + "(in hours)");
				for(int i = 1; i < playerAmount; i++) {
					File playerFile = new File(main.getDataFolder() + "/Players", uuid[i] + ".yml");
					FileConfiguration playerStat = YamlConfiguration.loadConfiguration(playerFile);
					value[i] = playerStat.getInt(uuid[i] + ".TimePlayed");
				}
				break;
			}
			case "deaths":{
				sender.sendMessage(ChatColor.GRAY + "Category: " + ChatColor.AQUA + "Deaths");
				for(int i = 1; i <= playerAmount; i++) {
					try {
						String query = "SELECT Deaths AS count FROM tblPlayerStats WHERE UUID = ?;";
						PreparedStatement pst = conn.prepareStatement(query);
						pst.setString(1, uuid[i-1].toString());
						ResultSet rs = pst.executeQuery();
						rs.next();
						value[i-1] = rs.getLong("count");
						rs.close();
					}catch(SQLException sqlEx) {
						System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
						return;
					}
				}	
				break;
			}
			case "damage-taken":{
				sender.sendMessage(ChatColor.GRAY + "Category: " + ChatColor.AQUA + "Damage Taken");
				for(int i = 1; i <= playerAmount; i++) {
					try {
						String query = "SELECT DamageTaken AS count FROM tblPlayerStats WHERE UUID = ?;";
						PreparedStatement pst = conn.prepareStatement(query);
						pst.setString(1, uuid[i-1].toString());
						ResultSet rs = pst.executeQuery();
						rs.next();
						value[i-1] = rs.getLong("count");
						rs.close();
					}catch(SQLException sqlEx) {
						System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
						return;
					}
				}	
				break;
			}
			default:{
				sender.sendMessage(ChatColor.RED + "Category unknown.");
				unknown = 1;
				break;
			}
		}
		String temp_str;
	    float temp = 0;  
	    if(unknown == 0) {
	    	  for(int i=0; i < playerAmount; i++){  
	                 for(int j=1; j < (playerAmount-i); j++){  
	                          if(value[j-1] < value[j]){  
	                                 temp = value[j-1];  
	                                 temp_str = player[j-1];
	                                 value[j-1] = value[j]; 
	                                 player[j-1] = player[j];
	                                 value[j] = temp;
	                                 player[j] = temp_str;
	                         }                 
	                 }  
	         }
	         for(int i = 0; i < playerAmount; i++) {
					sender.sendMessage(ChatColor.YELLOW + player[i] + ChatColor.WHITE + " - " + ChatColor.RED + value[i]);
				}
	    }   
			sender.sendMessage(ChatColor.GRAY + "================");
         
	}
}
