package ml.codenoodles.lmve.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;

import ml.codenoodles.lmve.Main;
import ml.codenoodles.lmve.other.ConsoleColor;

public class CustomRecipes implements Listener{

	
	private Main main;
	public CustomRecipes(Main main) {
		this.main = main;
	}
	
	MaterialChoice wool = new MaterialChoice(Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.CYAN_WOOL, Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL,
			Material.LIGHT_GRAY_WOOL, Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.ORANGE_WOOL, Material.PINK_WOOL, Material.PURPLE_WOOL, Material.RED_WOOL, Material.WHITE_WOOL, Material.YELLOW_WOOL);
	
	private CampfireRecipe getCampfireRecipe(String name) {
		switch(name){
			case "COOKED_COD":{
				CampfireRecipe cookedCod = new CampfireRecipe(new NamespacedKey(main, "cfcod"), new ItemStack(Material.COOKED_COD, 1), Material.COD, 0, 400);
				return cookedCod;
			}
			case "COOKED_SALMON":{
				CampfireRecipe cookedSalmon = new CampfireRecipe(new NamespacedKey(main, "cfsalmon"), new ItemStack(Material.COOKED_SALMON, 1), Material.SALMON, 0, 400);
				return cookedSalmon;
			}
			default: {
				return null;
			}
		}
	}
	
	private ShapelessRecipe getShapelessRecipe(String name) {
		switch(name) {
			case "BLACK_DYE":{
				ShapelessRecipe blackinkRecipe = new ShapelessRecipe(new NamespacedKey(main, "blackink"), new ItemStack(Material.BLACK_DYE, 1))
						.addIngredient(Material.COAL);
				return blackinkRecipe;
			}
			case "STRINGS":{
				ShapelessRecipe stringsRecipe = new ShapelessRecipe(new NamespacedKey(main, "strings"),  new ItemStack(Material.STRING, 4))
						.addIngredient(wool);
						return stringsRecipe;
			}
			default:{
				return null;
			}
		}
	}
	
	private ShapedRecipe getShapedRecipe(String name) {
		switch(name) {
			case "COBWEB":{
				ShapedRecipe cobwebRecipe = new ShapedRecipe(new NamespacedKey(main, "cobweb"), new ItemStack(Material.COBWEB, 4))
						.shape("s s", " s ", "s s")
						.setIngredient('s', Material.STRING);	
				return cobwebRecipe;
			}
			case "ELYTRA":{
				ShapedRecipe elytraRecipe = new ShapedRecipe(new NamespacedKey(main, "elytra"), new ItemStack(Material.ELYTRA, 1))
						.shape("pnp", "psp", "p p")
						.setIngredient('p', Material.PHANTOM_MEMBRANE)
						.setIngredient('s', Material.STICK)
						.setIngredient('n', Material.NETHER_STAR);
				return elytraRecipe;
			}
			case "ICE01":{
				ShapedRecipe iceRecipe01 = new ShapedRecipe(new NamespacedKey(main, "ice01"), new ItemStack(Material.ICE, 4))
						.shape("sss", "sws", "sss")
						.setIngredient('s', Material.SNOWBALL)
						.setIngredient('w', Material.WATER_BUCKET);
				return iceRecipe01;
			}
			case "ICE02":{
				ShapedRecipe iceRecipe02 = new ShapedRecipe(new NamespacedKey(main, "ice02"), new ItemStack(Material.ICE, 4))
						.shape("   ", "sws", "   ")
						.setIngredient('s', Material.SNOW_BLOCK)
						.setIngredient('w', Material.WATER_BUCKET);
				
				return iceRecipe02;
			}
			case "MAGMA_BLOCK":{
				ShapedRecipe magmablockRecipe = new ShapedRecipe(new NamespacedKey(main, "magmablock"), new ItemStack(Material.MAGMA_BLOCK, 1))
						.shape(" m ", " c ", " l ")
						.setIngredient('m', Material.MAGMA_CREAM)
						.setIngredient('c', Material.COBBLESTONE)
						.setIngredient('l', Material.LAVA_BUCKET);
				return magmablockRecipe;
			}
			case "MYCELIUM":{
				ShapedRecipe myceliumRecipe = new ShapedRecipe(new NamespacedKey(main, "mycelium"), new ItemStack(Material.MYCELIUM, 1))
						.shape("brb", "rdr", "brb")
						.setIngredient('b', Material.BROWN_MUSHROOM)
						.setIngredient('r', Material.RED_MUSHROOM)
						.setIngredient('d', Material.DIRT);
				return myceliumRecipe;
			}
			default:{
				return null;
			}
		}
	}
	
