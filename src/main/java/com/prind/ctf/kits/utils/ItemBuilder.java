package com.prind.ctf.kits.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
    }

    public ItemBuilder name(Component text) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(text);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(List<Component> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.lore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder flag(ItemFlag flag) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        return item;
    }
}
