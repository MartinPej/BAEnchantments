package main.java.ba.pickaxe_enhancements.commands;

import main.java.ba.pickaxe_enhancements.controllers.ConfigController;
import main.java.ba.pickaxe_enhancements.controllers.MessagesController;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends BAPickaxeEnhancements.Reload
{
	@Override
	public void reload(CommandSender sender)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.reload") && sender instanceof Player)
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements reload");
			return;
		}

		ConfigController.reload();
		sender.sendMessage(MessagesController.getReload());
	}
}