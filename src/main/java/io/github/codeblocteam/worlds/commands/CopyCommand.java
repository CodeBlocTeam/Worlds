package io.github.codeblocteam.worlds.commands;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.storage.WorldProperties;

public class CopyCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		WorldProperties properties = args.<WorldProperties>getOne("srcWorld").get();
		String newWorldName = args.<String>getOne("dstWorld").get();

		if (Sponge.getServer().getWorldProperties(newWorldName).isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Le monde ", TextColors.DARK_RED, newWorldName, TextColors.RED, " éxiste déjà"), false);
		}
		
		Optional<WorldProperties> newProperties = null;
		
		try {
			CompletableFuture<Optional<WorldProperties>> newWorldProperties = Sponge.getServer().copyWorld(properties, newWorldName);
			while (! newWorldProperties.isDone()) {
			}
			newProperties = newWorldProperties.get();
			
		} catch (InterruptedException | ExecutionException e){
			e.printStackTrace();
		}
		
		if (! newProperties.isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Une erreur est survenue lors de la duplication du monde"), false);
		}
		
		src.sendMessage(Text.of(TextColors.GREEN, "Le monde a été dupliqué avec succès"));
		return CommandResult.success();
	}

}
