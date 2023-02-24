package com.prind.ctf.kits.utils;

import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitUtil {

    public static ItemStack getKitIcon(Material material, KitEnum kitEnum, int price, String kitName, String description) {
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
        loreComps.add(Component.text(""));
        loreComps.add(
                Component.text("Price: ").color(NamedTextColor.GOLD).append(Component.text(price).color(NamedTextColor.GREEN))
        );

        ItemStack item = new ItemBuilder(material)
                .name(Component.text(kitName).color(TextColor.color(243, 255, 0)))
                .lore(loreComps)
                .enchant(Enchantment.IMPALING, 10)
                .flag(ItemFlag.HIDE_ENCHANTS)
                .build();

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setEnum("kit-id", kitEnum);
        nbtItem.mergeCustomNBT(item);

        item = nbtItem.getItem();

        return item;
    }

    public static ItemStack getCustomItem(Material material, ItemEnum itemEnum, int cooldown , String name, String itemDescription, String abilityName, String abilityDescription) {
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

        ItemStack item = new ItemBuilder(material)
                .name(Component.text(name).color(TextColor.color(255, 119, 6)))
                .lore(loreComps)
                .enchant(Enchantment.IMPALING, 10)
                .flag(ItemFlag.HIDE_ENCHANTS)
                .build();

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
