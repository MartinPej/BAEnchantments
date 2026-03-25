package main.java.ba.pickaxe_enhancements;

import main.java.ba.pickaxe_enhancements.api.PickaxeEnhancementsPlayerAPI;
import main.java.ba.pickaxe_enhancements.commands.*;
import main.java.ba.pickaxe_enhancements.controllers.ConfigController;
import main.java.ba.pickaxe_enhancements.controllers.MessagesController;
import main.java.ba.pickaxe_enhancements.controllers.PlayerDataController;
import main.java.ba.pickaxe_enhancements.events.BlockBreak;
import main.java.ba.pickaxe_enhancements.events.InventoryClick;
import main.java.ba.pickaxe_enhancements.events.PlayerJoin;
import main.java.ba.pickaxe_enhancements.logger.CustomLogger;
import main.java.ba.pickaxe_enhancements.models.PlayerData;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.HashMap;
import java.util.UUID;

public class Main  extends JavaPlugin implements Listener
{
	PluginDescriptionFile pdf;
	public HashMap<UUID, PlayerData> playerData = new HashMap<>();

	public Main()
	{
		this.pdf = getDescription();
	}

	public void onEnable()
	{
		registerEvents();

		registerCommands();

		initializeFiles();

		CustomLogger.sendMessage("-------------------------------");
		CustomLogger.sendMessage("");
		CustomLogger.sendMessage("    " + this.getName() + " Enabled!");
		CustomLogger.sendMessage("");
		CustomLogger.sendMessage("-------------------------------");
	}

	public void onDisable()
	{
		PlayerDataController.save();

		CustomLogger.sendMessage("-------------------------------");
		CustomLogger.sendMessage("");
		CustomLogger.sendMessage("    " + this.getName() + " Disabled!");
		CustomLogger.sendMessage("");
		CustomLogger.sendMessage("-------------------------------");
	}

	private void registerEvents()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		PluginManager pm = Bukkit.getServer().getPluginManager();

		/* Utilities */
		pm.registerEvents(new Utils(this), this);
		pm.registerEvents(new CustomLogger(this), this);

		/* API */
		pm.registerEvents(new PickaxeEnhancementsPlayerAPI(this), this);

		/* Events */
		pm.registerEvents(new InventoryClick(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new PlayerJoin(), this);

		/* Controllers */
		pm.registerEvents(new MessagesController(this), this);
		pm.registerEvents(new ConfigController(this), this);
		pm.registerEvents(new PlayerDataController(this), this);
	}

	private void registerCommands()
	{
		BukkitCommandHandler handler = BukkitCommandHandler.create(this);
		handler.register(new BAPickaxeEnhancements());
		handler.register(new Reload());
		handler.register(new Help());
		handler.register(new Points());
		handler.register(new Level());
		handler.registerBrigadier();
	}

	private void initializeFiles()
	{
		ConfigController.reload();
		MessagesController.reload();

		PlayerDataController.load();
	}
}