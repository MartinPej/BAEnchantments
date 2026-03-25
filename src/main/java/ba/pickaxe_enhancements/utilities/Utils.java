package main.java.ba.pickaxe_enhancements.utilities;


import main.java.ba.pickaxe_enhancements.Main;
import main.java.ba.pickaxe_enhancements.controllers.ConfigController;
import main.java.ba.pickaxe_enhancements.controllers.MessagesController;
import main.java.ba.pickaxe_enhancements.events.EnhancementBlockBreak;
import main.java.ba.pickaxe_enhancements.models.Enhancement;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class Utils implements Listener
{
	private static Main plugin;

	public Utils(Main plugin)
	{
		Utils.plugin = plugin;
	}

	public static String getPluginVersion() { return plugin.getDescription().getVersion(); }

	public static void insufficientPermissions(CommandSender sSender, String command)
	{
		String message = MessagesController.getGlobalInsufficientPermission()
				.replace("{command}", command);

		sSender.sendMessage(convertColorCodes(message));
	}

	public static void fakeBlockBreakEvent(BlockBreakEvent event, Block block, Player player, String enhancementStr)
	{
		EnhancementBlockBreak fakeEvent = new EnhancementBlockBreak(block, player);
		plugin.getServer().getPluginManager().callEvent(fakeEvent);
		if (fakeEvent.isCancelled()) return;

		ArrayList<ItemStack> drops = (ArrayList<ItemStack>) block.getDrops(event.getPlayer().getInventory().getItemInMainHand());
		Map<Enchantment, Integer> enchantments = event.getPlayer().getInventory().getItemInMainHand().getEnchantments();
		block.setType(Material.AIR);
		Enhancement enhancement = ConfigController.getEnhancements().get(enhancementStr);
		player.spawnParticle(enhancement.getParticle(), block.getLocation(), 3);
		player.playSound(player, enhancement.getSound(), enhancement.getVolume(), enhancement.getPitch());
		for (ItemStack item : drops)
		{
			int dropAmount = item.getAmount();
			if (enchantments.containsKey(Enchantment.LOOT_BONUS_BLOCKS))
			{
				// Formula for calculating fortune
				int bonus = (int) (Math.random() * (enchantments.get(Enchantment.LOOT_BONUS_BLOCKS) + 2)) - 1;
				if (bonus < 0) bonus = 0;
				dropAmount += bonus;
				item.setAmount(dropAmount);
			}
			player.getInventory().addItem(item);
		}
	}

	public static String convertColorCodes(String message) { return ChatColor.translateAlternateColorCodes('&', translateHex(message)); }

	private static String translateHex(String message)
	{
		final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
		Matcher matcher = hexPattern.matcher(message);
		StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
		while (matcher.find())
		{
			String group = matcher.group(1);
			matcher.appendReplacement(buffer, COLOR_CHAR + "x"
					+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
					+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
					+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
			);
		}
		return matcher.appendTail(buffer).toString();
	}
}