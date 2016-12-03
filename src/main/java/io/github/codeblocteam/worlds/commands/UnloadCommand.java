package io.github.codeblocteam.worlds.commands;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

public class UnloadCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		String worldName = args.<String>getOne("name").get();
		Optional<World> optionalWorld = Sponge.getServer().getWorld(worldName);
		if (! optionalWorld.isPresent()) {
			src.sendMessage(Text.of(TextColors.GREEN, "Aucun monde \"", TextColors.DARK_GREEN, worldName, TextColors.GREEN,  "\" n'est chargé"));
			return CommandResult.success();
		}
		
		World world = optionalWorld.get();
		World defaultWorld = Sponge.getServer().getWorld(Sponge.getServer().getDefaultWorld().get().getWorldName()).get();
		
		if ( world.getUniqueId().equals(defaultWorld.getUniqueId()) ) {
			throw new CommandException(Text.of(TextColors.RED, "Vous ne pouvez pas décharger le monde par défaut"), false);
		}
		
		src.sendMessage(Text.of(TextColors.YELLOW, "Déchargement du monde " + worldName + "..."));
		
		for (Entity entity : world.getEntities()) {
			if (entity instanceof Player) {
				entity.setLocationSafely(defaultWorld.getSpawnLocation());
			}
		}
		
		if (!Sponge.getServer().unloadWorld(world)) {
			throw new CommandException(Text.of(TextColors.RED, "Le monde ", TextColors.DARK_RED, worldName, TextColors.RED, " n'a pas pu être déchargé"), false);
		}
		
		src.sendMessage(Text.of(TextColors.GREEN, "Le monde ", TextColors.DARK_GREEN, worldName, TextColors.GREEN, " a été déchargé avec succès !"));
		return CommandResult.success();
	}

}
