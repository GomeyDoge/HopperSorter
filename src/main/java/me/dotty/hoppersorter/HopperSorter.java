package me.dotty.hoppersorter;

import me.dotty.hoppersorter.events.HopperEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class HopperSorter extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new HopperEvents(), this);
    }
}
