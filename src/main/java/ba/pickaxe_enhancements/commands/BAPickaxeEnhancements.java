package main.java.ba.pickaxe_enhancements.commands;

import main.java.ba.pickaxe_enhancements.controllers.EnhancementUIController;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Subcommand;

@Command("BAPickaxeEnhancements")
public class BAPickaxeEnhancements
{
	@DefaultFor("~")
	public void run(Player sender)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.enhancements"))
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements");
			return;
		}

		sender.openInventory(EnhancementUIController.getShopInventory(sender.getUniqueId()));
	}

	public abstract static class Help
	{
		@Subcommand("help")
		public abstract void help(CommandSender sender);
	}

	public abstract static class Reload
	{
		@Subcommand("reload")
		public abstract void reload(CommandSender sender);
	}

	@Subcommand("points")
	public abstract static class Points
	{
		@DefaultFor("~")
		public abstract void run(Player sender);
		@Subcommand("points add")
		public abstract void add(CommandSender sender, Player receiver, int increase);
		@Subcommand("points remove")
		public abstract void remove(CommandSender sender, Player receiver, int decrease);
		@Subcommand("points set")
		public abstract void set(CommandSender sender, Player receiver, int value);

	}

	@Subcommand("level")
	public abstract static class Level
	{
		@DefaultFor("~")
		public abstract void run(Player sender, String enchantment);

		@Subcommand("level set")
		public abstract void set(CommandSender sender, Player receiver, String enchantment, int value);
	}
}