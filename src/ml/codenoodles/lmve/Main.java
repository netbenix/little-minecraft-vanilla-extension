package ml.codenoodles.lmve;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ml.codenoodles.lmve.modules.CustomRecipes;
import ml.codenoodles.lmve.modules.EnragedMobs;
import ml.codenoodles.lmve.modules.Hardcore;
import ml.codenoodles.lmve.modules.Leaderboard;
import ml.codenoodles.lmve.modules.Messages;
import ml.codenoodles.lmve.modules.NetherPortal;
import ml.codenoodles.lmve.modules.PlayerHeads;
import ml.codenoodles.lmve.modules.PlayerList;
import ml.codenoodles.lmve.modules.PlayerStatistics;
import ml.codenoodles.lmve.modules.PreventMobGriefing;
import ml.codenoodles.lmve.modules.SQLHandler;
import ml.codenoodles.lmve.modules.SleepVoteSystem;
import ml.codenoodles.lmve.modules.StatCounter;
import ml.codenoodles.lmve.modules.UUIDReference;
import ml.codenoodles.lmve.other.ConsoleColor;
import ml.codenoodles.lmve.other.GlobalMute;
import ml.codenoodles.lmve.other.RemoveVanillaRecipes;
import ml.codenoodles.lmve.other.WelcomeBossBar;
import ml.codenoodles.lmve.sounds.ChatNotification;



