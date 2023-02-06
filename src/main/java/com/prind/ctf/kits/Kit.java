package com.prind.ctf.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Kit {

    public ItemStack getIcon();
    public void applyKit(Player player);
}
