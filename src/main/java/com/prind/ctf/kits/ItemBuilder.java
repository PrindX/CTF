package com.prind.ctf.kits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemBuilder {

    public static ItemStack getKitIcon (Material material, String kitName, String description) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(kitName).color(TextColor.color(243, 255, 0)));

        ArrayList<Component> loreComps = new ArrayList<>();
        loreComps.add(
                Component
                        .text("Description: ")
                        .color(TextColor.color(55, 213, 255))
                        .append(
                                Component.text(description).color(TextColor.color(255, 161, 68))
                        )
        );

        return item;
    }
}