public class Main
extends JavaPlugin
implements Listener
{
	FileConfiguration cfg = this.getConfig();
    NamespacedKey Nkey = new NamespacedKey(this, this.getDescription().getName());
    public SQLHandler sql = new SQLHandler();
    public static int DB_VER = 1;
	public void onEnable() {
		this.saveDefaultConfig();
		registerEvents();
		File PlayerDB = new File(getDataFolder().getAbsoluteFile() + "/" + "Players.db");
		if(!PlayerDB.exists()) {
			sql.createDefaultDatabase(getDataFolder().getAbsolutePath());
		}
		System.out.println(ConsoleColor.BLUE +  "[LMVE]Plugin Enabled!" + ConsoleColor.RESET);
	}

	
	public void onDisable() {
		System.out.println(ConsoleColor.BLUE + "[LMVE]Plugin Disabled!" + ConsoleColor.RESET);
	}
	
	public void registerEvents() {
		//Modules
		this.getServer().getPluginManager().registerEvents(new CustomRecipes(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerStatistics(this), this);
		this.getServer().getPluginManager().registerEvents(new StatCounter(this), this);
		this.getServer().getPluginManager().registerEvents(new Messages(this), this);
		this.getServer().getPluginManager().registerEvents(new Hardcore(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerList(this), this);
		this.getServer().getPluginManager().registerEvents(new SleepVoteSystem(this), this);
		this.getServer().getPluginManager().registerEvents(new UUIDReference(this), this);
		this.getServer().getPluginManager().registerEvents(new EnragedMobs(this), this);
		this.getServer().getPluginManager().registerEvents(new PreventMobGriefing(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerHeads(this), this);
		//Sounds
		this.getServer().getPluginManager().registerEvents(new ChatNotification(this), this);
		//Other Stuff
		this.getServer().getPluginManager().registerEvents(new NetherPortal(this), this);
		this.getServer().getPluginManager().registerEvents(new GlobalMute(this), this);
		this.getServer().getPluginManager().registerEvents(new RemoveVanillaRecipes(this), this);
		this.getServer().getPluginManager().registerEvents(new WelcomeBossBar(this), this);
	}

	//Getter
	public FileConfiguration getConfigFile() {
		return cfg;
	}

	//////////  RVC = RemoveVanillaRecipes
	// VARS //
	//////////
	public boolean RVC_magmablock = cfg.getBoolean("RemoveVanillaRecipes.magma-block");
	public boolean snd_chatnotify = cfg.getBoolean("Sounds.chat");
	//////////////
	// Commands //
	//////////////
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if(cmd.getName().equalsIgnoreCase("lmve")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.GRAY + "================");
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "LMVE MAIN COMMANDS");
				sender.sendMessage(ChatColor.RED + "/lmve info" + ChatColor.WHITE + " - Shows the plugin information");
				sender.sendMessage(ChatColor.RED + "/lmve stats" + ChatColor.WHITE + " - Shows the Player Stats");
				sender.sendMessage(ChatColor.RED + "/lmve leaderboard" + ChatColor.WHITE + " - Shows the Leaderboard Infos");
				sender.sendMessage(ChatColor.RED + "/lmve settings" + ChatColor.WHITE + " - Shows the Player Settings");
				sender.sendMessage(ChatColor.RED + "/lmve version" + ChatColor.WHITE + " - Shows the plugin version");
				sender.sendMessage(ChatColor.GRAY + "================");
				return true;
			}

			if(args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.GRAY + "================");
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "LMVE INFO");
				sender.sendMessage(ChatColor.YELLOW + "Name: " +
						ChatColor.RED + "L" + ChatColor.WHITE + "ittle " +
						ChatColor.RED + "M" + ChatColor.WHITE + "inecraft " +
						ChatColor.RED + "V" + ChatColor.WHITE + "anilla " +
						ChatColor.RED + "E" + ChatColor.WHITE + "xtension"
						);
				sender.sendMessage(ChatColor.YELLOW + "Author: " + ChatColor.WHITE + "netbenix");
				sender.sendMessage(ChatColor.GRAY + "================");
				return true;
			}

			if(args[0].equalsIgnoreCase("stats")) {
				if(args.length == 1) {
					sender.sendMessage(ChatColor.GRAY + "================");
					sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "LMVE STATS");
					sender.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.AQUA + "/lmve stats <sub-command>");
					sender.sendMessage(ChatColor.YELLOW + "Sub-Commands:");
					sender.sendMessage(ChatColor.RED + "show" + ChatColor.WHITE + " - Shows your Stats");
					sender.sendMessage(ChatColor.RED + "show <player>" + ChatColor.WHITE + " - Shows another players Stats");

					sender.sendMessage(ChatColor.GRAY + "================");
					return true;
				}

				if(args[1].equalsIgnoreCase("show")) {
					if(args.length >= 4) {
						sender.sendMessage(ChatColor.RED + "Too many Arguments.");
						return true;
					}
					if(args.length == 2) {
						if(!(sender instanceof Player)) {
							sender.sendMessage("Please use: /lmve stats show <player>");
							return true;
						}
						Player p = (Player) sender;
						PlayerStatistics pStats = new PlayerStatistics(this);
						UUIDReference uuidRef = new UUIDReference(this);
						UUID uuid = uuidRef.getUUID(p.getName());
						pStats.outputStats(sender, uuid);
					}
					if(args.length == 3) {
						PlayerStatistics pStats = new PlayerStatistics(this);
						UUIDReference uuidRef = new UUIDReference(this);
						UUID uuid;
						if(uuidRef.getUUID(args[2]) == null) {
							sender.sendMessage(ChatColor.RED + "Player was never on this Server!");
							return true;
						} else {
							uuid = uuidRef.getUUID(args[2]);
						}
						pStats.outputStats(sender, uuid);
					}

					return true;
				}
				return true;
			}

			if(args[0].equalsIgnoreCase("leaderboard")){
				if(args.length == 1){
					sender.sendMessage(ChatColor.GRAY + "================");
					sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "LMVE LEADERBOARD INFO");
					sender.sendMessage(ChatColor.YELLOW + "Usage : " + ChatColor.AQUA + "/lmve leaderboard <category>");
					sender.sendMessage(ChatColor.YELLOW + "Categories: ");
					sender.sendMessage(ChatColor.RED + "time-played");
					sender.sendMessage(ChatColor.RED + "deaths");
					sender.sendMessage(ChatColor.RED + "damage-taken");
					return true;
				}
				if(args.length > 2){
					sender.sendMessage(ChatColor.RED + "Too many Arguments.");
					return true;
				}
				if(args.length == 2){
					PlayerList playerList = new PlayerList(this);
					int totalPlayers = 0;
					totalPlayers = playerList.getAmount();
					Leaderboard lb = new Leaderboard(this);
					lb.show(sender, totalPlayers, args);
				}
				return true;
			}
			
			//SQL REWORK
			if(args[0].equalsIgnoreCase("settings")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("Command is only for Players!");
					return true;
				}
				if(args.length == 1) {
					Player p = (Player) sender;
					File playerStat = new File(getDataFolder() + "/Players", p.getUniqueId() + ".yml");
					@SuppressWarnings("static-access")
					FileConfiguration player_stat = new YamlConfiguration().loadConfiguration(playerStat);
					sender.sendMessage(ChatColor.GRAY + "================");
					sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "LMVE SETTINGS");
					sender.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.AQUA + "/lmve settings <config-string>");
					sender.sendMessage(ChatColor.YELLOW + "Your Settings: ");
					sender.sendMessage(ChatColor.RED+ "ChatNotify: " + ChatColor.WHITE + player_stat.getBoolean(p.getUniqueId() + ".Settings.Sounds.ChatNotify"));
					sender.sendMessage(ChatColor.GRAY + "================");
					return true;
				}
				if(args.length > 2) {
					sender.sendMessage(ChatColor.RED + "Too many Arguments.");
					return true;
				}
				if(args[1].equalsIgnoreCase("ChatNotify")) {
					Player p = (Player) sender;
					File PlayerStat = new File(getDataFolder() + "/Players", p.getUniqueId() + ".yml");
					@SuppressWarnings("static-access")
					FileConfiguration player_stat = new YamlConfiguration().loadConfiguration(PlayerStat);
					if(player_stat.getBoolean(p.getUniqueId() + ".Settings.Sounds.ChatNotify")) {
						player_stat.set(p.getUniqueId() + ".Settings.Sounds.ChatNotify", false);
						sender.sendMessage(ChatColor.YELLOW + "Setting now changed to: " + ChatColor.RED + "false");
					} else {
						player_stat.set(p.getUniqueId() + ".Settings.Sounds.ChatNotify", true);
						sender.sendMessage(ChatColor.YELLOW + "Setting now changed to: " + ChatColor.RED + "true");
					}
					try {
						player_stat.save(PlayerStat);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			}

			if(args[0].equalsIgnoreCase("version")) {
				sender.sendMessage(ChatColor.GRAY + "Current " + ChatColor.GOLD + "" + ChatColor.BOLD + "LMVE" + ChatColor.GRAY + " version running: " + ChatColor.RED + this.getDescription().getVersion());
				return true;
			}
			return true;
		}


		return false;
	}
}
