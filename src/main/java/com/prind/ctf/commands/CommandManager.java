package com.prind.ctf.commands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandHelpHandler;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.execution.FilteringCommandSuggestionProcessor;
import cloud.commandframework.extra.confirmation.CommandConfirmationManager;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.minecraft.extras.AudienceProvider;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import cloud.commandframework.paper.PaperCommandManager;
import com.prind.ctf.CTF;
import com.prind.ctf.commands.cmds.*;
import com.prind.ctf.commands.cmds.game.GameJoinCommand;
import com.prind.ctf.commands.cmds.game.GameLeaveCommand;
import com.prind.ctf.commands.cmds.game.GameListCommand;
import com.prind.ctf.commands.cmds.game.GameTestCommand;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.text;

public class CommandManager {

  private final CTF plugin;
  public PaperCommandManager<CommandSender> manager;
  private CommandConfirmationManager<CommandSender> confirmationManager;
  private AnnotationParser<CommandSender> annotationParser;
  private Audience audience;
  private MinecraftHelp<CommandSender> minecraftHelp;

  public CommandManager(CTF plugin) {
    this.plugin = plugin;
    initCommands();
  }

  private void initCommands() {
    try {
      manager = new PaperCommandManager<>(
        plugin,
        CommandExecutionCoordinator.simpleCoordinator(),
        Function.identity(),
        Function.identity()
      );
    } catch (Exception e) {
      plugin.getLogger().severe("Failed to initialize the command manager");
      Bukkit.getPluginManager().disablePlugin(plugin);
      return;
    }

    // Use contains to filter suggestions instead of default startsWith
    this.manager.commandSuggestionProcessor(new FilteringCommandSuggestionProcessor<>(
      FilteringCommandSuggestionProcessor.Filter.<CommandSender>contains(true).andTrimBeforeLastSpace()
    ));

    this.audience = Audience.audience();

    this.minecraftHelp = new MinecraftHelp<>(
      "/ctf help",
      AudienceProvider.nativeAudience(),
      this.manager
    );

    if (this.manager.hasCapability(CloudBukkitCapabilities.BRIGADIER))
      this.manager.registerBrigadier();

    if (this.manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION))
      this.manager.registerAsynchronousCompletions();

    this.confirmationManager = new CommandConfirmationManager<>(
      /* Timeout */ 30L,
      /* Timeout unit */ TimeUnit.SECONDS,
      /* Action when confirmation is required */ context -> context.getCommandContext().getSender().sendMessage(
      ChatColor.RED + "Confirmation required. Confirm using /ctf confirm."),
      /* Action when no confirmation is pending */ sender -> sender.sendMessage(
      ChatColor.RED + "You don't have any pending commands.")
    );

    this.confirmationManager.registerConfirmationProcessor(this.manager);

    final Function<ParserParameters, CommandMeta> commandMetaFunction = p ->
      CommandMeta.simple()
        // This will allow you to decorate commands with descriptions
        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
        .build();

    annotationParser = new AnnotationParser<>(
      manager,
      CommandSender.class,
      commandMetaFunction
    );

    new MinecraftExceptionHandler<CommandSender>()
      .withInvalidSyntaxHandler()
      .withInvalidSenderHandler()
      .withNoPermissionHandler()
      .withArgumentParsingHandler()
      .withCommandExecutionHandler()
      .withDecorator(
        component -> text()
          .append(text("[", NamedTextColor.DARK_GRAY))
          .append(text("CTF", NamedTextColor.GOLD))
          .append(text("] ", NamedTextColor.DARK_GRAY))
          .append(component).build()
      ).apply(this.manager, AudienceProvider.nativeAudience());

    this.constructCommands();
  }

  private void constructCommands() {
    this.annotationParser.parse(this);
    this.annotationParser.parse(new GameJoinCommand());
    this.annotationParser.parse(new GameListCommand());
    this.annotationParser.parse(new GameTestCommand());
    this.annotationParser.parse(new KitCommand());
    this.annotationParser.parse(new KitGetCommand());
    this.annotationParser.parse(new GameLeaveCommand());
    // Parse all @CommandContainer-annotated classes
    try {
      this.annotationParser.parseContainers();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    final Command.Builder<CommandSender> builder = this.manager.commandBuilder("ctf");

    this.manager.command(builder.literal("confirm")
      .meta(CommandMeta.DESCRIPTION, "Confirm a pending command")
      .handler(this.confirmationManager.createConfirmationExecutionHandler()));
  }

  @CommandMethod("ctf help [query]")
  @CommandDescription("Help menu")
  public void commandHelp(
    final @NonNull CommandSender sender,
    final @Argument(value = "query", suggestions = "help_queries") @Greedy String query
  ) {
    this.minecraftHelp.queryCommands(query == null ? "" : query, sender);
  }

  @Suggestions("help_queries")
  public @NonNull List<String> suggestHelpQueries(
    final @NonNull CommandContext<CommandSender> ctx,
    final @NonNull String input
  ) {
    return this.manager.createCommandHelpHandler().queryRootIndex(ctx.getSender()).getEntries().stream()
      .map(CommandHelpHandler.VerboseHelpEntry::getSyntaxString)
      .collect(Collectors.toList());
  }

}