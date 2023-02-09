package com.prind.ctf.kits.utils;

import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    public static ItemStack getKitIcon (Material material, KitEnum kitEnum, String kitName, String description) {
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

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setEnum("kit-id", kitEnum);
        nbtItem.mergeCustomNBT(item);

        item = nbtItem.getItem();

        return item;
    }

    public static ItemStack getCustomItem(Material material, ItemEnum itemEnum, String name, String itemDescription, String abilityName, String abilityDescription) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
                Component.text(name).color(TextColor.color(255, 161, 68))
        );

        ArrayList<Component> loreComps = new ArrayList<>(List.of(
                Component.text("Item Description: ").color(TextColor.color(243, 255, 0)).append(Component.text(itemDescription).color(TextColor.color(139, 139, 139))),
                Component.text(""),
                Component.text("Item Ability: " + abilityName).color(TextColor.color(255, 119, 6)).append(Component.text(" RIGHT CLICK").color(TextColor.color(243, 255, 0)).decorate(TextDecoration.BOLD)),
                Component.text(itemDescription).color(TextColor.color(139, 139, 139))
            ));

        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setEnum("item-id", itemEnum);
        nbtItem.mergeCustomNBT(item);

        item = nbtItem.getItem();

        return item;
    }
}
