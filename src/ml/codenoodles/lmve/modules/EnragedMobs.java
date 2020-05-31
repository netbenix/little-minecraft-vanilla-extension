package ml.codenoodles.lmve.modules;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ml.codenoodles.lmve.Main;


public class EnragedMobs implements Listener{

	private Main main;
	public EnragedMobs(Main main) {
		this.main = main;
	}
	
	private boolean checkIfTrigger() {
		int chance = main.getConfig().getInt("EnragedMobs.chance");;
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
	
	private boolean checkCustomName(String name) {
		if(name == null) {
			return false;
		}
		if(name.startsWith("Enraged")) {
			return true; 
		} else {
			return false;
		}
	}
	
	@EventHandler
	private void ChangeExpDrop(EntityDeathEvent e) {
		if(!main.getConfig().getBoolean("EnragedMobs.enabled")) {
			return;
		}
		if(e.getEntity().getCustomName() == null) { return; }
		if(e.getEntity().getCustomName().startsWith("Enraged")) {
			e.setDroppedExp(e.getDroppedExp() * 2);
		}	
	}
	
	@EventHandler
	private void TransformToEnraged(EntityDamageByEntityEvent e) {
		if(!main.getConfig().getBoolean("EnragedMobs.enabled")) {
			return;
		}
		if(e.getDamager().getType() != EntityType.PLAYER) {
			return;
		}
		ArrayList<Material> triggerItems = new ArrayList<Material>();
		triggerItems.add(Material.IRON_SWORD);
		triggerItems.add(Material.IRON_AXE);
		triggerItems.add(Material.DIAMOND_SWORD);
		triggerItems.add(Material.DIAMOND_AXE);
		boolean correctItem = false;
		for(int i = 0; i < triggerItems.size(); i++) {
			Player p = (Player) e.getDamager();
			if(p.getInventory().getItemInMainHand().getType() == triggerItems.get(i)) {
				correctItem = true;
			}
		}
		if(!correctItem) {
			return;
		}
		
		boolean causeTrigger = false;
		switch(e.getCause()) {
		case ENTITY_ATTACK: { causeTrigger = true; break;}
		case ENTITY_SWEEP_ATTACK: { causeTrigger = true; break;}
		case PROJECTILE: { causeTrigger = true; break;}
		case THORNS: { causeTrigger = true; break;}
		default:{ causeTrigger = false; break;}
		}
		if(causeTrigger) {
			switch(e.getEntity().getType()) {
			case ZOMBIE:{
				Zombie zombie = (Zombie) e.getEntity();
				boolean trigger = checkIfTrigger();
				if(trigger) {
					if(!checkCustomName(zombie.getCustomName())) {
						World world = zombie.getWorld();
						world.strikeLightningEffect(zombie.getLocation());
						zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 3));
						zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 1));
						zombie.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 60, 1));
						zombie.setHealth(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						zombie.setCustomName("Enraged Zombie");
						zombie.setCustomNameVisible(true);
					}
				}
				return;
			}
			case PIG_ZOMBIE:{
				PigZombie pigzombie = (PigZombie) e.getEntity();
				boolean trigger = checkIfTrigger();
				if(trigger) {
					if(!checkCustomName(pigzombie.getCustomName())) {
						World world = pigzombie.getWorld();
						world.strikeLightningEffect(pigzombie.getLocation());
						pigzombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 3));
						pigzombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 1));
						pigzombie.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 60, 1));
						pigzombie.setHealth(pigzombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						pigzombie.setCustomName("Enraged Zombie Pigman");
						pigzombie.setCustomNameVisible(true);
					}
				}
				return;
			}
			default:{ }
			}
		}	
	}
	
}
