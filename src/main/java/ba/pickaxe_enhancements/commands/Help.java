package main.java.ba.pickaxe_enhancements.commands;

import main.java.ba.pickaxe_enhancements.controllers.MessagesController;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Default;

import java.util.ArrayList;

public class Help extends BAPickaxeEnhancements.Help
{
	@Override
	public void help(@Default("me") CommandSender sender)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.help") && sender instanceof Player)
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements help");
			return;
		}
		ArrayList<String> helpMessages = MessagesController.getHelp();

		for (String message : helpMessages)
		{
			message = message.replace("{version}", Utils.getPluginVersion());

			sender.sendMessage(Utils.convertColorCodes(message));
		}
	}
}