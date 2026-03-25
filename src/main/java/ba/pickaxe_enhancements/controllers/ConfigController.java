package main.java.ba.pickaxe_enhancements.controllers;

import main.java.ba.pickaxe_enhancements.Main;
import main.java.ba.pickaxe_enhancements.logger.CustomLogger;
import main.java.ba.pickaxe_enhancements.models.Enhancement;
import main.java.ba.pickaxe_enhancements.models.EnhancementLevel;
import main.java.ba.pickaxe_enhancements.utilities.Utils;
import org.apache.commons.io.FileUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ConfigController implements Listener
{
	private static Main plugin;

	private static String pluginFolderPath;

	private static final HashMap<String, Enhancement> enhancements = new HashMap<>();
	private static final ArrayList<Material> enabledTools = new ArrayList<>();

	public ConfigController(Main plugin)
	{
		ConfigController.plugin = plugin;
		pluginFolderPath = "plugins/" + plugin.getName() + "/";
	}

	public static void reload()
	{
		File configFile = new File(plugin.getDataFolder(), "config.yml");
		if (!configFile.exists())
		{
			try
			{
				FileUtils.copyInputStreamToFile(Objects.requireNonNull(plugin.getResource("resources/config.yml")), new File(pluginFolderPath + "config.yml"));
			}
			catch (Exception e)
			{
				CustomLogger.sendError("Failed to load default config.yml");
				CustomLogger.sendError(e);
			}
		}

		plugin.reloadConfig();

		/* Reload cached info from config */
		reloadEnhancements();
		reloadEnabledTools();
	}


	/*
		Option Methods
	 */
	public static ArrayList<String> getEnabledWorlds() { return (ArrayList<String>) plugin.getConfig().getStringList("Options.EnabledWorlds"); }
	public static ArrayList<Material> getEnabledTools() { return enabledTools; }

	/*
		Enhancement UI methods
	 */
	public static String getTitle() { return Utils.convertColorCodes(plugin.getConfig().getString("EnhancementUI.Title")); }


	// Unlocked Item
	public static Sound getUnlockedSoundSound()
	{
		Sound sound = Sound.BLOCK_AMETHYST_BLOCK_HIT;
		try
		{
			sound = Sound.valueOf(plugin.getConfig().getString("EnhancementUI.Unlocked.Sound.Sound"));
		}
		catch (Exception e)
		{
			CustomLogger.sendError("Failed to load sound for Unlock. Defaulting to BLOCK_AMETHYST_BLOCK_HIT.");
		}

		return sound;
	}
	public static float getUnlockedVolume() { return Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString("EnhancementUI.Unlocked.Sound.Volume")));}
	public static float getUnlockedPitch() { return Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString("EnhancementUI.Unlocked.Sound.Pitch")));}
	public static String getUnlockedMaterial() { return plugin.getConfig().getString("EnhancementUI.Unlocked.Material"); }

	public static int getUnlockedAmount() { return plugin.getConfig().getInt("EnhancementUI.Unlocked.Amount"); }

	public static String getUnlockedName() { return Utils.convertColorCodes(plugin.getConfig().getString("EnhancementUI.Unlocked.Name")); }

	public static ArrayList<String> getUnlockedLore() { return (ArrayList<String>) plugin.getConfig().getStringList("EnhancementUI.Unlocked.Lore"); }


	// Locked Item
	public static Sound getLockedSoundSound()
	{
		Sound sound = Sound.BLOCK_AMETHYST_BLOCK_HIT;
		try
		{
			sound = Sound.valueOf(plugin.getConfig().getString("EnhancementUI.Locked.Sound.Sound"));
		}
		catch (Exception e)
		{
			CustomLogger.sendError("Failed to load sound for Unlock. Defaulting to BLOCK_AMETHYST_BLOCK_HIT.");
		}

		return sound;
	}
	public static float getLockedVolume() { return Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString("EnhancementUI.Locked.Sound.Volume")));}
	public static float getLockedPitch() { return Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString("EnhancementUI.Locked.Sound.Pitch")));}
	public static String getLockedMaterial() { return plugin.getConfig().getString("EnhancementUI.Locked.Material"); }

	public static int getLockedAmount() { return plugin.getConfig().getInt("EnhancementUI.Locked.Amount"); }

	public static String getLockedName() { return Utils.convertColorCodes(plugin.getConfig().getString("EnhancementUI.Locked.Name")); }

	public static ArrayList<String> getLockedLore() { return (ArrayList<String>) plugin.getConfig().getStringList("EnhancementUI.Locked.Lore"); }


	// Filler Item
	public static boolean getUseFiller() { return plugin.getConfig().getBoolean("EnhancementUI.UseFiller"); }

	public static String getFillerItemMaterial() { return plugin.getConfig().getString("EnhancementUI.FillerItem.Material"); }

	public static int getFillerItemAmount() { return plugin.getConfig().getInt("EnhancementUI.FillerItem.Amount"); }

	public static String getFillerItemName() { return Utils.convertColorCodes(plugin.getConfig().getString("EnhancementUI.FillerItem.Name")); }

	public static ArrayList<String> getFillerItemLore() { return (ArrayList<String>) plugin.getConfig().getStringList("EnhancementUI.FillerItem.Lore"); }


	// Info Item
	public static String getInfoItemMaterial() { return plugin.getConfig().getString("EnhancementUI.InfoItem.Material"); }

	public static int getInfoItemAmount() { return plugin.getConfig().getInt("EnhancementUI.InfoItem.Amount"); }

	public static String getInfoItemName() { return Utils.convertColorCodes(plugin.getConfig().getString("EnhancementUI.InfoItem.Name")); }

	public static ArrayList<String> getInfoItemLore() { return (ArrayList<String>) plugin.getConfig().getStringList("EnhancementUI.InfoItem.Lore"); }


	// Primary Enhancement Item
	public static String getPrimaryEnhancementItemMaterial() { return plugin.getConfig().getString("EnhancementUI.PrimaryEnhancementItem.Material"); }

	public static int getPrimaryEnhancementItemAmount() { return plugin.getConfig().getInt("EnhancementUI.PrimaryEnhancementItem.Amount"); }

	public static String getPrimaryEnhancementItemName() { return Utils.convertColorCodes(plugin.getConfig().getString("EnhancementUI.PrimaryEnhancementItem.Name")); }

	public static ArrayList<String> getPrimaryEnhancementItemLore() { return (ArrayList<String>) plugin.getConfig().getStringList("EnhancementUI.PrimaryEnhancementItem.Lore"); }


	/*
		Individual methods
	 */
	public static boolean getDebug() { return plugin.getConfig().getBoolean("Debug"); }


	/*
		Retrieval methods
	 */
	public static HashMap<String, Enhancement> getEnhancements() { return enhancements; }

	/*
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Config cache reload methods
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private static void reloadEnhancements()
	{
		enhancements.clear();

		for (String enhancementStr : plugin.getConfig().getConfigurationSection("Enhancements").getKeys(false))
		{
			Particle particle = Particle.EXPLOSION_NORMAL;
			try
			{
				particle = Particle.valueOf(plugin.getConfig().getString(String.format("Enhancements.%s.Particle", enhancementStr)));
			}
			catch (Exception e)
			{
				CustomLogger.sendError(String.format("Failed to load particle for %s. Defaulting to EXPLOSION_NORMAL.", enhancementStr));
			}
			Sound sound = Sound.ENTITY_GENERIC_EXPLODE;
			try
			{
				sound = Sound.valueOf(plugin.getConfig().getString(String.format("Enhancements.%s.Sound.Sound", enhancementStr)));
			}
			catch (Exception e)
			{
				CustomLogger.sendError(String.format("Failed to load sound for %s. Defaulting to ENTITY_GENERIC_EXPLODE.", enhancementStr));
			}

			float volume = Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString(String.format("Enhancements.%s.Sound.Volume", enhancementStr))));
			float pitch = Float.parseFloat(Objects.requireNonNull(plugin.getConfig().getString(String.format("Enhancements.%s.Sound.Pitch", enhancementStr))));

			EnhancementLevel one = new EnhancementLevel(getRate(enhancementStr, "One"), getRange(enhancementStr, "One"));
			EnhancementLevel two = new EnhancementLevel(getRate(enhancementStr, "Two"), getRange(enhancementStr, "Two"));
			EnhancementLevel three = new EnhancementLevel(getRate(enhancementStr, "Three"), getRange(enhancementStr, "Three"));
			Enhancement enhancement = new Enhancement(particle, sound, volume, pitch, one, two, three);
			enhancements.put(enhancementStr, enhancement);
		}
	}

	private static void reloadEnabledTools()
	{
		enabledTools.clear();
		{
			for (String materialStr : plugin.getConfig().getStringList("Options.EnabledTools"))
			{
				try
				{
					Material material = Material.valueOf(materialStr);
					enabledTools.add(material);
				}
				catch (Exception e)
				{
					CustomLogger.sendError("Failed to verify material " + materialStr + " for enabled tools. Skipping...");
				}
			}
		}
	}

	/*
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Helper Methods
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private static double getRate(String enhancement, String level)
	{
		double rate = 0;
		try
		{
			rate = plugin.getConfig().getDouble(String.format("Enhancements.%s.Levels.%s.Rate", enhancement, level));
		}
		catch (Exception e)
		{
			CustomLogger.sendError(e);
			CustomLogger.sendError(String.format("Incorrect number format for %s level %s. Must be a percent in decimal format. Eg 10%% = 0.1. Defaulting to 0%%", enhancement, level));
		}
		return rate;
	}

	private static int getRange(String enhancement, String level)
	{
		int range = 0;
		try
		{
			range = plugin.getConfig().getInt(String.format("Enhancements.%s.Levels.%s.Range", enhancement, level));
		}
		catch (Exception e)
		{
			CustomLogger.sendError(e);
			CustomLogger.sendError(String.format("Incorrect number format for %s level %s. Must be a whole number. Eg 1 not 1.1. Defaulting to 0", enhancement, level));
		}
		return range;
	}
}