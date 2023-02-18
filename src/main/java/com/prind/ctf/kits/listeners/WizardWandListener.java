package com.prind.ctf.kits.listeners;

import com.prind.ctf.CTF;
import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WizardWandListener implements Listener {

    private Map<UUID, Integer> cooldown = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        ItemStack item = event.getItem();
        if (!(ItemBuilder.isCustomItem(item, ItemEnum.WIZARD_WAND))) return;

        Player player = event.getPlayer();
        if (cooldown.containsKey(player.getUniqueId())) {
            player.sendMessage(Component.text("You are currently in cooldown for " + cooldown.get(player.getUniqueId()) + " seconds").color(TextColor.color(255, 0, 0)));
            return;
        }

        // Ability Code
        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setIsIncendiary(false);
        fireball.setYield(0f);
        fireball.setVelocity(fireball.getVelocity().multiply(1.5f));
        fireball.setDirection(player.getLocation().getDirection());

        cooldown.put(player.getUniqueId(), 10); // Seconds
        new BukkitRunnable() {
            @Override
            public void run() {
                int current = cooldown.get(player.getUniqueId());
                if (current > 0) {
                    cooldown.put(player.getUniqueId(), current - 1);
                }
                if (current == 0) {
                    cooldown.remove(player.getUniqueId());
                    cancel();
                }
            }
        }.runTaskTimer(CTF.getInstance(), 0L, 20L);

        event.setCancelled(true);
    }
}
