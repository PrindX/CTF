package com.prind.ctf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand extends Command {

    private boolean requiresPlayer = false;

    private final List<AbstractSubCommand> subCommandList;

    public AbstractCommand(String name) {
        super(name);
        this.subCommandList = new ArrayList<>();
    }

    public abstract void perform(CommandArgs commandContext);

    public boolean execute(CommandArgs commandContext) {
        FileConfiguration messages = Assistant.getInstance().getMessagesConfig().getConfiguration();
        if (commandContext.getArgs().length > 0) {
            for (AbstractSubCommand abstractSubCommand : subCommandList) {
                if (!abstractSubCommand.getAliasList().contains(commandContext.getArgs()[0])) {
                    continue;
                }

                if (abstractSubCommand.getPermission() != null && !commandContext.getCommandSender().hasPermission(abstractSubCommand.getPermission())) {
                    ChatUtil.message((Player) commandContext.getCommandSender(), messages.getString("no-permission"));
                    return false;
                }

                if (abstractSubCommand.isPlayer() && !(commandContext.getCommandSender() instanceof Player)) {
                    ChatUtil.message((Player) commandContext.getCommandSender(), messages.getString("players-only")
                            .replace("{prefix}", messages.getString("prefix")));
                    return false;
                }

                if (abstractSubCommand.getRequiredArgs() > commandContext.getArgs().length) {
                    ChatUtil.message((Player) commandContext.getCommandSender(), messages.getString("usage")
                            .replace("{prefix}", messages.getString("prefix"))
                            .replace("{usage}", abstractSubCommand.getUsage()));
                    return false;
                }

                abstractSubCommand.perform(commandContext);
                return true;
            }
        }

        if (getPermission() != null && !commandContext.getCommandSender().hasPermission(getPermission())) {
            ChatUtil.message((Player) commandContext.getCommandSender(), messages.getString("no-permission"));
            return false;
        }

        if (requiresPlayer && !(commandContext.getCommandSender() instanceof Player)) {
            ChatUtil.message((Player) commandContext.getCommandSender(), messages.getString("players-only")
                    .replace("{prefix}", messages.getString("prefix")));
            return false;
        }

        perform(commandContext);
        return true;
    }

    public void setRequiresPlayer(boolean requiresPlayer) {
        this.requiresPlayer = requiresPlayer;
    }

    public void addSubCommands(AbstractSubCommand... abstractSubCommands) {
        subCommandList.addAll(Arrays.asList(abstractSubCommands));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return this.execute(new CommandArgs(sender, args, commandLabel));
    }

}
