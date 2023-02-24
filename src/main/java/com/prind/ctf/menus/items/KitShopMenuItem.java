package com.prind.ctf.menus.items;

import com.prind.ctf.kits.utils.ItemBuilder;
import com.prind.ctf.menus.enums.ItemMenuEnum;
import com.prind.ctf.util.ItemUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitShopMenuItem {

    public static ItemStack get() {
        ItemStack item = new ItemBuilder(Material.CHEST)
                .name(getName())
                .lore(getLore())
                .build();

        item = ItemUtil.makeCustom(item, ItemMenuEnum.KitShopMenu);

        return item;
    }

    private static Component getName() {
        Component name = Component.text("Kit Shop").color(NamedTextColor.GOLD).append(Component.text(" (Right Click)").color(NamedTextColor.GRAY));

        return name;
    }

    private static List<Component> getLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Opens the Kit Shop Menu").color(NamedTextColor.GREEN));
        return lore;
    }
}
