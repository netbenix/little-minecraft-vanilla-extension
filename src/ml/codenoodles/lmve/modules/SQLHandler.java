package ml.codenoodles.lmve.modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import ml.codenoodles.lmve.other.ConsoleColor;

public class SQLHandler {

	public SQLHandler() {
	}
	
	public void updateKillCounter(int table, String entity, UUID uuid, String dbPath) {
		Connection conn = null;
		String tableName = null;
		
		switch(table){
			case 0:{
				tableName = "tblPlayerStats";
				break;
			}
			case 1:{
				tableName = "tblPlayerFKills";
				break;
			}
			case 2:{
				tableName = "tblPlayerNKills";
				break;
			}
			case 3:{
				tableName = "tblPlayerHKills";
				break;
			}
			default:{
				tableName = "NULL";
				break;
			}
		}
		String query = "UPDATE " + tableName + " SET " + entity + " = ( " + entity + " + 1) WHERE UUID ='" + uuid.toString() + "';";
		try { //Try connection and exec query
			conn = DriverManager.getConnection(dbPath);
			Statement stmt = conn.createStatement();
			stmt.execute(query);
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
		
		try { //Try connection close
			conn.close();
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
	}
	
	
	public void createDefaultDatabase(String dbPath) {
		Connection conn;
		int successfullQuerys = 0;
		
		String finalPath = "jdbc:sqlite:" + dbPath + "/" + "Players.db";
		try { //Try Connection
			conn = DriverManager.getConnection(finalPath);
			if(conn != null) {
				System.out.println(ConsoleColor.PURPLE + "[LMVE]Connected to Database!" + ConsoleColor.RESET);
			}
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
			return;
		}
		
		try { //Create Playertable
			String query = "CREATE TABLE tblPlayers(" 
					+ "ID INTEGER PRIMARY KEY, "
					+ "Name TEXT NOT NULL,"
					+ "UUID TEXT);";
			Statement stmt = conn.createStatement();
			stmt.execute(query);
			System.out.println(ConsoleColor.PURPLE + "[LMVE]Player Table created!" + ConsoleColor.RESET);
			successfullQuerys++;
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
		}
		
		try { //Create PlayerStatsTable
			String query = "CREATE TABLE tblPlayerStats("
					+ "ID INTEGER PRIMARY KEY, " + "UUID TEXT NOT NULL," 
					+ "Displayname TEXT," + "Health REAL,"
					+ "World TEXT,"
					+ "FirstJoined TEXT," + "LastJoined TEXT,"
					+ "Deaths INTEGER," + "DamageTaken REAL);";
			Statement stmt = conn.createStatement();
			stmt.execute(query);
			System.out.println(ConsoleColor.PURPLE + "[LMVE]Player Stats Table created!" + ConsoleColor.RESET);
			successfullQuerys++;
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
		}
		
		try { //Create FriendlyKillTable
			String query = "CREATE TABLE tblPlayerFKills("
					+ "ID INTEGER PRIMARY KEY," + "UUID TEXT NOT NULL,"
					+ "Sheep INTEGER, " + "Pig INTEGER, " + "Chicken INTEGER, "
					+ "Bat INTEGER, " + "Cat INTEGER, " + "Cod INTEGER, "
					+ "Cow INTEGER, " + "Donkey INTEGER, " + "Fox INTEGER, "
					+ "Horse INTEGER, " + "Mooshroom INTEGER, " + "Mule INTEGER, "
					+ "Ocelot INTEGER, " + "Parrot INTEGER, " + "Rabbit INTEGER, "
					+ "Salmon INTEGER, " + "Skeleton_Horse INTEGER, " + "Squid INTEGER, "
					+ "Tropical_Fish INTEGER, " + "Turtle INTEGER, " + "Villager INTEGER, "
					+ "Wandering_Trager INTEGER, " + "Snowman INTEGER);";
			Statement stmt = conn.createStatement();
			stmt.execute(query);
			System.out.println(ConsoleColor.PURPLE + "[LMVE]Player KillStats F Table created!" + ConsoleColor.RESET);
			successfullQuerys++;
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
		
		try { //Create NeutralKillTable
			String query = "CREATE TABLE tblPlayerNKills("
					+ "ID INTEGER PRIMARY KEY," + "UUID TEXT NOT NULL,"
					+ "Enderman INTEGER, " + "Pufferfish INTEGER, " + "Dolphin INTEGER, "
					+ "Llama INTEGER, " + "Panda INTEGER, " + "Polar_Bear INTEGER,"
					+ "Trader_Llama INTEGER, " + "Wolf INTEGER, " + "Iron_Golem INTEGER,"
					+ "Zombie_Pigman INTEGER, " + "Bee INTEGER);";
			Statement stmt = conn.createStatement();
			stmt.execute(query);
			System.out.println(ConsoleColor.PURPLE + "[LMVE]Player KillStats N Table created!" + ConsoleColor.RESET);
			successfullQuerys++;
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
		
		try { //Create HostileKillTable
			String query = "CREATE TABLE tblPlayerHKills("
					+ "ID INTEGER PRIMARY KEY," + "UUID TEXT NOT NULL,"
					+ "Blaze INTEGER, " + "Creeper INTEGER, " + "Drowned INTEGER, "
					+ "Elder_Guardian INTEGER, " + "Endermite INTEGER, " + "Evoker INTEGER, "
					+ "Ghast INTEGER, " + "Guardian INTEGER, " + "Husk INTEGER, "
					+ "Magma_Cube INTEGER, " + "Phantom INTEGER, " + "Pillager INTEGER, "
					+ "Ravager INTEGER, " + "Shulker INTEGER, " + "Silverfish INTEGER, "
					+ "Skeleton INTEGER, " + "Slime INTEGER, " + "Stray INTEGER, "
					+ "Vex INTEGER, " + "Vindicator INTEGER, " + "Witch INTEGER, "
					+ "Wither_Skeleton INTEGER, " + "Zombie INTEGER, " + "Zombie_Villager INTEGER);";
			Statement stmt = conn.createStatement();
			stmt.execute(query);
			System.out.println(ConsoleColor.PURPLE + "[LMVE]Player KillStats H Table created!" + ConsoleColor.RESET);
			successfullQuerys++;
		}catch(SQLException sqlEx) {
			System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
		}
		
		try { //Close Connection
			conn.close();
		}catch(SQLException sqlEx) {
			System.out.println("[LMVE]" + sqlEx.getMessage());
		}
		
		if(successfullQuerys == 5) {
			System.out.println(ConsoleColor.GREEN + "[LMVE]Default Database successfully created!" + ConsoleColor.RESET);
		} else {
			System.out.println(ConsoleColor.RED + "[LMVE]Oops.. Something went wrong during the creation of the Default Database." + ConsoleColor.RESET);
		}
		
	}
	
}
