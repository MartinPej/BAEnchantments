package main.java.ba.pickaxe_enhancements.controllers;

import main.java.ba.pickaxe_enhancements.api.PickaxeEnhancementsPlayerAPI;
import main.java.ba.pickaxe_enhancements.logger.CustomLogger;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class EnhancementUIController
{
	public static Inventory getShopInventory(UUID uuid)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ConfigController.getTitle());

		// Fill the inventory with filler item if enabled
		if (ConfigController.getUseFiller())
		{
			for (int counter = 0 ; counter < inv.getSize() ; counter++)
			{
				inv.setItem(counter, getFillerItem());
			}
		}

		// Create and set Explosion level panes
		inv.setItem(46, getEnhancementDisplay("Explosion", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getExplosionLevel(), 1));
		inv.setItem(37, getEnhancementDisplay("Explosion", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getExplosionLevel(), 2));
		inv.setItem(28, getEnhancementDisplay("Explosion", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getExplosionLevel(), 3));

		// Create and set Disk level panes
		inv.setItem(49, getEnhancementDisplay("Disk", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getDiskLevel(), 1));
		inv.setItem(40, getEnhancementDisplay("Disk", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getDiskLevel(), 2));
		inv.setItem(31, getEnhancementDisplay("Disk", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getDiskLevel(), 3));

		// Create and set Laser level panes
		inv.setItem(52, getEnhancementDisplay("Laser", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getLaserLevel(), 1));
		inv.setItem(43, getEnhancementDisplay("Laser", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getLaserLevel(), 2));
		inv.setItem(34, getEnhancementDisplay("Laser", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getLaserLevel(), 3));

		// Create and set primary enhancement button
		inv.setItem(0, getPrimaryEnhancementItem(uuid));

		// Create and set info item
		inv.setItem(8, getInfoItem(uuid));

		return inv;
	}

	private static ItemStack getFillerItem()
	{
		Material material = Material.WHITE_STAINED_GLASS_PANE;
		try
		{
			material = Material.valueOf(ConfigController.getFillerItemMaterial().toUpperCase());
		}
		catch (Exception e)
		{
			CustomLogger.sendError("Failed to recognise material set for filler item. Defaulting to WHITE_STAINED_GLASS_PANE");
		}
		int amount = ConfigController.getFillerItemAmount();
		String name = ConfigController.getFillerItemName();
		ArrayList<String> lore = new ArrayList<>();
		for (String line : ConfigController.getFillerItemLore())
		{
			lore.add(Utils.convertColorCodes(line));
		}
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	private static ItemStack getInfoItem(UUID uuid)
	{
		Material material = Material.PAPER;
		try
		{
			material = Material.valueOf(ConfigController.getInfoItemMaterial().toUpperCase());
		}
		catch (Exception e)
		{
			CustomLogger.sendError("Failed to recognise material set for info item. Defaulting to PAPER");
		}
		int amount = ConfigController.getInfoItemAmount();
		String name = ConfigController.getInfoItemName().replace("{points}", String.valueOf(PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getSkillPoints()));
		ArrayList<String> lore = new ArrayList<>();
		for (String line : ConfigController.getInfoItemLore())
		{
			lore.add(Utils.convertColorCodes(line));
		}
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack getPrimaryEnhancementItem(UUID uuid)
	{
		Material material = Material.STONE_BUTTON;
		try
		{
			material = Material.valueOf(ConfigController.getPrimaryEnhancementItemMaterial().toUpperCase());
		}
		catch (Exception e)
		{
			CustomLogger.sendError("Failed to recognise material set for Primary Enhancement item. Defaulting to STONE_BUTTON");
		}
		int amount = ConfigController.getPrimaryEnhancementItemAmount();
		String name = ConfigController.getPrimaryEnhancementItemName().replace("{primary_enhancement}", PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getPrimaryEnhancement());
		ArrayList<String> lore = new ArrayList<>();
		for (String line : ConfigController.getPrimaryEnhancementItemLore())
		{
			lore.add(Utils.convertColorCodes(line));
		}
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	private static ItemStack getEnhancementDisplay(String enhancement, int playerLevel, int level)
	{
		if (playerLevel >= level) return getEnhancementUnlocked(enhancement, level);
		return getEnhancementLocked(enhancement, level);
	}

	public static ItemStack getEnhancementLocked(String enhancement, int level)
	{
		double rate = 0;
		switch (level)
		{
			case 1:
				rate = ConfigController.getEnhancements().get(enhancement).getOne().getRate();
				break;
			case 2:
				rate = ConfigController.getEnhancements().get(enhancement).getTwo().getRate();
				break;
			case 3:
				rate = ConfigController.getEnhancements().get(enhancement).getThree().getRate();
				break;
		}
		Material material = Material.RED_STAINED_GLASS_PANE;
		try
		{
			material = Material.valueOf(ConfigController.getLockedMaterial().toUpperCase());
		}
		catch (Exception e)
		{
			CustomLogger.sendError("Failed to recognise material set for Locked Enhancement item. Defaulting to RED_STAINED_GLASS_PANE");
		}

		int amount = ConfigController.getLockedAmount();

		String name = ConfigController.getLockedName().replace("{enhancement}", enhancement).replace("{level}", String.valueOf(level));

		ArrayList<String> lore = new ArrayList<>();
		for (String line : ConfigController.getLockedLore())
		{
			line = line.replace("{enhancement}", enhancement).replace("{level}", String.valueOf(level)).replace("{rate}", String.valueOf(rate * 100));
			lore.add(Utils.convertColorCodes(line));
		}
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack getEnhancementUnlocked(String enhancement, int level)
	{
		double rate = 0;
		switch (level)
		{
			case 1:
				rate = ConfigController.getEnhancements().get(enhancement).getOne().getRate();
				break;
			case 2:
				rate = ConfigController.getEnhancements().get(enhancement).getTwo().getRate();
				break;
			case 3:
				rate = ConfigController.getEnhancements().get(enhancement).getThree().getRate();
				break;
		}
		Material material = Material.GREEN_STAINED_GLASS_PANE;
		try
		{
			material = Material.valueOf(ConfigController.getUnlockedMaterial().toUpperCase());
		}
		catch (Exception e)
		{
			CustomLogger.sendError("Failed to recognise material set for Locked Enhancement item. Defaulting to GREEN_STAINED_GLASS_PANE");
		}

		int amount = ConfigController.getUnlockedAmount();

		String name = ConfigController.getUnlockedName().replace("{enhancement}", enhancement).replace("{level}", String.valueOf(level));

		ArrayList<String> lore = new ArrayList<>();
		for (String line : ConfigController.getUnlockedLore())
		{
			line = line.replace("{enhancement}", enhancement).replace("{level}", String.valueOf(level)).replace("{rate}", String.valueOf(rate * 100));
			lore.add(Utils.convertColorCodes(line));
		}
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}