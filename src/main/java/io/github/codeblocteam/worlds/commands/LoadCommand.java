package io.github.codeblocteam.worlds.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.storage.WorldProperties;

public class LoadCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if (! Sponge.getServer().loadWorld(args.<WorldProperties>getOne("name").get()).isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Aucun monde portant le nom ",TextColors.DARK_RED, args.<WorldProperties>getOne("name").get().getWorldName(), TextColors.RED, " n'a pu �tre charg�"), false);
		}
		
		src.sendMessage(Text.of(TextColors.GREEN, "Le monde ", TextColors.DARK_GREEN, args.<WorldProperties>getOne("name").get().getWorldName(), TextColors.GREEN, " a �t� charg� avec succ�s"));
		return CommandResult.success();
	}

}
