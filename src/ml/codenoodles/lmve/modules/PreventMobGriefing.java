package ml.codenoodles.lmve.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import ml.codenoodles.lmve.Main;

public class PreventMobGriefing implements Listener{

	private Main main;
	public PreventMobGriefing(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void PreventExplosion(ExplosionPrimeEvent e) {
		boolean change = false;
		switch(e.getEntityType()) {
		case CREEPER:{
			if(main.getConfig().getBoolean("PreventMobGriefing.creeper")) {
				change = true;
			}
			break;
		}
		case WITHER:{
			if(main.getConfig().getBoolean("PreventMobGriefing.wither")) {
				change = true;
			}
			break;
		}
		case WITHER_SKULL:{
			if(main.getConfig().getBoolean("PreventMobGriefing.wither")) {
				change = true;
			}
			break;
		}
		default: {change = false; break;}
		}
		
		if(change) {
			e.setRadius(0);
			e.setFire(false);
		}
	}
}
