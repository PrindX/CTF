package com.prind.ctf.kits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BasicKit implements Kit {
    @Override
    public ItemStack getIcon() {
        return ItemBuilder.getKitIcon(
                Material.DIAMOND_SWORD,
                getName(),
                "Get a quick start in the arena"
        );
    }

    @Override
    public String getName() {
        return "Basic Kit";
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        inv.setArmorContents(new ItemStack[]
                {
                     new ItemStack(Material.IRON_BOOTS),
                     new ItemStack(Material.IRON_LEGGINGS),
                     new ItemStack(Material.IRON_CHESTPLATE),
                     new ItemStack(Material.IRON_HELMET)
                }
        );

        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD));
        inv.setItem(1, new ItemStack(Material.BOW));
        inv.setItem(2, new ItemStack(Material.ARROW, 16));
        inv.setItem(2, new ItemStack(Material.GOLDEN_APPLE, 6));
    }
}
