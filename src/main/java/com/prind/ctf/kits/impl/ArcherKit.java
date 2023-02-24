package com.prind.ctf.kits.impl;

import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.kits.utils.KitUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ArcherKit implements Kit {

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public ItemStack getIcon() {
        return KitUtil.getKitIcon(
                Material.BOW,
                getKitEnum(),
                getPrice(),
                getName(),
                "Kill your enemies with distance"
        );
    }

    @Override
    public String getName() {
        return "Archer Kit";
    }

    @Override
    public KitEnum getKitEnum() {
        return KitEnum.ARCHER_KIT;
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        inv.setArmorContents(getArmorContents());

        inv.setItem(0, KitUtil.makeCustomWeapon(
                Material.BOW,
                ItemEnum.TERMINATOR_BOW,
                10,
                "Terminator",
                "Start killing your enemies with distance",
                "Rapid Fire",
                "Launch a burst of arrows to where you are looking"
        ));
        inv.setItem(1, new ItemStack(Material.IRON_AXE));
        inv.setItem(2, new ItemStack(Material.ARROW, 64));
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
