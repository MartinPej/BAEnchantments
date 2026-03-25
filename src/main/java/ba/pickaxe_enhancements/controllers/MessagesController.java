package main.java.ba.pickaxe_enhancements.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import main.java.ba.pickaxe_enhancements.Main;
import main.java.ba.pickaxe_enhancements.logger.CustomLogger;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

public class MessagesController implements Listener
{
	private static Main plugin;

	private static FileConfiguration messagesConfig = null;

	public MessagesController(Main plugin)
	{
		MessagesController.plugin = plugin;
	}

	public static void reload()
	{
		File langFolder = new File("plugins/" + plugin.getName() +"/lang");

		if (!langFolder.exists())
		{
			/* Create the folder */
			langFolder.mkdir();
		}


		String[] langFiles = {
				"lang_en"
		};

		for (String langName : langFiles)
		{
			/* Lang Files */
			File langEn = new File("plugins/" + plugin.getName() +"/lang", langName + ".yml");

			if (!langEn.exists())
			{
				try
				{
					/* Copy the lang_en.yml default file */
					FileUtils.copyInputStreamToFile(Objects.requireNonNull(plugin.getResource("resources/lang/" + langName + ".yml")), langEn);
				}
				catch (IOException e)
				{
					CustomLogger.sendError("Failed to load " + langName + ".yml");
					CustomLogger.sendError(e);
				}
			}
		}

		File messagesFile = new File("plugins/" + plugin.getName() + "/lang", getLangFile() + ".yml");

		if (!messagesFile.exists())
		{
			CustomLogger.sendError("Failed to locate lang file, '" + messagesFile.getPath() + "'. Defaulting to lang_en.yml");
			messagesFile = new File("plugins/" + plugin.getName() +"/lang", "lang_en.yml");
		}

		messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(messagesFile);
		messagesConfig.setDefaults(defConfig);
	}



	private static String getLangFile() { return plugin.getConfig().getString("Lang"); }

	public static String getPrefix() { return messagesConfig.getString("Messages.Prefix"); }
	/*
	  	Global
	 */
	public static String getGlobalInsufficientPermission() { return getMessage("Messages.Global.Insufficient_Permission"); }

	/*
		Level
	 */
	public static String getLevelLevel() { return getMessage("Messages.Level.Level"); }
	public static String getLevelSet() { return getMessage("Messages.Level.Set"); }

	/*
		Points
	 */
	public static String getPointsPoints() { return getMessage("Messages.Points.Points"); }
	public static String getPointsAdd() { return getMessage("Messages.Points.Add"); }
	public static String getPointsAddPlayerMessage() { return getMessage("Messages.Points.AddPlayerMessage"); }
	public static String getPointsRemove() { return getMessage("Messages.Points.Remove"); }
	public static String getPointsSet() { return getMessage("Messages.Points.Set"); }

	/*
		Reload
	 */
	public static String getReload() { return getMessage("Messages.Reload"); }


	/*
	  	Help
	 */
	public static ArrayList<String> getHelp() { return (ArrayList<String>) messagesConfig.getStringList("Messages.Help"); }


	/* Helper methods */
	private static String getMessage(String path)
	{
		return Utils.convertColorCodes(Objects.requireNonNull(messagesConfig.getString(path))
				.replace("{prefix}", getPrefix()));
	}
}