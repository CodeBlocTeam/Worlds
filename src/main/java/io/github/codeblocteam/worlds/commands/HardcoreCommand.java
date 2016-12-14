package io.github.codeblocteam.worlds.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.storage.WorldProperties;

public class HardcoreCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		WorldProperties properties = args.<WorldProperties>getOne("world").get();
		boolean state = (boolean) args.getOne("value").get();
		
		properties.setHardcore(state);
		
		src.sendMessage(Text.of(TextColors.GREEN, "Propiété Hardcore du monde ", TextColors.DARK_GREEN, properties.getWorldName(), TextColors.GREEN, " changée à ", TextColors.DARK_AQUA, state));
		return CommandResult.success();
	}

}
