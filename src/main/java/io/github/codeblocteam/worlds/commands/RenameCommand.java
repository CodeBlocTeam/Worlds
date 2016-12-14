package io.github.codeblocteam.worlds.commands;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.storage.WorldProperties;

public class RenameCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		WorldProperties properties = args.<WorldProperties>getOne("world").get();
		String worldName = properties.getWorldName();
		String newWorldName = args.<String>getOne("name").get();
		
		if (Sponge.getServer().getWorld(worldName).isPresent()) {
			throw new CommandException(Text.of(TextColors.DARK_RED, properties.getWorldName(), TextColors.RED, " ne doit pas être chargé pour pouvoir le rennomer"), false);
		}
		
		if (Sponge.getServer().getWorldProperties(newWorldName).isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Un monde nommé", TextColors.DARK_RED, newWorldName, TextColors.RED, " existe déjà"), false);
		}
		
		Optional<WorldProperties> rename = Sponge.getServer().renameWorld(properties, newWorldName);

		if (!rename.isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Impossible de renommer ", TextColors.DARK_RED, worldName), false);
		}
		
		src.sendMessage(Text.of(TextColors.GREEN, "Le monde ", TextColors.DARK_GREEN, worldName, TextColors.GREEN, " a été rennome avec succès en ", TextColors.DARK_GREEN, newWorldName));
		return CommandResult.success();
	}

}
