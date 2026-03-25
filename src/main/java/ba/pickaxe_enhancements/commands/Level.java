package main.java.ba.pickaxe_enhancements.commands;

import main.java.ba.pickaxe_enhancements.api.PickaxeEnhancementsPlayerAPI;
import main.java.ba.pickaxe_enhancements.controllers.MessagesController;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Level extends BAPickaxeEnhancements.Level
{
	@Override
	public void run(Player sender, String enchantment)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.level"))
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements level");
			return;
		}
		int level = 0;
		switch (enchantment.toUpperCase())
		{
			case "EXPLOSION":
				level = PickaxeEnhancementsPlayerAPI.getPlayerData(sender.getUniqueId()).getExplosionLevel();
				break;
			case "DISK":
				level = PickaxeEnhancementsPlayerAPI.getPlayerData(sender.getUniqueId()).getDiskLevel();
				break;
			case "LASER":
				level = PickaxeEnhancementsPlayerAPI.getPlayerData(sender.getUniqueId()).getLaserLevel();
				break;
		}
		sender.sendMessage(MessagesController.getLevelLevel().replace("{enhancement}", enchantment).replace("{level}", String.valueOf(level)));
	}

	@Override
	public void set(CommandSender sender, Player receiver, String enchantment, int value)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.level.set") && sender instanceof Player)
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements level set");
			return;
		}

		switch (enchantment.toUpperCase())
		{
			case "EXPLOSION":
				PickaxeEnhancementsPlayerAPI.getPlayerData(receiver.getUniqueId()).setExplosionLevel(value);
				break;
			case "DISK":
				PickaxeEnhancementsPlayerAPI.getPlayerData(receiver.getUniqueId()).setDiskLevel(value);
				break;
			case "LASER":
				PickaxeEnhancementsPlayerAPI.getPlayerData(receiver.getUniqueId()).setLaserLevel(value);
				break;
		}

		sender.sendMessage(MessagesController.getLevelSet().replace("{receiver}", receiver.getDisplayName()).replace("{enhancement}", enchantment).replace("{level}", String.valueOf(value)));
	}
}