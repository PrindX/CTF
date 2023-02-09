package com.prind.ctf.game.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GameEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

    }
}
