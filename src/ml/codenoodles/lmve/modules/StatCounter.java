package ml.codenoodles.lmve.modules;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import ml.codenoodles.lmve.Main;
import ml.codenoodles.lmve.other.ConsoleColor;

public class StatCounter implements Listener{

	private Main main;
	public StatCounter(Main main) {
		this.main = main;
	}
	

	@EventHandler
	private void PlayerDamageCounter(EntityDamageEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof Player) {
			Player p = (Player) e.getEntity();
			if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
				UUID uuid = p.getUniqueId();
				
				Connection conn = null;
				String path = "jdbc:sqlite:" + main.getDataFolder().getAbsolutePath() + "/" + "Players.db";
				String query = "UPDATE tblPlayerStats SET DamageTaken = (DamageTaken + " + e.getDamage() + ") WHERE UUID = " + uuid.toString() + "';"; 
				try {
					conn = DriverManager.getConnection(path);
					Statement stmt = conn.createStatement();
					stmt.execute(query);
				}catch(SQLException sqlEx) {
					System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
				}
				
				try {
					conn.close();
				}catch(SQLException sqlEx) {
					System.out.println(ConsoleColor.RED + "[LMVE]" + sqlEx.getMessage() + ConsoleColor.RESET);
				}
			}		
		}
	}
	
	@EventHandler
	private void PlayerDeathCounter(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		UUID uuid = p.getUniqueId();		
		File playerStats = new File(main.getDataFolder() + "/Players", uuid + ".yml");
		FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
		int deaths = player_stat.getInt(uuid + ".Statistics.Deaths");
		deaths = deaths + 1;
		player_stat.set(uuid + ".Statistics.Deaths", deaths);
		try {
			player_stat.save(playerStats);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	private void EntityKillCounter(EntityDeathEvent e) {
		String ent_type = e.getEntity().getType().toString();
		Player p = (Player) e.getEntity().getKiller();
		UUID uuid;
		SQLHandler sql = new SQLHandler();
		if(p != null) {
			uuid = p.getUniqueId();	
		} else {
			return;
		}
		
		String dbPath = "jdbc:sqlite:" + main.getDataFolder().getAbsolutePath() + "/" + "Players.db";
	
		switch(ent_type) {
		case "SHEEP":{
			sql.updateKillCounter(1, "Sheep", uuid, dbPath);
			break;
		}
		case "PIG":{
			sql.updateKillCounter(1, "Pig", uuid, dbPath);
			break;
		}
		case "CHICKEN":{
			sql.updateKillCounter(1, "Chicken", uuid, dbPath);
			break;
		}
		case "BAT":{
			sql.updateKillCounter(1, "Bat", uuid, dbPath);
			break;
		}
		case "CAT":{
			sql.updateKillCounter(1, "Cat", uuid, dbPath);
			break;
		}
		case "COD":{
			sql.updateKillCounter(1, "Cod", uuid, dbPath);
			break;
		}
		case "COW":{
			sql.updateKillCounter(1, "Cow", uuid, dbPath);
			break;
		}
		case "DONKEY":{
			sql.updateKillCounter(1, "Donkey", uuid, dbPath);
			break;
		}
		case "FOX":{
			sql.updateKillCounter(1, "Fox", uuid, dbPath);
			break;
		}
		case "HORSE":{
			sql.updateKillCounter(1, "Horse", uuid, dbPath);
			break;
		}
		case "MOOSHROOM":{
			sql.updateKillCounter(1, "Mooshroom", uuid, dbPath);
			break;
		}
		case "MULE":{
			sql.updateKillCounter(1, "Mule", uuid, dbPath);
			break;
		}
		case "OCELOT":{
			sql.updateKillCounter(1, "Ocelot", uuid, dbPath);
			break;
		}
		case "PARROT":{
			sql.updateKillCounter(1, "Parrot", uuid, dbPath);
			break;
		}
		case "RABBIT":{
			sql.updateKillCounter(1, "Rabbit", uuid, dbPath);
			break;
		}
		case "SALMON":{
			sql.updateKillCounter(1, "Salmon", uuid, dbPath);
			break;	
		}
		case "SKELETON_HORSE":{
			sql.updateKillCounter(1, "Skeleton_Horse", uuid, dbPath);
			break;
		}
		case "SQUID":{
			sql.updateKillCounter(1, "Squid", uuid, dbPath);
			break;
		}
		case "TROPICAL_FISH":{
			sql.updateKillCounter(1, "Tropical_Fish", uuid, dbPath);
			break;
		}
		case "TURTLE":{
			sql.updateKillCounter(1, "Turtle", uuid, dbPath);
			break;
		}
		case "VILLAGER":{
			sql.updateKillCounter(1, "Villager", uuid, dbPath);
			break;
		}
		case "WANDERING_TRADER":{
			sql.updateKillCounter(1, "Wandering_Trader", uuid, dbPath);
			break;
		}
		case "SNOWMAN":{
			sql.updateKillCounter(1, "Snowman", uuid, dbPath);
			break;
		}
		case "ENDERMAN":{
			sql.updateKillCounter(2, "Enderman", uuid, dbPath);
			break;
		}
		case "PUFFERFISH":{
			sql.updateKillCounter(2, "Pufferfish", uuid, dbPath);
			break;
		}
		case "DOLPHIN":{
			sql.updateKillCounter(2, "Dolphin", uuid, dbPath);
			break;
		}
		case "LLAMA":{
			sql.updateKillCounter(2, "Llama", uuid, dbPath);
			break;
		}
		case "PANDA":{
			sql.updateKillCounter(2, "Panda", uuid, dbPath);
			break;
		}
		case "POLAR_BEAR":{
			sql.updateKillCounter(2, "Polar_Bear", uuid, dbPath);
			break;
		}
		case "TRADER_LLAMA":{
			sql.updateKillCounter(2, "Trader_Llama", uuid, dbPath);
			break;
		}
		case "WOLF":{
			sql.updateKillCounter(2, "Wolf", uuid, dbPath);
			break;
		}
		case "IRON_GOLEM":{
			sql.updateKillCounter(2, "Iron_Golem", uuid, dbPath);
			break;
		}
		case "PIG_ZOMBIE":{
			sql.updateKillCounter(2, "Pig_Zombie", uuid, dbPath);
			break;
		}
		case "BEE":{
			sql.updateKillCounter(2, "Bee", uuid, dbPath);
			break;
		}
		case "BLAZE":{
			sql.updateKillCounter(3, "Blaze", uuid, dbPath);
			break;
		}
		case "CREEPER":{
			sql.updateKillCounter(3, "Creeper", uuid, dbPath);
			break;
		}
		case "DROWNED":{
			sql.updateKillCounter(3, "Drowned", uuid, dbPath);
			break;
		}
		case "ELDER_GUARDIAN":{
			sql.updateKillCounter(3, "Elder_Guardian", uuid, dbPath);
			break;
		}
		case "ENDERMITE":{
			sql.updateKillCounter(3, "Endermite", uuid, dbPath);
			break;
		}
		case "EVOKER":{
			sql.updateKillCounter(3, "Evoker", uuid, dbPath);
			break;
		}
		case "GHAST":{
			sql.updateKillCounter(3, "Ghast", uuid, dbPath);
			break;
		}
		case "GUARDIAN":{
			sql.updateKillCounter(3, "Guardian", uuid, dbPath);
			break;
		}
		case "HUSK":{
			sql.updateKillCounter(3, "Husk", uuid, dbPath);
			break;
		}
		case "MAGMA_CUBE":{
			sql.updateKillCounter(3, "Magma_Cube", uuid, dbPath);
			break;
		}
		case "PHANTOM":{
			sql.updateKillCounter(3, "Phantom", uuid, dbPath);
			break;
		}
		case "PILLAGER":{
			sql.updateKillCounter(3, "Pillager", uuid, dbPath);
			break;
		}
		case "RAVAGER":{
			sql.updateKillCounter(3, "Ravager", uuid, dbPath);
			break;
		}
		case "SHULKER":{
			sql.updateKillCounter(3, "Shulker", uuid, dbPath);
			break;
		}
		case "SILVERFISH":{
			sql.updateKillCounter(3, "Silverfish", uuid, dbPath);
			break;
		}
		case "SKELETON":{
			sql.updateKillCounter(3, "Skeleton", uuid, dbPath);
			break;
		}
		case "SLIME":{
			sql.updateKillCounter(3, "Slime", uuid, dbPath);
			break;
		}
		case "STRAY":{
			sql.updateKillCounter(3, "Stray", uuid, dbPath);
			break;
		}
		case "VEX":{
			sql.updateKillCounter(3, "Vex", uuid, dbPath);
			break;
		}
		case "VINDICATOR":{
			sql.updateKillCounter(3, "Vindicator", uuid, dbPath);
			break;
		}
		case "WITCH":{
			sql.updateKillCounter(3, "Witch", uuid, dbPath);
			break;
		}
		case "WITHER_SKELETON":{
			sql.updateKillCounter(3, "Wither_Skeleton", uuid, dbPath);
			break;
		}
		case "ZOMBIE":{
			sql.updateKillCounter(3, "Zombie", uuid, dbPath);
			break;
		}
		case "ZOMBIE_VILLAGER":{
			sql.updateKillCounter(3, "Zombie_Villager", uuid, dbPath);
			break;
		}
		}
	}
}
