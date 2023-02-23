package com.prind.ctf.kits.listeners;

import com.prind.ctf.CTF;
import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.utils.KitUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SwordOfDeathListener implements Listener {

    private Map<UUID, Integer> cooldown = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        ItemStack item = event.getItem();
        if (!(KitUtil.isCustomItem(item, ItemEnum.SWORD_OF_DEATH))) return;

        Player player = event.getPlayer();
        if (cooldown.containsKey(player.getUniqueId())) {
            player.sendMessage(Component.text("You are currently in cooldown for " + cooldown.get(player.getUniqueId()) + " seconds").color(TextColor.color(255, 0, 0)));
            return;
        }
        World world = player.getWorld();

        for (Entity entity : player.getNearbyEntities(4, 2, 4)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (entity.getUniqueId().equals(player.getUniqueId())) continue;
                livingEntity.damage(5);
                livingEntity.setVelocity(livingEntity.getLocation().toVector().subtract(player.getLocation().toVector()).multiply(0.6f));
                world.strikeLightning(livingEntity.getLocation());
            }
        }

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