	private StonecuttingRecipe getStonecuttingRecipe(Material mat) {
		switch(mat) {
		case OAK_SLAB: {
			StonecuttingRecipe oakWoodSlabs = new StonecuttingRecipe(new NamespacedKey(main, "scoakwoodslabs"), new ItemStack(Material.OAK_SLAB, 2), Material.OAK_PLANKS);
			return oakWoodSlabs;
		}
		case BIRCH_SLAB:{
			StonecuttingRecipe birchWoodSlabs = new StonecuttingRecipe(new NamespacedKey(main, "scbirchwoodslabs"), new ItemStack(Material.BIRCH_SLAB, 2), Material.BIRCH_PLANKS);
			return birchWoodSlabs;
		}
		case JUNGLE_SLAB:{
			StonecuttingRecipe jungleWoodSlabs = new StonecuttingRecipe(new NamespacedKey(main, "scjunglewoodslabs"), new ItemStack(Material.JUNGLE_SLAB, 2), Material.JUNGLE_PLANKS);
			return jungleWoodSlabs;
		}
		case SPRUCE_SLAB:{
			StonecuttingRecipe spruceWoodSlabs = new StonecuttingRecipe(new NamespacedKey(main, "scsprucewoodslabs"), new ItemStack(Material.SPRUCE_SLAB, 2), Material.SPRUCE_PLANKS);
			return spruceWoodSlabs;
		}
		case DARK_OAK_SLAB:{
			StonecuttingRecipe darkoakWoodSlabs = new StonecuttingRecipe(new NamespacedKey(main, "scdarkoakwoodslabs"), new ItemStack(Material.DARK_OAK_SLAB, 2), Material.DARK_OAK_PLANKS);
			return darkoakWoodSlabs;
		}
		case ACACIA_SLAB:{
			StonecuttingRecipe acaciaWoodSlabs = new StonecuttingRecipe(new NamespacedKey(main, "scacaciawoodslabs"), new ItemStack(Material.ACACIA_SLAB, 2), Material.ACACIA_PLANKS);
			return acaciaWoodSlabs;
		}
		case OAK_STAIRS:{
			StonecuttingRecipe oakWoodStairs = new StonecuttingRecipe(new NamespacedKey(main, "scoakwoodstairs"), new ItemStack(Material.OAK_STAIRS), Material.OAK_PLANKS);
			return oakWoodStairs;
		}
		case BIRCH_STAIRS:{
			StonecuttingRecipe birchWoodStairs = new StonecuttingRecipe(new NamespacedKey(main, "scbirchwoodstairs"), new ItemStack(Material.BIRCH_STAIRS), Material.BIRCH_PLANKS);
			return birchWoodStairs;
		}
		case JUNGLE_STAIRS:{
			StonecuttingRecipe jungleWoodStairs = new StonecuttingRecipe(new NamespacedKey(main, "scjunglewoodstairs"), new ItemStack(Material.JUNGLE_STAIRS), Material.JUNGLE_PLANKS);
			return jungleWoodStairs;
		}
		case SPRUCE_STAIRS:{
			StonecuttingRecipe spruceWoodStairs = new StonecuttingRecipe(new NamespacedKey(main, "scsprucewoodstairs"), new ItemStack(Material.SPRUCE_STAIRS), Material.SPRUCE_PLANKS);
			return spruceWoodStairs;
		}
		case DARK_OAK_STAIRS:{
			StonecuttingRecipe darkoakWoodStairs = new StonecuttingRecipe(new NamespacedKey(main, "scdarkoakwoodstairs"), new ItemStack(Material.DARK_OAK_STAIRS), Material.DARK_OAK_PLANKS);
			return darkoakWoodStairs;
		}
		case ACACIA_STAIRS:{
			StonecuttingRecipe acaciaWoodStairs = new StonecuttingRecipe(new NamespacedKey(main, "scacaciawoodstairs"), new ItemStack(Material.ACACIA_STAIRS), Material.ACACIA_PLANKS);
			return acaciaWoodStairs;
		}
		default:{ return null; }
		}
	}
	
	
	
