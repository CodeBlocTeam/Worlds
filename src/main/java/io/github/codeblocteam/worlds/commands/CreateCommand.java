package io.github.codeblocteam.worlds.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.WorldArchetype;

public class CreateCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		String worldName = args.<String>getOne("name").get();
		if (Sponge.getServer().getWorldProperties(worldName).isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Le monde" + worldName + "existe déjà."));
		}
		
//		WorldArchetype.Builder worldBuilder = WorldArchetype.builder();
		if (args.hasAny("n") || args.hasAny("nether")) {
			src.sendMessage(Text.of("prout"));
		}
		return CommandResult.success();
	}

}
