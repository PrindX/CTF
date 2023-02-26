package com.prind.ctf.game.event;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.Team;
import com.prind.ctf.game.GameManager;
import com.prind.ctf.menus.KitSelectionMenu;
import com.prind.ctf.stats.PlayerStats;
import com.prind.ctf.util.ChatUtil;
import de.tr7zw.changeme.nbtapi.NBTBlock;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameEvent implements Listener {

    private final FileConfiguration gameConfig = CTF.getInstance().getGameConfig().getConfiguration();
    private final FileConfiguration mainConfig = CTF.getInstance().getConfig();
    private GameManager gameManager = CTF.getInstance().getGameManager();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        for (Game game : gameManager.getGames()) {
            if (!game.getPlayers().contains(player)) return;
            Team teamOne = game.getTeamById(1);
            Team teamTwo = game.getTeamById(2);

            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
            ItemStack hand = player.getInventory().getItemInMainHand();
            NBTBlock nbtBlock = new NBTBlock(event.getClickedBlock());
            switch (nbtBlock.getData().getString("Team")) {
                case "one":
                    event.setCancelled(true);
                    if (teamTwo.getPlayers().contains(player)) {
                        return;
                    }

                    if (!hand.equals(getFlagItem("two", Material.RED_BANNER))) {
                        event.setCancelled(true);
                        return;
                    }

                    teamOne.addFlags(1);
                    testFlagPositions(game);
                    ChatUtil.message(player, mainConfig.getString("messages.captured-flag"));
                    hand.setAmount(hand.getAmount() - 1);

                    break;
                case "two":
                    event.setCancelled(true);
                    if (teamOne.getPlayers().contains(player)) {
                        return;
                    }

                    if (!hand.equals(getFlagItem("one", Material.BLUE_BANNER))) {
                        event.setCancelled(true);
                        return;
                    }

                    teamTwo.addFlags(1);
                    testFlagPositions(game);
                    ChatUtil.message(player, mainConfig.getString("messages.captured-flag"));
                    hand.setAmount(hand.getAmount() - 1);

                    break;
                default:
                    event.setCancelled(true);
                    break;
            }

        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        for (Game game : gameManager.getGames()) {
            if (!game.getPlayers().contains(player)) return;
            Team teamOne = game.getTeamById(1);
            Team teamTwo = game.getTeamById(2);

            Block block = event.getBlock();
            NBTBlock nbtBlock = new NBTBlock(block);
            if (!nbtBlock.getData().hasTag("Team")) {
                return;
            }

            switch (nbtBlock.getData().getString("Team")) {
                case "one":
                    if (teamOne.getPlayers().contains(player)) {
                        event.setCancelled(true);
                        return;
                    }

                    player.getInventory().addItem(event.getBlock().getDrops().toArray(new ItemStack[0]));
                    event.getBlock().getDrops().clear();

                    break;
                case "two":
                    if (teamTwo.getPlayers().contains(player)) {
                        event.setCancelled(true);
                        return;
                    }
                    event.setCancelled(true);
                    player.getInventory().addItem(getFlagItem("two", event.getBlock().getType()));
                    event.getBlock().setType(Material.AIR);


                    break;
                default:

                    break;
            }

        }
    }

    public void testFlagPositions(Game game) {
        for (Team team : game.getTeams()) {
            MVWorldManager worldManager = CTF.getInstance().getMultiverseCore().getMVWorldManager();
            String world = gameConfig.getString("games." + game.getDisplayName() + ".teams." + team.getId() + ".world");
            double x = gameConfig.getDouble("games." + game.getDisplayName() + ".teams." + team.getId() + ".flag.x");
            double y = gameConfig.getDouble("games." + game.getDisplayName() + ".teams." + team.getId() + ".flag.y");
            double z = gameConfig.getDouble("games." + game.getDisplayName() + ".teams." + team.getId() +".flag.z");

            Location loc = new Location(worldManager.getMVWorld(world).getCBWorld(), x, y, z);
            NBTBlock block = new NBTBlock(loc.getBlock());
            if (team.getId() == 1) {
                loc.getBlock().setType(Material.BLUE_BANNER);
                block.getData().setString("Team", "one");
            } else {
                loc.getBlock().setType(Material.RED_BANNER);
                block.getData().setString("Team", "two");
            }
        }
    }

    public ItemStack getFlagItem(String team, Material material) {
        ItemStack itemStack = new ItemStack(material);
        NBTItem item = new NBTItem(itemStack);

        item.setString("Team", team);
        item.mergeCustomNBT(itemStack);

        return itemStack;
    }

    @EventHandler
    public void PlayerCloseInventory(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof KitSelectionMenu)) return;
        if (!(event.getPlayer() instanceof Player player)) return;

        for (Game game : gameManager.getGames()) {
            if (!game.getPlayers().contains(player)) return;

            PlayerStats stats = CTF.getInstance().getStatsManager().get(player.getUniqueId());

            if (stats.getSelectedKit() == null)
                Bukkit.getScheduler().runTaskLater(CTF.getInstance(), () -> new KitSelectionMenu(player), 5L);
        }
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        for (Game game : gameManager.getGames()) {
            if (!game.getPlayers().contains(player)) return;

            PlayerStats stats = CTF.getInstance().getStatsManager().get(player.getUniqueId());
            stats.setCoins(stats.getCoins() + mainConfig.getInt("coins-per-kill"));
        }
    }
}
