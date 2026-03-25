package main.java.ba.pickaxe_enhancements.commands;

import main.java.ba.pickaxe_enhancements.api.PickaxeEnhancementsPlayerAPI;
import main.java.ba.pickaxe_enhancements.controllers.MessagesController;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Points extends BAPickaxeEnhancements.Points
{
	@Override
	public void run(Player sender)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.points"))
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements points");
			return;
		}

		int skillPoints = PickaxeEnhancementsPlayerAPI.getPlayerData(sender.getUniqueId()).getSkillPoints();

		sender.sendMessage(MessagesController.getPointsPoints().replace("{points}", String.valueOf(skillPoints)));
	}

	@Override
	public void add(CommandSender sender, Player receiver, int increase)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.points.add") && sender instanceof Player)
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements points add");
			return;
		}

		PickaxeEnhancementsPlayerAPI.getPlayerData(receiver.getUniqueId()).increaseSkillPoints(increase);

		receiver.sendMessage(MessagesController.getPointsAddPlayerMessage().replace("{points}", String.valueOf(increase)));
		sender.sendMessage(MessagesController.getPointsAdd().replace("{points}", String.valueOf(increase)).replace("{receiver}", receiver.getDisplayName()));
	}

	@Override
	public void remove(CommandSender sender, Player receiver, int decrease)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.points.remove") && sender instanceof Player)
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements points remove");
			return;
		}

		PickaxeEnhancementsPlayerAPI.getPlayerData(receiver.getUniqueId()).decreaseSkillPoints(decrease);

		sender.sendMessage(MessagesController.getPointsRemove().replace("{points}", String.valueOf(decrease)).replace("{receiver}", receiver.getDisplayName()));
	}

	@Override
	public void set(CommandSender sender, Player receiver, int value)
	{
		if (!sender.hasPermission("BAPickaxeEnhancements.points.set") && sender instanceof Player)
		{
			Utils.insufficientPermissions(sender, "/BAPickaxeEnhancements points set");
			return;
		}

		PickaxeEnhancementsPlayerAPI.getPlayerData(receiver.getUniqueId()).setSkillPoints(value);


		sender.sendMessage(MessagesController.getPointsSet().replace("{points}", String.valueOf(value)).replace("{receiver}", receiver.getDisplayName()));
	}
}