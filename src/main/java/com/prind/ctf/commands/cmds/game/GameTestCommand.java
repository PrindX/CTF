package com.prind.ctf.commands.cmds.game;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.Team;
import com.prind.ctf.game.GameManager;
import de.tr7zw.changeme.nbtapi.NBTBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GameTestCommand {

    private GameManager gameManager = CTF.getInstance().getGameManager();
    private final FileConfiguration gameConfig = CTF.getInstance().getGameConfig().getConfiguration();

    @CommandMethod("game test <game>")
    public void onTestCommand(Player player, @Argument("game")String name) {
        Game game = gameManager.getGameByName(name);

        MVWorldManager worldManager = CTF.getInstance().getMultiverseCore().getMVWorldManager();
        for (Team team : game.getTeams()) {

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
}