	@EventHandler
	private void RegisterCampfireRecipes(ServerLoadEvent e) {
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Campfire.cooked-cod")){
			Bukkit.addRecipe(getCampfireRecipe("COOKED_COD"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "COOKED_COD" + ConsoleColor.BLUE + " CampfireRecipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Campfire.cooked-salmon")){
			Bukkit.addRecipe(getCampfireRecipe("COOKED_SALMON"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "COOKED_SALMON" + ConsoleColor.BLUE + " CampfireRecipe." + ConsoleColor.RESET);
		}		
	}
	
	@EventHandler
	private void RegisterShapelessRecipes(ServerLoadEvent e) {
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.black-dye")) {
			Bukkit.addRecipe(getShapelessRecipe("BLACK_DYE"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "BLACK_DYE" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.strings")) {
			Bukkit.addRecipe(getShapelessRecipe("STRINGS"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "STRINGS" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
	}
	
	@EventHandler
	private void RegisterShapedRecipes(ServerLoadEvent e) {
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.cobweb")) {
			Bukkit.addRecipe(getShapedRecipe("COBWEB"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "COBWEB" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.elytra")) {
			Bukkit.addRecipe(getShapedRecipe("ELYTRA"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "ELYTRA" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.ice01")) {
			Bukkit.addRecipe(getShapedRecipe("ICE01"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "ICE01" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.ice02")) {
			Bukkit.addRecipe(getShapedRecipe("ICE02"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "ICE02" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.magma-block")) {
			Bukkit.addRecipe(getShapedRecipe("MAGMA_BLOCK"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "MAGMA_BLOCK" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Crafting.mycelium")) {
			Bukkit.addRecipe(getShapedRecipe("MYCELIUM"));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "MYCELIUM" + ConsoleColor.BLUE + " Recipe." + ConsoleColor.RESET);
		}
	}
	
	@EventHandler
	private void RegisterStonecuttingRecipe(ServerLoadEvent e) {
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Stonecutter.wood-slabs")) {
			Bukkit.addRecipe(getStonecuttingRecipe(Material.OAK_SLAB));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.BIRCH_SLAB));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.JUNGLE_SLAB));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.SPRUCE_SLAB));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.DARK_OAK_SLAB));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.ACACIA_SLAB));	
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "WOOD_SLAB" + ConsoleColor.BLUE + " StonecuttingRecipe." + ConsoleColor.RESET);
		}
		if(main.getConfigFile().getBoolean("LMVE-Recipes.Stonecutter.wood-stairs")) {
			Bukkit.addRecipe(getStonecuttingRecipe(Material.OAK_STAIRS));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.BIRCH_STAIRS));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.JUNGLE_STAIRS));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.SPRUCE_STAIRS));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.DARK_OAK_STAIRS));
			Bukkit.addRecipe(getStonecuttingRecipe(Material.ACACIA_STAIRS));
			System.out.println(ConsoleColor.BLUE + "[LMVE]Loaded " + ConsoleColor.YELLOW + "WOOD_STAIRS" + ConsoleColor.BLUE + " StonecuttingRecipe." + ConsoleColor.RESET);
		}
		
	}
}
