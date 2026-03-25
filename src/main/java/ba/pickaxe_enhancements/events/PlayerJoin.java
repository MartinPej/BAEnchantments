package main.java.ba.pickaxe_enhancements.events;

import main.java.ba.pickaxe_enhancements.api.PickaxeEnhancementsPlayerAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		UUID uuid = player.getUniqueId();
		PickaxeEnhancementsPlayerAPI.createPlayer(uuid);
	}
}