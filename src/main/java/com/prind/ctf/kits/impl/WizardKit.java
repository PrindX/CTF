package com.prind.ctf.kits.impl;

import com.prind.ctf.CTF;
import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.kits.utils.KitUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class WizardKit implements Kit {

    FileConfiguration config = CTF.getInstance().getKitsConfig().getConfiguration();

    @Override
    public boolean isFree() {
        return config.getBoolean("kits.wizard.is-free");
    }

    @Override
    public int getPrice() {
        return config.getInt("kits.wizard.price");
    }

    @Override
    public ItemStack getIcon() {
        return KitUtil.getKitIcon(
                Material.STICK,
                getKitEnum(),
                getPrice(),
                getName(),
                "Become the wizard and start zapping everyone"
        );
    }

    @Override
    public String getName() {
        return "Wizard Kit";
    }

    @Override
    public KitEnum getKitEnum() {
        return KitEnum.WIZARD_KIT;
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.clear();

        inv.setArmorContents(getArmorContents());

        inv.setItem(0, KitUtil.makeCustomWeapon(
                Material.STICK,
                ItemEnum.WIZARD_WAND,
                config.getInt("kits.wizard.item-cooldown"),
                "Wizard Wand",
                "Use this wand to zap everyone you like",
                "Zapper",
                "Shoots a fireball where you are looking"
        ));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta metaSword = sword.getItemMeta();
        metaSword.setUnbreakable(true);
        sword.setItemMeta(metaSword);
        inv.setItem(1, sword);
        inv.setItem(2, new ItemStack(Material.GOLDEN_APPLE, 6));
    }

    private ItemStack[] getArmorContents() {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

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
