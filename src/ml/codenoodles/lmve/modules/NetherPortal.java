package ml.codenoodles.lmve.modules;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

import ml.codenoodles.lmve.Main;

public class NetherPortal implements Listener{

	private Main main;
	public NetherPortal(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void PreventPortalActivate(PortalCreateEvent e) {
		if(main.getConfig().getBoolean("NetherPortal.prevent-portals-above-nehter-ceiling") == true) {
			if(e.getWorld().getEnvironment() == Environment.NETHER) {
				List<BlockState> block = e.getBlocks();
				World world = e.getWorld();
				Location loc = null;
				for(int i = 0; i < block.size(); i++) {
					BlockState state = block.get(i);
					loc = state.getLocation();
					if(state.getLocation().getBlockY() >= 128) {
						Block blockk = state.getBlock();
						blockk.setType(Material.AIR);
						world.spawnParticle(Particle.EXPLOSION_NORMAL, loc.getX(), loc.getY(), loc.getZ(), 10);
					}
				}
				if(loc.getY() > 127) {
					world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 100, 1);
					e.setCancelled(true);
				} 
			}
		}
	}
}
