package com.rylinaux.plugman.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/*
 * @author cullan on 9/10/2024
 */
public class DependencyUtil {

    public static List<Plugin> getDepends(Plugin target)
    {
        List<Plugin> depends = new ArrayList<>();

        pluginLoop:
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
        {
            for (String depend : plugin.getDescription().getDepend())
            {
                if (depend.equalsIgnoreCase(target.getName()))
                {
                    depends.add(plugin);
                    continue pluginLoop;
                }
            }
        }

        return depends;
    }

    public static List<Plugin> getSoftDepends(Plugin target)
    {
        List<Plugin> depends = new ArrayList<>();

        pluginLoop:
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
        {
            for (String depend : plugin.getDescription().getSoftDepend())
            {
                if (depend.equalsIgnoreCase(target.getName()))
                {
                    depends.add(plugin);
                    continue pluginLoop;
                }
            }
        }

        return depends;
    }

}
