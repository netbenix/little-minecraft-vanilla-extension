package ml.codenoodles.lmve.modules;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import ml.codenoodles.lmve.Main;

public class StatCounter implements Listener{

	private Main main;
	public StatCounter(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void BlockDestroyCounter(BlockBreakEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();		
		File playerStats = new File(main.getDataFolder() + "/Players", uuid + ".yml");
		FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
		long counter = player_stat.getInt(uuid + ".Statistics.Blocks.Destroyed");
		counter++;
		player_stat.set(uuid + ".Statistics.Blocks.Destroyed", counter); 
		try {
			player_stat.save(playerStats);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	private void BlockPlaceCounter(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();		
		File playerStats = new File(main.getDataFolder() + "/Players", uuid + ".yml");
		FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
		long counter = player_stat.getInt(uuid + ".Statistics.Blocks.Placed");
		counter++;
		player_stat.set(uuid + ".Statistics.Blocks.Placed", counter); 
		try {
			player_stat.save(playerStats);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	private void PlayerDamageCounter(EntityDamageEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof Player) {
			Player p = (Player) e.getEntity();
			if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
				UUID uuid = p.getUniqueId();		
				File playerStats = new File(main.getDataFolder() + "/Players", uuid + ".yml");
				FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
				double damage = player_stat.getDouble(uuid + ".Statistics.DamageTaken");
				damage = damage + e.getDamage();
				player_stat.set(uuid + ".Statistics.DamageTaken", damage);
				try {
					player_stat.save(playerStats);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
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
		UUID player_uuid;
		if(p != null) {
			player_uuid = p.getUniqueId();	
		} else {
			return;
		}
		File playerStats = new File(main.getDataFolder() + "/Players", player_uuid + ".yml");
		FileConfiguration player_stat = YamlConfiguration.loadConfiguration(playerStats);
		//Friendly
		int sheep, pig, chicken, bat, cat, cod, cow, donkey, fox, horse, mooshroom, mule,
		ocelot, parrot, rabbit,salmon, skeletonhorse, squid, tropicalfish, turtle, villager, wanderingtrader, snowgolem;	//PRE 1.15
		//Neutral
		int enderman, pufferfish, dolphin, llama, panda, polarBear, traderLlama, wolf, ironGolem, zombiePigman;	//PRE 1.15
		int bee;	//1.15 +
		//Hostile
		int blaze, creeper, drowned, elderGuardian, endermite, evoker, ghast, guardian, husk,
		magmaCube, phantom, pillager, ravager, shulker, silverfish, skeleton, slime,
		stray, vex, vindicator, witch, witherSkeleton, zombie, zombieVillager;	//PRE 1.15
		//Boss
		int enderDragon, wither;
		switch(ent_type) {
		case "SHEEP":{
			sheep = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Sheep");
			sheep++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Sheep", sheep);
			break;
		}
		case "PIG":{
			pig = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Pig");
			pig++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Pig", pig);
			break;
		}
		case "CHICKEN":{
			chicken = player_stat.getInt(player_uuid + "Statistics.Kills.Friendly.Chicken");
			chicken++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Chicken", chicken);
			break;
		}
		case "BAT":{
			bat = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Bat");
			bat++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Bat", bat);
			break;
		}
		case "CAT":{
			cat = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Cat");
			cat++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Cat", cat);
			break;
		}
		case "COD":{
			cod = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Cod");
			cod++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Cod", cod);
			break;
		}
		case "COW":{
			cow = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Cow");
			cow++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Cow", cow);
			break;
		}
		case "DONKEY":{
			donkey = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Donkey");
			donkey++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Donkey", donkey);
			break;
		}
		case "FOX":{
			fox = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Fox");
			fox++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Fox", fox);
			break;
		}
		case "HORSE":{
			horse = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Horse");
			horse++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Horse", horse);
			break;
		}
		case "MOOSHROOM":{
			mooshroom = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Mooshroom");
			mooshroom++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Mooshroom", mooshroom);
			break;
		}
		case "MULE":{
			mule = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Mule");
			mule++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Mule", mule);
			break;
		}
		case "OCELOT":{
			ocelot = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Ocelot");
			ocelot++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Ocelot", ocelot);
			break;
		}
		case "PARROT":{
			parrot = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Parrot");
			parrot++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Parrot", parrot);
			break;
		}
		case "RABBIT":{
			rabbit = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Rabbit");
			rabbit++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Rabbit", rabbit);
			break;
		}
		case "SALMON":{
			salmon = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Salmon");
			salmon++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Salmon", salmon);
			break;	
		}
		case "SKELETON_HORSE":{
			skeletonhorse = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Skeleton_Horse");
			skeletonhorse++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Skeleton_Horse", skeletonhorse);
			break;
		}
		case "SQUID":{
			squid = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Squid");
			squid++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Squid",squid);
			break;
		}
		case "TROPICAL_FISH":{
			tropicalfish = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Tropical_Fish");
			tropicalfish++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Tropical_Fish", tropicalfish);
			break;
		}
		case "TURTLE":{
			turtle = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Turtle");
			turtle++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Turtle", turtle);
			break;
		}
		case "VILLAGER":{
			villager = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Villager");
			villager++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Villager", villager);
			break;
		}
		case "WANDERING_TRADER":{
			wanderingtrader = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Wandering_Trader");
			wanderingtrader++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Wandering_Trader", wanderingtrader);
			break;
		}
		case "SNOWMAN":{
			snowgolem = player_stat.getInt(player_uuid + ".Statistics.Kills.Friendly.Snowman");
			snowgolem++;
			player_stat.set(player_uuid + ".Statistics.Kills.Friendly.Snowman", snowgolem);
			break;
		}
		case "ENDERMAN":{
			enderman = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Enderman");
			enderman++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Enderman", enderman);
			break;
		}
		case "PUFFERFISH":{
			pufferfish = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Pufferfish");
			pufferfish++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Pufferfish", pufferfish);
			break;
		}
		case "DOLPHIN":{
			dolphin = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Dolphin");
			dolphin++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Dolphin", dolphin);
			break;
		}
		case "LLAMA":{
			llama = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Llama");
			llama++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Llama", llama);
			break;
		}
		case "PANDA":{
			panda = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Panda");
			panda++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Panda", panda);
			break;
		}
		case "POLAR_BEAR":{
			polarBear = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Polar_Bear");
			polarBear++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Polar_Bear", polarBear);
			break;
		}
		case "TRADER_LLAMA":{
			traderLlama = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Trader_Llama");
			traderLlama++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Trader_Llama", traderLlama);
			break;
		}
		case "WOLF":{
			wolf = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Wolf");
			wolf++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Wolf", wolf);
			break;
		}
		case "IRON:GOLEM":{
			ironGolem = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Iron_Golem");
			ironGolem++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Iron_Golem", ironGolem); 
			break;
		}
		case "PIG_ZOMBIE":{
			zombiePigman = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Zombie_Pigman");
			zombiePigman++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Zombie_Pigman", zombiePigman);
			break;
		}
		case "BEE":{
			bee = player_stat.getInt(player_uuid + ".Statistics.Kills.Neutral.Bee");
			bee++;
			player_stat.set(player_uuid + ".Statistics.Kills.Neutral.Bee", bee);
			break;
		}
		case "BLAZE":{
			blaze = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Blaze");
			blaze++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Blaze", blaze);
			break;
		}
		case "CREEPER":{
			creeper = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Creeper");
			creeper++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Creeper", creeper);
			break;
		}
		case "DROWNED":{
			drowned = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Drowned");
			drowned++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Drowned", drowned);
			break;
		}
		case "ELDER_GUARDIAN":{
			elderGuardian = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Elder_Guardian");
			elderGuardian++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Elder_Guardian", elderGuardian);
			break;
		}
		case "ENDERMITE":{
			endermite = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Endermite");
			endermite++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Endermite", endermite);
			break;
		}
		case "EVOKER":{
			evoker = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Evoker");
			evoker++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Evoker", evoker);
			break;
		}
		case "GHAST":{
			ghast = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Ghast");
			ghast++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Ghast", ghast);
			break;
		}
		case "GUARDIAN":{
			guardian = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Guardian");
			guardian++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Guardian", guardian);
			break;
		}
		case "HUSK":{
			husk = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Husk");
			husk++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Husk", husk);
			break;
		}
		case "MAGMA_CUBE":{
			magmaCube = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Magma_Cube");
			magmaCube++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Magma_Cube", magmaCube);
			break;
		}
		case "PHANTOM":{
			phantom = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Phantom");
			phantom++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Phantom", phantom);
			break;
		}
		case "PILLAGER":{
			pillager = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Pillager");
			pillager++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Pillager", pillager);
			break;
		}
		case "RAVAGER":{
			ravager = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Ravager");
			ravager++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Ravager", ravager);
			break;
		}
		case "SHULKER":{
			shulker = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Shulker");
			shulker++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Shulker", shulker);
			break;
		}
		case "SILVERFISH":{
			silverfish = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Silverfish");
			silverfish++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Silverfish", silverfish);
			break;
		}
		case "SKELETON":{
			skeleton = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Skeleton");
			skeleton++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Skeleton", skeleton);
			break;
		}
		case "SLIME":{
			slime = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Slime");
			slime++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Slime", slime);
			break;
		}
		case "STRAY":{
			stray = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Stray");
			stray++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Stray", stray);
			break;
		}
		case "VEX":{
			vex = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Vex");
			vex++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Vex", vex);
			break;
		}
		case "VINDICATOR":{
			vindicator = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Vindicator");
			vindicator++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Vindicator", vindicator);
			break;
		}
		case "WITCH":{
			witch = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Witch");
			witch++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Witch", witch);
			break;
		}
		case "WITHER_SKELETON":{
			witherSkeleton = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Wither_Skeleton");
			witherSkeleton++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Wither_Skeleton", witherSkeleton);
			break;
		}
		case "ZOMBIE":{
			zombie = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Zombie");
			zombie++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Zombie", zombie);
			break;
		}
		case "ZOMBIE_VILLAGER":{
			zombieVillager = player_stat.getInt(player_uuid + ".Statistics.Kills.Hostile.Zombie_Villager");
			zombieVillager++;
			player_stat.set(player_uuid + ".Statistics.Kills.Hostile.Zombie_Villager", zombieVillager);
			break;
		}
		case "ENDER_DRAGON":{
			enderDragon = player_stat.getInt(player_uuid + ".Statistics.Kills.Boss.Ender_Dragon");
			enderDragon++;
			player_stat.set(player_uuid + ".Statistics.Kills.Boss.Ender_Dragon", enderDragon);
			break;
		}
		case "WITHER":{
			wither = player_stat.getInt(player_uuid + ".Statistics.Kills.Boss.Wither");
			wither++;
			player_stat.set(player_uuid + ".Statistics.Kills.Boss.Wither", wither);
			break;
		}
		}
		try {
			player_stat.save(playerStats);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
