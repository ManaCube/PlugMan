package com.rylinaux.plugman.util;

import org.bukkit.command.Command;

public class BukkitCommandWrapUseless extends BukkitCommandWrap {

    public BukkitCommandWrapUseless() {
    }

    @Override
    public void wrap(Command command, String alias) {
    }

    @Override
    public void unwrap(String command) {
    }
}
