package ml.codenoodles.lmve.modules;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import ml.codenoodles.lmve.Main;

public class PlayerHeads implements Listener{

	private Main main;
	public PlayerHeads(Main main) {
		this.main = main;
	}
	
	private boolean checkIfTrigger() {
		int chance = main.getConfig().getInt("PlayerHeads.chance");;
		boolean trigger = false;
		Random rand = new Random();
		int n = rand.nextInt(100);
		n += 1;
		if(n < chance) {
			trigger = true;
		} else {
			trigger = false;
		}
		return trigger;
	}
	
	@EventHandler
	private void PlayerHeadDrop(PlayerDeathEvent e) {
		if(!main.getConfig().getBoolean("PlayerHeads.enabled")) {
			return;
		}
		boolean trigger = checkIfTrigger();
		if(trigger) {
			OfflinePlayer p = e.getEntity();
			ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
			SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Head of " + p.getName());
			skullMeta.setLore(lore);
			skullMeta.setOwningPlayer(p);
			head.setItemMeta(skullMeta);
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), head);
		}
	}
	
	
}
