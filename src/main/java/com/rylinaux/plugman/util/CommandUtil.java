package com.rylinaux.plugman.util;

import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * @author cullan on 1/2/2024
 */
public class CommandUtil
{

    private static Method syncCommandsMethod;

    public static void sync() {
        if (syncCommandsMethod == null) try {
            syncCommandsMethod = Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".CraftServer").getDeclaredMethod("syncCommands");
            syncCommandsMethod.setAccessible(true);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
