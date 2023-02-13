package com.prind.ctf.kits;

import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.kits.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class WizardKit implements Kit{
    @Override
    public ItemStack getIcon() {
        return ItemBuilder.getKitIcon(
                Material.STICK,
                KitEnum.WIZARD_KIT,
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

        inv.setItem(0, ItemBuilder.getCustomItem(
                Material.STICK,
                ItemEnum.WIZARD_WAND,
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
