package main.java.ba.pickaxe_enhancements.events;

import main.java.ba.pickaxe_enhancements.api.PickaxeEnhancementsPlayerAPI;
import main.java.ba.pickaxe_enhancements.controllers.ConfigController;
import main.java.ba.pickaxe_enhancements.controllers.EnhancementUIController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class InventoryClick implements Listener
{
	@EventHandler
	public void onInvClick(InventoryClickEvent e)
	{
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getCurrentItem() == null) return;
		if (e.getView().getTitle().equals(ConfigController.getTitle()))
		{
			Player player = (Player) e.getWhoClicked();
			/*
				Check if buying
			 */
			if (e.getClick() == ClickType.LEFT)
			{
				// Explosion checks
				if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Explosion", 1))) purchaseLevel(player, "Explosion", 1);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Explosion", 2))) purchaseLevel(player, "Explosion", 2);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Explosion", 3))) purchaseLevel(player, "Explosion", 3);
				// Disk check
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Disk", 1))) purchaseLevel(player, "Disk", 1);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Disk", 2))) purchaseLevel(player, "Disk", 2);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Disk", 3))) purchaseLevel(player, "Disk", 3);
				// Laser check
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Laser", 1))) purchaseLevel(player, "Laser", 1);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Laser", 2))) purchaseLevel(player, "Laser", 2);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementLocked("Laser", 3))) purchaseLevel(player, "Laser", 3);
			}

			/*
				Check if returning
			 */
			if (e.getClick() == ClickType.RIGHT)
			{
				if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Explosion", 1))) returnLevel(player, "Explosion", 1);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Explosion", 2))) returnLevel(player, "Explosion", 2);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Explosion", 3))) returnLevel(player, "Explosion", 3);
				// Disk check
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Disk", 1))) returnLevel(player, "Disk", 1);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Disk", 2))) returnLevel(player, "Disk", 2);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Disk", 3))) returnLevel(player, "Disk", 3);
				// Laser check
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Laser", 1))) returnLevel(player, "Laser", 1);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Laser", 2))) returnLevel(player, "Laser", 2);
				else if (e.getCurrentItem().equals(EnhancementUIController.getEnhancementUnlocked("Laser", 3))) returnLevel(player, "Laser", 3);
			}

			// Cycle through primary enhancements
			if (e.getCurrentItem().equals(EnhancementUIController.getPrimaryEnhancementItem(e.getWhoClicked().getUniqueId()))) PickaxeEnhancementsPlayerAPI.getPlayerData(e.getWhoClicked()
																																													   .getUniqueId()).updatePrimaryEnhancement();
			e.setCancelled(true);
			e.getWhoClicked().openInventory(EnhancementUIController.getShopInventory(e.getWhoClicked().getUniqueId()));
		}
	}

	private void purchaseLevel(Player player, String enhancement, int level)
	{
		UUID uuid = player.getUniqueId();
		if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getSkillPoints() <= 0) return;
		switch (enhancement.toUpperCase())
		{
			case "EXPLOSION":
				if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getExplosionLevel() == level - 1)
				{
					player.playSound(player.getLocation(), ConfigController.getLockedSoundSound(), ConfigController.getLockedVolume(), ConfigController.getLockedPitch());
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).increaseExplosionLevel(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).decreaseSkillPoints(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).updatePrimaryEnhancement();
				}
				break;
			case "DISK":
				if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getDiskLevel() == level - 1)
				{
					player.playSound(player.getLocation(), ConfigController.getLockedSoundSound(), ConfigController.getLockedVolume(), ConfigController.getLockedPitch());
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).increaseDiskLevel(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).decreaseSkillPoints(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).updatePrimaryEnhancement();
				}
				break;
			case "LASER":
				if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getLaserLevel() == level - 1)
				{
					player.playSound(player.getLocation(), ConfigController.getLockedSoundSound(), ConfigController.getLockedVolume(), ConfigController.getLockedPitch());
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).increaseLaserLevel(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).decreaseSkillPoints(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).updatePrimaryEnhancement();
				}
				break;
		}
	}

	private void returnLevel(Player player, String enhancement, int level)
	{
		UUID uuid = player.getUniqueId();
		switch (enhancement.toUpperCase())
		{
			case "EXPLOSION":
				if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getExplosionLevel() == level)
				{
					player.playSound(player.getLocation(), ConfigController.getUnlockedSoundSound(), ConfigController.getUnlockedVolume(), ConfigController.getUnlockedPitch());
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).decreaseExplosionLevel(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).increaseSkillPoints(1);
					if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getExplosionLevel() == 0) PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).updatePrimaryEnhancement();
				}
				break;
			case "DISK":
				if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getDiskLevel() == level)
				{
					player.playSound(player.getLocation(), ConfigController.getUnlockedSoundSound(), ConfigController.getUnlockedVolume(), ConfigController.getUnlockedPitch());
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).decreaseDiskLevel(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).increaseSkillPoints(1);
					if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getDiskLevel() == 0) PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).updatePrimaryEnhancement();
				}
				break;
			case "LASER":
				if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getLaserLevel() == level)
				{
					player.playSound(player.getLocation(), ConfigController.getUnlockedSoundSound(), ConfigController.getUnlockedVolume(), ConfigController.getUnlockedPitch());
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).decreaseLaserLevel(1);
					PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).increaseSkillPoints(1);
					if (PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).getLaserLevel() == 0) PickaxeEnhancementsPlayerAPI.getPlayerData(uuid).updatePrimaryEnhancement();
				}
				break;
		}
	}
}