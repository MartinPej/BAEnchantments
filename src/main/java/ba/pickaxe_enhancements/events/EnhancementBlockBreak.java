package main.java.ba.pickaxe_enhancements.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class EnhancementBlockBreak extends BlockBreakEvent
{
	public EnhancementBlockBreak(Block theBlock, Player player)
	{
		super(theBlock, player);
	}
}