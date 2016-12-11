package io.github.codeblocteam.worlds.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SpawnCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (! (src instanceof Player)) {
			throw new CommandException(Text.of(TextColors.RED, "Commande réservée aux joueurs uniquement"), false);
		}
		
		Player player = (Player) src;
		player.setLocationSafely(player.getWorld().getSpawnLocation());
		
		src.sendMessage(Text.of(TextColors.GREEN, "Téléporté au spawn"));
		return CommandResult.success();
	}

}
