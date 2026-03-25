package main.java.ba.pickaxe_enhancements.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.ba.pickaxe_enhancements.Main;
import main.java.ba.pickaxe_enhancements.controllers.ConfigController;
import org.bukkit.event.Listener;



public class CustomLogger implements Listener
{
	private static Logger logger;

	public CustomLogger(Main plugin)
	{
		logger = plugin.getLogger();
	}

	public static void sendMessage(String message)
	{
		logger.log(Level.INFO, message);
	}

	public static void sendError(String err)
	{
		logger.log(Level.WARNING, err);
	}

	public static void sendError(Exception err)
	{
		if (ConfigController.getDebug())
		{
			logger.log(Level.WARNING, err.getMessage());
		}
	}
}