package me.entity303.plugmanbungee.main;

import me.entity303.plugmanbungee.commands.PlugManBungeeCommand;
import me.entity303.plugmanbungee.commands.PluginsCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public final class PlugManBungee extends Plugin implements Listener {
    private static PlugManBungee instance;

    @Override
    public void onEnable() {
        instance = this;

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PluginsCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PlugManBungeeCommand());
    }

    @Override
    public void onDisable() {
    }

    public static PlugManBungee getInstance() {
        return instance;
    }
}
