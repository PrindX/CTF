package com.prind.ctf.kits.impl;

import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.kits.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class StrikerKit implements Kit {
    @Override
    public ItemStack getIcon() {
        return ItemBuilder.getKitIcon(
                Material.DIAMOND_SWORD,
                KitEnum.STRIKER_KIT,
                getName(),
                "Start striking your enemies in the arena"
        );
    }

    @Override
    public String getName() {
        return "Striker Kit";
    }

    @Override
    public KitEnum getKitEnum() {
        return KitEnum.STRIKER_KIT;
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        inv.setArmorContents(getArmorContents());

        inv.setItem(0, ItemBuilder.getCustomItem(
                Material.DIAMOND_SWORD,
                ItemEnum.SWORD_OF_DEATH,
                10,
                "Sword Of Death",
                "Slay your enemies\nand get a reward\nfor each kill",
                "Strike The Rage",
                "Strike every player near you\nin a 4 block radius and damage them"
        ));
        inv.setItem(1, new ItemStack(Material.BOW));
        inv.setItem(2, new ItemStack(Material.ARROW, 16));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 6));
    }

    private ItemStack[] getArmorContents() {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);

        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setUnbreakable(true);
        boots.setItemMeta(bootsMeta);

        ItemMeta leggingsMeta = boots.getItemMeta();
        leggingsMeta.setUnbreakable(true);
        leggings.setItemMeta(leggingsMeta);

        ItemMeta chestplateMeta = boots.getItemMeta();
        chestplateMeta.setUnbreakable(true);
        chestplate.setItemMeta(chestplateMeta);

        ItemMeta helmetMeta = boots.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);

        return new ItemStack[] {
          boots,
          leggings,
          chestplate,
          helmet
        };
    }
}
