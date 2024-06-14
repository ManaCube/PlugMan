package me.entity303.plugmanbungee.util;

public class PluginResult {
    private final String message;
    private final boolean positive;

    public PluginResult(String message, boolean positive) {
        this.message = message;
        this.positive = positive;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isPositive() {
        return this.positive;
    }
}
