package com.prind.ctf.kits;

import com.prind.ctf.kits.enums.KitEnum;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Kit {

    public int getPrice();

    public ItemStack getIcon();

    public String getName();

    public KitEnum getKitEnum();

    public void applyKit(Player player);
}
