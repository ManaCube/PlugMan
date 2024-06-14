package com.rylinaux.plugman.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BukkitCommandWrap {
    private final boolean nmsVersioning;
    private String nmsVersion;

    private Field bField;
    private Method removeCommandMethod;
    private Class minecraftServerClass;
    private Method aMethod;
    private Method getServerMethod;
    private Field vanillaCommandDispatcherField;
    private Method getCommandDispatcherMethod;
    private Method registerMethod;
    private Method syncCommandsMethod;
    private Constructor bukkitcommandWrapperConstructor;

    public BukkitCommandWrap() {
        String prefix = getCraftBukkitPrefix();
        String[] packageParts = prefix.split("\\.");
        String versionPart = packageParts[packageParts.length - 1];
        if (versionPart.startsWith("v1_"))
            this.nmsVersion = versionPart;

        boolean nmsVers = true;
        try {
            Class.forName("net.minecraft.server.MinecraftServer");
            nmsVers = false;
        } catch (ClassNotFoundException ignore) {}
        this.nmsVersioning = nmsVers;
    }

    private static @NotNull String getCraftBukkitPrefix() {
        return Bukkit.getServer().getClass().getPackage().getName();
    }

    public static @NotNull String getCraftBukkitPrefix(String cbClassName) {
        return getCraftBukkitPrefix() + "." + cbClassName;
    }

    private @NotNull String getNetMinecraftServerPrefix(String nmsClassName) {
        if (this.nmsVersioning) return "net.minecraft.server." + this.nmsVersion + "." + nmsClassName;
        return "net.minecraft.server." + nmsClassName;
    }

    public void wrap(Command command, String alias) {
        if (this.nmsVersion == null) return;

        if (!this.resolveMinecraftServerClass()) return;

        if (!this.resolveGetServerMethod()) return;
        Object minecraftServer = this.getServerInstance();

        if (!this.resolveVanillaCommandDispatcherField()) return;
        Object commandDispatcher = this.getCommandDispatcher(minecraftServer);
        if (commandDispatcher == null) return;

        if (!this.resolveBField()) return;

        if (!this.resolveAMethod(commandDispatcher)) return;

        if (!this.resolveBukkitCmdWrapperConstructor()) return;
        Object commandWrapper = this.getCommandWrapper(command);
        if (commandWrapper == null) return;

        Object aInstance = this.getAInstance(commandDispatcher);
        if (aInstance == null) return;

        if (!this.resolveRegisterCommandMethod()) return;

        try {
            this.registerMethod.invoke(commandWrapper, aInstance, alias);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private boolean resolveSyncCommandsMethod() {
        if (this.syncCommandsMethod != null) return true;

        try {
            this.syncCommandsMethod = Class.forName(getCraftBukkitPrefix("CraftServer")).getDeclaredMethod("syncCommands");
            this.syncCommandsMethod.setAccessible(true);
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sync() {
        if (!this.resolveSyncCommandsMethod()) return;

        try {
            this.syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unwrap(String command) {
        if (this.nmsVersion == null) return;

        if (!this.resolveMinecraftServerClass()) return;

        if (!this.resolveGetServerMethod()) return;
        Object server = this.getServerInstance();

        if (!this.resolveVanillaCommandDispatcherField()) return;
        Object commandDispatcher = this.getCommandDispatcher(server);

        if (!this.resolveBField()) return;

        CommandDispatcher b = this.getDispatcher(commandDispatcher);
        if (b == null) return;

        if (!this.resolveRemoveCommandMethod()) return;

        try {
            this.removeCommandMethod.invoke(b.getRoot(), command);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private boolean resolveRemoveCommandMethod() {
        if (this.removeCommandMethod == null) try {
            try {
                this.removeCommandMethod = RootCommandNode.class.getDeclaredMethod("removeCommand", String.class);
            } catch (NoSuchMethodException | NoSuchMethodError ex) {
                this.removeCommandMethod = CommandNode.class.getDeclaredMethod("removeCommand", String.class);
            }
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private @Nullable CommandDispatcher getDispatcher(Object commandDispatcher) {
        try {
            return (CommandDispatcher) this.bField.get(commandDispatcher);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private @Nullable Object getCommandDispatcher(Object server) {
        try {
            return this.vanillaCommandDispatcherField.get(server);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object getServerInstance() {
        try {
            return this.getServerMethod.invoke(this.minecraftServerClass);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean resolveMinecraftServerClass() {
        if (this.minecraftServerClass != null) return true;
        try {
            this.minecraftServerClass = Class.forName(this.getNetMinecraftServerPrefix("MinecraftServer"));
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean resolveGetServerMethod() {
        if (this.getServerMethod != null) return true;
        try {
            this.getServerMethod = this.minecraftServerClass.getMethod("getServer");
            this.getServerMethod.setAccessible(true);
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resolveVanillaCommandDispatcherField() {
        if (this.vanillaCommandDispatcherField != null) return true;
        try {
            this.vanillaCommandDispatcherField = this.minecraftServerClass.getDeclaredField("vanillaCommandDispatcher");
            this.vanillaCommandDispatcherField.setAccessible(true);
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resolveBField() {
        if (this.bField != null) return true;
        try {
            this.bField = Class.forName(this.getNetMinecraftServerPrefix("CommandDispatcher")).getDeclaredField("b");
            this.bField.setAccessible(true);
            return true;
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            try {
                Class<?> clazz = Class.forName("net.minecraft.commands.CommandDispatcher");
                Field gField = clazz.getDeclaredField("g");
                if (gField.getType() == com.mojang.brigadier.CommandDispatcher.class)
                    this.bField = gField;
                else
                    this.bField = clazz.getDeclaredField("h");
                this.bField.setAccessible(true);
                return true;
            } catch (NoSuchFieldException | ClassNotFoundException ex) {
                ex.addSuppressed(e);
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean resolveRegisterCommandMethod() {
        if (this.registerMethod != null) return true;
        try {
            this.registerMethod = Class.forName(getCraftBukkitPrefix("command.BukkitCommandWrapper"))
                    .getMethod("register", CommandDispatcher.class, String.class);
            this.registerMethod.setAccessible(true);
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Object getAInstance(Object commandDispatcher) {
        try {
            return this.aMethod.invoke(commandDispatcher);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private @Nullable Object getCommandWrapper(Command command) {
        try {
            return this.bukkitcommandWrapperConstructor.newInstance(Bukkit.getServer(), command);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean resolveBukkitCmdWrapperConstructor() {
        if (this.bukkitcommandWrapperConstructor != null) return true;
        try {
            this.bukkitcommandWrapperConstructor = Class.forName(getCraftBukkitPrefix("command.BukkitCommandWrapper")).getDeclaredConstructor(Class.forName("org.bukkit.craftbukkit." + this.nmsVersion + ".CraftServer"), Command.class);
            this.bukkitcommandWrapperConstructor.setAccessible(true);
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resolveAMethod(Object commandDispatcher) {
        if (this.aMethod != null) return true;
        try {
            this.aMethod = commandDispatcher.getClass().getDeclaredMethod("a");
            this.aMethod.setAccessible(true);
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }
}
