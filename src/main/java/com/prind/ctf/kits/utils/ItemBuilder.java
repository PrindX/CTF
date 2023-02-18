package com.prind.ctf.kits.utils;

import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    public static ItemStack getKitIcon(Material material, KitEnum kitEnum, String kitName, String description) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(kitName).color(TextColor.color(243, 255, 0)));

        ArrayList<Component> loreComps = new ArrayList<>();
        loreComps.add(Component.text(""));
        loreComps.add(
                Component
                        .text("Description: ")
                        .color(TextColor.color(55, 213, 255))
                        .append(
                                Component.text(description).color(TextColor.color(255, 161, 68))
                        )
        );
        meta.lore(loreComps);

        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setEnum("kit-id", kitEnum);
        nbtItem.mergeCustomNBT(item);

        item = nbtItem.getItem();

        return item;
    }

    public static ItemStack getCustomItem(Material material, ItemEnum itemEnum, int cooldown , String name, String itemDescription, String abilityName, String abilityDescription) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
                Component.text(name).color(TextColor.color(255, 119, 6))
        );

        String[] itemDescSplitted = itemDescription.split("\n");

        ArrayList<Component> loreComps = new ArrayList<>(List.of(
                Component.text(""),
                Component.text("Item Description: ").color(TextColor.color(255, 119, 6)).append(Component.text(itemDescSplitted[0]).color(TextColor.color(139, 139, 139)))
            ));

        itemDescSplitted[0] = null;

        for (String str : itemDescSplitted) {
            if (str == null) continue;

            loreComps.add(
                    Component.text(str).color(TextColor.color(139, 139, 139)
                    ));
        }

        loreComps.addAll(List.of(
                Component.text(""),
                Component.text("Item Ability: " + abilityName).color(TextColor.color(255, 119, 6)).append(Component.text(" RIGHT CLICK").color(TextColor.color(243, 255, 0)).decorate(TextDecoration.BOLD))
        ));

        String[] abilityDescSplitted = abilityDescription.split("\n");
        for (String str : abilityDescSplitted) {
            loreComps.add(
                    Component.text(str).color(TextColor.color(139, 139, 139)
            ));
        }

        loreComps.add(Component.text(""));
        loreComps.add(Component.text("Cooldown: ").color(TextColor.color(255, 119, 6)).append(Component.text(cooldown + " Seconds").color(NamedTextColor.GREEN)));

        meta.lore(loreComps);

        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setEnum("item-id", itemEnum);
        nbtItem.mergeCustomNBT(item);

        item = nbtItem.getItem();

        return item;
    }

    public static boolean isCustomItem(ItemStack item, ItemEnum customItem) {
        NBTItem nbtItem = new NBTItem(item);
        ItemEnum itemEnum = nbtItem.getEnum("item-id", ItemEnum.class);

        if (itemEnum == null) return false;
        if (itemEnum != customItem) return false;
        return true;
    }
}
