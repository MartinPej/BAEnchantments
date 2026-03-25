package main.java.ba.pickaxe_enhancements.api;

import main.java.ba.pickaxe_enhancements.Main;
import main.java.ba.pickaxe_enhancements.models.PlayerData;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PickaxeEnhancementsPlayerAPI implements Listener
{
	private static Main plugin;

	public PickaxeEnhancementsPlayerAPI(Main plugin)
	{
		PickaxeEnhancementsPlayerAPI.plugin = plugin;
	}

	public static boolean playerExists(UUID uuid)
	{
		return plugin.playerData.containsKey(uuid);
	}

	public static void createPlayer(UUID uuid)
	{
		if (!playerExists(uuid)) plugin.playerData.put(uuid, new PlayerData());
	}

	public static PlayerData getPlayerData(UUID uuid)
	{
		return plugin.playerData.get(uuid);
	}
}