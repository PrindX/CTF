package com.prind.ctf.game.event;

import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.Team;
import com.prind.ctf.game.manager.GameManager;
import com.prind.ctf.kits.utils.ItemBuilder;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTBlock;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameEvent implements Listener {

    private final FileConfiguration gameConfig = CTF.getInstance().getGameConfig().getConfiguration();
    private GameManager gameManager = CTF.getInstance().getGameManager();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        for (Game game : gameManager.getGames()) {
            if (!game.getPlayers().contains(player)) return;
            Team teamOne = game.getTeamById(1);
            Team teamTwo = game.getTeamById(2);

            ItemStack hand = player.getInventory().getItemInMainHand();
            if (teamOne.getPlayers().contains(player)) {

            } else if (teamTwo.getPlayers().contains(player)) {

            }

        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        for (Game game : gameManager.getGames()) {
            System.out.println("awdad");
            if (!game.getPlayers().contains(player)) return;
            Team teamOne = game.getTeamById(1);
            Team teamTwo = game.getTeamById(2);

            Block block = event.getBlock();
            NBTBlock nbtBlock = new NBTBlock(block);
            if (!nbtBlock.getData().hasTag("Team")) {
                System.out.println("HEY HEY");
                return;
            }

            if (teamOne.getPlayers().contains(player)) {
                System.out.println("1");
                if (nbtBlock.getData().getString("Team").equalsIgnoreCase("one")) {
                    event.setCancelled(true);
                    System.out.println("2");
                    return;
                }


            } else if (teamTwo.getPlayers().contains(player)) {

            }

        }
    }

    public void checkBlock() {

    }

    public ItemStack checkItem(String team, String banner) {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(banner));
        if (itemStack.getType() != Material.LEGACY_BANNER) return null;

        NBTItem item = new NBTItem(itemStack);
        if (item.getString("Team").equalsIgnoreCase(team))

        item.mergeCustomNBT(itemStack);

        return itemStack;
    }
}
