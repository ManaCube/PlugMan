package com.rylinaux.plugman.command;

import com.rylinaux.plugman.PlugMan;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * #%L
 * PlugMan
 * %%
 * Copyright (C) 2010 - 2014 PlugMan
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

public class DownloadCommand extends AbstractCommand {

    /**
     * The name of the command.
     */
    public static final String NAME = "Download";

    /**
     * The description of the command.
     */
    public static final String DESCRIPTION = "Download a plugin.";

    /**
     * The main permission of the command.
     */
    public static final String PERMISSION = "plugman.download";

    /**
     * The proper usage of the command.
     */
    public static final String USAGE = "/plugman download <direct|spigot> <ID|URL>";

    /**
     * The sub permissions of the command.
     */
    public static final String[] SUB_PERMISSIONS = {};

    /**
     * Construct out object.
     *
     * @param sender the command sender
     */
    public DownloadCommand(CommandSender sender) {
        super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
    }

    /**
     * Execute the command
     *
     * @param sender  the sender of the command
     * @param command the command being done
     * @param label   the name of the command
     * @param args    the arguments supplied
     */
    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.RED + "This command is disabled.");

        /*
        if (!hasPermission()) {
            sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("error.no-permission"));
            return;
        }

        if (PlugMan.getInstance().isDisableDownloadCommand())
            return;

        URL url;
        switch (args.length < 2 ? "" : args[1]) {
            case "spigot":
                if (args.length < 3 || !NumberUtils.isNumber(args[2])) {
                    sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("download.invalid-id"));
                    sendUsage();
                    return;
                }

                try {
                    url = new URL("https://api.spiget.org/v2/resources/" + args[2] + "/download");
                } catch (MalformedURLException e) {
                    sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("download.invalid-id"));
                    sendUsage();
                    return;
                }

                break;
            case "direct":
                if (args.length < 3 || !args[2].startsWith("http://") && !args[2].startsWith("https://")) {
                    sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("download.invalid-url"));
                    sendUsage();
                    return;
                }

                try {
                    url = new URL(args[2]);
                } catch (MalformedURLException e) {
                    sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("download.malformed-url"));
                    sendUsage();
                    return;
                }

                break;
            default:
                sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("download.invalid-type"));
                sendUsage();
                return;
        }

        File file;
        try {
            file = PlugMan.getInstance().getPluginUtil().download(url);
        } catch (IOException e) {
            e.printStackTrace();
            sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("download.download-failed"));
            return;
        }

        String name = file.getName();
        name = name.substring(0, name.length() - 4);
        sender.sendMessage(PlugMan.getInstance().getPluginUtil().load(name));
        */
    }

}
