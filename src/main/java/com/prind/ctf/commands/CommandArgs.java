package com.prind.ctf.commands;

import lombok.*;
import lombok.experimental.*;
import org.bukkit.command.CommandSender;

@Getter
@AllArgsConstructor
@Accessors(makeFinal = true)
public class CommandArgs {

    private CommandSender commandSender;
    private String[] args;
    private String label;
}
