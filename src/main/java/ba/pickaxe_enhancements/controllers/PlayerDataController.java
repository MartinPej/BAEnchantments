package main.java.ba.pickaxe_enhancements.controllers;

import main.java.ba.pickaxe_enhancements.Main;
import main.java.ba.pickaxe_enhancements.logger.CustomLogger;
import main.java.ba.pickaxe_enhancements.models.PlayerData;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.UUID;

public class PlayerDataController implements Listener
{
	private static Main plugin;
	private static final String dataFolderPath = "plugins/BAPickaxeEnhancement/data";

	public PlayerDataController(Main plugin)
	{
		PlayerDataController.plugin = plugin;
	}

	public static void save()
	{
		createDirectory();

		for (UUID uuid : plugin.playerData.keySet())
		{
			File playersData = new File(dataFolderPath + "/" + uuid + ".yml");
			try
			{
				if (!playersData.exists())
				{
					if (!playersData.createNewFile())
					{
						CustomLogger.sendMessage("Failed to save data for user " + uuid);
						continue;
					}
				}
			}
			catch (Exception e)
			{
				CustomLogger.sendMessage("Failed to save data for user " + uuid);
				CustomLogger.sendError(e);
				continue;
			}
			PlayerData data = plugin.playerData.get(uuid);
			YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playersData);
			yamlFile.set("PrimaryEnhancement", data.getPrimaryEnhancement());
			yamlFile.set("SkillPoints", data.getSkillPoints());
			yamlFile.set("ExplosionLevel", data.getExplosionLevel());
			yamlFile.set("DiskLevel", data.getDiskLevel());
			yamlFile.set("LaserLevel", data.getLaserLevel());
			try
			{
				yamlFile.save(playersData);
			}
			catch (Exception e)
			{
				CustomLogger.sendMessage("Failed to save data for user " + uuid);
				CustomLogger.sendError(e);
			}
		}
	}

	public static void load()
	{
		createDirectory();
		File[] playerDataFiles = new File(dataFolderPath).listFiles();
		assert playerDataFiles != null;
		for (File playersData : playerDataFiles)
		{
			YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playersData);
			String primaryEnhancement = yamlFile.getString("PrimaryEnhancement");
			int skillPoints = yamlFile.getInt("SkillPoints");
			int explosionLevel = yamlFile.getInt("ExplosionLevel");
			int diskLevel = yamlFile.getInt("DiskLevel");
			int laserLevel = yamlFile.getInt("LaserLevel");

			UUID uuid = UUID.fromString(FilenameUtils.removeExtension(playersData.getName()));
			plugin.playerData.put(uuid, new PlayerData(primaryEnhancement, skillPoints, explosionLevel, diskLevel, laserLevel));
		}
	}

	private static void createDirectory()
	{
		File folder = new File(dataFolderPath);
		if (!folder.exists())
		{
			while (true)
			{
				if (!folder.mkdir()) continue;
				break;
			}
		}
	}

}