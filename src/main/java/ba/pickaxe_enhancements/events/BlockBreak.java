package main.java.ba.pickaxe_enhancements.events;

import main.java.ba.pickaxe_enhancements.api.PickaxeEnhancementsPlayerAPI;
import main.java.ba.pickaxe_enhancements.controllers.ConfigController;
import main.java.ba.pickaxe_enhancements.models.Enhancement;
import main.java.ba.pickaxe_enhancements.models.EnhancementLevel;
import main.java.ba.pickaxe_enhancements.models.PlayerData;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Random;

public class BlockBreak implements Listener
{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (e instanceof EnhancementBlockBreak) return;
		if (ConfigController.getEnabledWorlds().contains(e.getPlayer().getWorld().getName()) && ConfigController.getEnabledTools().contains(e.getPlayer().getInventory().getItemInMainHand().getType()))
		{
			explosion(e);
			disk(e);
			laser(e);
		}
	}

	private void explosion(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		PlayerData playerData = PickaxeEnhancementsPlayerAPI.getPlayerData(player.getUniqueId());
		EnhancementLevel level = doesTrigger(playerData, "Explosion");
		if (level.getRate() == 0) return;

		/*
			Enhancement triggered
		 */
		int range = level.getRange();
		// Generate the circle of possible blocks to break
		ArrayList<Block> possibleBlocks = new ArrayList<>();
		Location startingBlock = e.getBlock().getLocation();
		for (int x = -range ; x < range ; x++)
		{
			for (int y = -range ; y < range ; y++)
			{
				for (int z = -range ; z < range ; z++)
				{
					if (Math.sqrt((x * x) + (y * y) + (z * z)) <= range)
					{
						Block block = startingBlock.getWorld().getBlockAt(x + startingBlock.getBlockX(), y + startingBlock.getBlockY(), z + startingBlock.getBlockZ());
						// If the block isn't air or bedrock add it to the possible breaks
						if (!block.isEmpty() && !block.getType().equals(Material.BEDROCK)) possibleBlocks.add(block);
					}
				}
			}
		}

		// Pick a random block
		Random random = new Random();
		int index = random.nextInt(possibleBlocks.size());
		Utils.fakeBlockBreakEvent(e, possibleBlocks.get(index), e.getPlayer(), "Explosion");
	}

	private void disk(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		PlayerData playerData = PickaxeEnhancementsPlayerAPI.getPlayerData(player.getUniqueId());
		EnhancementLevel level = doesTrigger(playerData, "Disk");
		if (level.getRate() == 0) return;

		/*
			Enhancement triggered
		 */
		int range = level.getRange();
		BlockFace direction = e.getPlayer().getFacing();
		Location startingBlock = e.getBlock().getLocation();
		for (int x = -range ; x <= range ; x++)
		{
			for (int z = -range ; z <= range ; z++)
			{
				if (Math.sqrt((x * x) + (z * z)) <= range)
				{
					Block block = startingBlock.getWorld().getBlockAt(x + startingBlock.getBlockX(), startingBlock.getBlockY(), z + startingBlock.getBlockZ());

					if (block.getX() == startingBlock.getBlockX() && block.getY() == startingBlock.getBlockY() && block.getZ() == startingBlock.getBlockZ()) continue;
					// If the block is air or bedrock ignore it and continue
					if (block.isEmpty()) continue;
					if (block.getType().equals(Material.BEDROCK)) continue;

					switch (direction)
					{
						case NORTH:
							if (block.getZ() > startingBlock.getZ()) continue;
							Utils.fakeBlockBreakEvent(e, block, e.getPlayer(), "Disk");
							break;
						case EAST:
							if (block.getX() < startingBlock.getX()) continue;
							Utils.fakeBlockBreakEvent(e, block, e.getPlayer(), "Disk");
							break;
						case SOUTH:
							if (block.getZ() < startingBlock.getZ()) continue;
							Utils.fakeBlockBreakEvent(e, block, e.getPlayer(), "Disk");
							break;
						case WEST:
							if (block.getX() > startingBlock.getX()) continue;
							Utils.fakeBlockBreakEvent(e, block, e.getPlayer(), "Disk");
							break;
					}
				}
			}

		}
	}

	private void laser(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		PlayerData playerData = PickaxeEnhancementsPlayerAPI.getPlayerData(player.getUniqueId());
		EnhancementLevel level = doesTrigger(playerData, "Laser");
		if (level.getRate() == 0) return;

		/*
			Enhancement triggered
		 */
		int range = level.getRange();
		BlockFace direction = e.getPlayer().getFacing();
		Location location;
		switch (direction)
		{
			case NORTH:
				location = e.getBlock().getLocation();
				for (int counter = 0 ; counter < range ; counter++)
				{
					location.setZ(location.getBlockZ() - 1);
					// If the block isn't air or bedrock break it
					if (!location.getBlock().isEmpty() && !location.getBlock().getType().equals(Material.BEDROCK)) Utils.fakeBlockBreakEvent(e, location.getBlock(), e.getPlayer(), "Laser");
				}
				break;
			case EAST:
				location = e.getBlock().getLocation();
				for (int counter = 0 ; counter < range ; counter++)
				{
					location.setX(location.getBlockX() + 1);
					if (!location.getBlock().isEmpty() && !location.getBlock().getType().equals(Material.BEDROCK)) Utils.fakeBlockBreakEvent(e, location.getBlock(), e.getPlayer(), "Laser");
				}
				break;
			case SOUTH:
				location = e.getBlock().getLocation();
				for (int counter = 0 ; counter < range ; counter++)
				{
					location.setZ(location.getBlockZ() + 1);
					if (!location.getBlock().isEmpty() && !location.getBlock().getType().equals(Material.BEDROCK)) Utils.fakeBlockBreakEvent(e, location.getBlock(), e.getPlayer(), "Laser");
				}
				break;
			case WEST:
				location = e.getBlock().getLocation();
				for (int counter = 0 ; counter < range ; counter++)
				{
					location.setX(location.getBlockX() - 1);
					if (!location.getBlock().isEmpty() && !location.getBlock().getType().equals(Material.BEDROCK)) Utils.fakeBlockBreakEvent(e, location.getBlock(), e.getPlayer(), "Laser");
				}
				break;
		}
	}

	private EnhancementLevel doesTrigger(PlayerData playerData, String enhancementStr)
	{
		int level = 0;
		switch (enhancementStr)
		{
			case "Explosion":
				level = playerData.getExplosionLevel();
				break;
			case "Disk":
				level = playerData.getDiskLevel();
				break;
			case "Laser":
				level = playerData.getLaserLevel();
				break;
		}
		if (level == 0) return new EnhancementLevel(0, 0);

		Enhancement enhancement = ConfigController.getEnhancements().get(enhancementStr);
		EnhancementLevel enhancementLevel;
		switch (level)
		{
			case 2:
				enhancementLevel = enhancement.getTwo();
				break;
			case 3:
				enhancementLevel = enhancement.getThree();
				break;
			default:
				enhancementLevel = enhancement.getOne();
		}
		// Random number between 1-100
		Random random = new Random();
		int chance = random.nextInt(100 - 1) + 1;
		double rate = enhancementLevel.getRate() * 100;
		if (!enhancementStr.equals(playerData.getPrimaryEnhancement())) rate /= 2;

		// Check if activates
		if (chance > rate) return new EnhancementLevel(0, 0);
		return enhancementLevel;
	}
}