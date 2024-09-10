package com.rylinaux.plugman.command;

import com.rylinaux.plugman.PlugMan;
import com.rylinaux.plugman.util.DependencyUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/*
 *  Command that checks what plugins depend on a specific plugin
 *
 * @author Rory Skipper (Roree) on 2023-06-21
 */
public class DependsCommand extends AbstractCommand
{
	/**
	 * The name of the command.
	 */
	public static final String NAME = "Depends";

	/**
	 * The description of the command.
	 */
	public static final String DESCRIPTION = "Check what plugins depend on a specific plugin.";

	/**
	 * The main permission of the command.
	 */
	public static final String PERMISSION = "plugman.depends";

	/**
	 * The proper usage of the command.
	 */
	public static final String USAGE = "/plugman depends <plugin>";

	/**
	 * The sub permissions of the command.
	 */
	public static final String[] SUB_PERMISSIONS = {"all"};

	/**
	 * Construct out object.
	 *
	 * @param sender the command sender
	 */
	public DependsCommand(CommandSender sender)
	{
		super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
	}


	@Override
	public void execute(CommandSender sender, Command command, String label, String[] args)
	{
		if (!this.hasPermission())
		{
			sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("error.no-permission"));
			return;
		}

		if (args.length < 2)
		{
			sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("error.specify-plugin"));
			this.sendUsage();
			return;
		}

		Plugin target = PlugMan.getInstance().getPluginUtil().getPluginByName(args, 1);

		if (target == null)
		{
			sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("error.invalid-plugin"));
			this.sendUsage();
			return;
		}

		List<Plugin> depends = DependencyUtil.getDepends(target), softDepends = DependencyUtil.getSoftDepends(target);

		if (depends.isEmpty() && softDepends.isEmpty())
		{
			sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("depends.none", target.getName()));
			return;
		}

		StringBuilder pluginList = new StringBuilder();
		for (Plugin depend : depends)
		{
			pluginList.append(PlugMan.getInstance().getMessageFormatter().format(false, "depends.depend", depend.getName())).append(PlugMan.getInstance().getMessageFormatter().format(false, "depends.plugin-separator"));
		}

		for (Plugin depend : softDepends)
		{
			pluginList.append(PlugMan.getInstance().getMessageFormatter().format(false, "depends.soft-depend", depend.getName())).append(PlugMan.getInstance().getMessageFormatter().format(false, "depends.plugin-separator"));
		}

		sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("depends.header", target.getName(), pluginList.toString()));
	}

}
