package com.rylinaux.plugman.pluginmanager;

import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;

public interface PluginManager {

    /**
     * Enable a plugin.
     *
     * @param plugin the plugin to enable
     */
    void enable(Plugin plugin);

    /**
     * Enable all plugins.
     */
    void enableAll();

    /**
     * Disable a plugin.
     *
     * @param plugin the plugin to disable
     */
    void disable(Plugin plugin);

    /**
     * Disable all plugins.
     */
    void disableAll();

    /**
     * Returns the formatted name of the plugin.
     *
     * @param plugin the plugin to format
     * @return the formatted name
     */
    String getFormattedName(Plugin plugin);

    /**
     * Returns the formatted name of the plugin.
     *
     * @param plugin          the plugin to format
     * @param includeVersions whether to include the version
     * @return the formatted name
     */
    String getFormattedName(Plugin plugin, boolean includeVersions);

    /**
     * Returns a plugin from an array of Strings.
     *
     * @param args  the array
     * @param start the index to start at
     * @return the plugin
     */
    Plugin getPluginByName(String[] args, int start);

    /**
     * Returns a plugin from a String.
     *
     * @param name the name of the plugin
     * @return the plugin
     */
    Plugin getPluginByName(String name);

    /**
     * Returns a List of plugin names.
     *
     * @return list of plugin names
     */
    List<String> getPluginNames(boolean fullName);

    /**
     * Returns a List of disabled plugin names.
     *
     * @return list of disabled plugin names
     */
    List<String> getDisabledPluginNames(boolean fullName);

    /**
     * Returns a List of enabled plugin names.
     *
     * @return list of enabled plugin names
     */
    List<String> getEnabledPluginNames(boolean fullName);

    /**
     * Get the version of another plugin.
     *
     * @param name the name of the other plugin.
     * @return the version.
     */
    String getPluginVersion(String name);

    /**
     * Returns the commands a plugin has registered.
     *
     * @param plugin the plugin to deal with
     * @return the commands registered
     */
    String getUsages(Plugin plugin);

    /**
     * Find which plugin has a given command registered.
     *
     * @param command the command.
     * @return the plugin.
     */
    List<String> findByCommand(String command);

    /**
     * Checks whether the plugin is ignored.
     *
     * @param plugin the plugin to check
     * @return whether the plugin is ignored
     */
    boolean isIgnored(Plugin plugin);

    /**
     * Checks whether the plugin is ignored.
     *
     * @param plugin the plugin to check
     * @return whether the plugin is ignored
     */
    boolean isIgnored(String plugin);

    /**
     * Loads and enables a plugin.
     *
     * @param name plugin's name
     * @return status message
     * @throws RuntimeException if command map or known commands field cannot be found
     */
    String load(String name);

    Map<String, Command> getKnownCommands();

    /**
     * Changes the knownCommands field in CommandMap
     * @param knownCommands the modified known commands
     * @throws RuntimeException if command map or known commands field cannot be found
     */
    void setKnownCommands(Map<String, Command> knownCommands);


    /**
     * Reload a plugin.
     *
     * @param plugin the plugin to reload
     */
    void reload(Plugin plugin);

    /**
     * Reload all plugins.
     */
    void reloadAll();

    /**
     * Unload a plugin.
     *
     * @param plugin the plugin to unload
     * @return the message to send to the user.
     */
    String unload(Plugin plugin);

    /**
     * Returns if the plugin is a Paper plugin.
     * @param plugin the plugin to check
     * @return if the plugin is a Paper plugin
     */
    boolean isPaperPlugin(Plugin plugin);
}
