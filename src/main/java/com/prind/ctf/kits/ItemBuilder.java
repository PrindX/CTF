package com.prind.ctf.kits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getCustomItem(Material material, String name, String itemDescription, String abilityDescription) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
                Component.text(name).color(TextColor.color(255, 161, 68))
        );

        ArrayList<Component> loreComps = new ArrayList<>(List.of(
                Component.text("Item Description: " + itemDescription).color(TextColor.color(243, 255, 0)),
                Component.text(""),
                Component.text("Item Ability: " + abilityDescription).color(TextColor.color(243, 255, 0))
            ));

        item.setItemMeta(meta);

        return item;
    }
}
