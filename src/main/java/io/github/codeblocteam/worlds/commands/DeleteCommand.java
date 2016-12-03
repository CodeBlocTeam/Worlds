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

public class DeleteCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		String worldName = args.<String>getOne("name").get();
		
		Sponge.getCommandManager().process(src, "world unload " + worldName);
		
		WorldProperties properties = Sponge.getServer().getWorldProperties(worldName).get();
		
		try {
			if (Sponge.getServer().deleteWorld(properties).get()) {

				src.sendMessage(Text.of(TextColors.GREEN, "Le monde ", TextColors.DARK_GREEN, worldName, TextColors.GREEN, " a été supprimé avec succès !"));

				return CommandResult.success();
			}
		} catch (Exception e) {
			//TODO : Mettre l'erreur dans un logger...
		}
		
		src.sendMessage(Text.of(TextColors.RED, "Le monde ", TextColors.DARK_RED, worldName, TextColors.RED, " n'a pas pu être supprimé"));
		
		return CommandResult.empty();
	}

}
