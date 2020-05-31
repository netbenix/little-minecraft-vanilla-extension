package ml.codenoodles.lmve.other;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import ml.codenoodles.lmve.Main;

public class RemoveVanillaRecipes implements Listener{
	
	private Main main;
	public RemoveVanillaRecipes(Main main) {
		this.main = main;
	}

	// 1 2 3		Inventory IDs
	// 4 5 6 -> 0
	// 7 8 9
	
	@EventHandler
	public void removeMinecraftRecipes(PrepareItemCraftEvent e) {	
		if(main.getConfigFile().getBoolean("RemoveVanillaRecipes.magma-block")) {
			if(e.getInventory().getItem(0) != null){
				if(e.getRecipe().getResult().getType().equals(Material.MAGMA_BLOCK) && (e.getView().getItem(5).getType() == Material.MAGMA_CREAM || e.getView().getItem(1).getType() == Material.MAGMA_CREAM)) {
					e.getInventory().setResult(new ItemStack(Material.AIR));						
				}	
			}
		}	
	}
	
	
}
