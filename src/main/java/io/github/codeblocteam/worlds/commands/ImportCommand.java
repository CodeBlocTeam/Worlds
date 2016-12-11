package io.github.codeblocteam.worlds.commands;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.storage.WorldProperties;

public class ImportCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		String worldName = args.<String>getOne("name").get();
		String defaultWorldName = Sponge.getServer().getDefaultWorldName();
		
		if (Sponge.getServer().getWorld(worldName).isPresent()) {
			throw new CommandException(Text.of(TextColors.DARK_RED, worldName, TextColors.RED, " est déjà chargé"), false);
		}
		
		File spongeDataFile = new File(defaultWorldName + File.separator + worldName, "level_sponge.dat");
		if (spongeDataFile.exists()) {
			src.sendMessage(Text.of(TextColors.YELLOW, "Monde Sponge détecté, utilisez la commande load pour le charger"));
			return CommandResult.success();
		}
		
		File worldDataFile = new File(defaultWorldName + File.separator + worldName, "level.dat");
		if (!worldDataFile.exists())
			throw new CommandException(Text.of(TextColors.RED, "Aucun monde nommé ", TextColors.DARK_RED, worldName, TextColors.RED, " n'a été trouvé"), false);
		
		if ((args.hasAny("n") || args.hasAny("nether")) && (args.hasAny("e") || args.hasAny("ender"))) 
			throw new CommandException(Text.of(TextColors.RED, "Vous avez entré plus d'un type de dimension"), false);
		
		
		WorldArchetype.Builder worldBuilder = WorldArchetype.builder();
		
		if (args.hasAny("n") || args.hasAny("nether"))
			worldBuilder.dimension(DimensionTypes.NETHER);
		else if (args.hasAny("e") || args.hasAny("ender"))
			worldBuilder.dimension(DimensionTypes.THE_END);
		else
			worldBuilder.dimension(DimensionTypes.OVERWORLD);
		
		WorldArchetype archetype = worldBuilder.enabled(true).keepsSpawnLoaded(true).loadsOnStartup(true).build(worldName, worldName);
		WorldProperties properties;
		
		try {
			properties = Sponge.getServer().createWorldProperties(worldName, archetype);
		} catch (IOException e) {
			throw new CommandException(Text.of(TextColors.RED, "Une erreur est survenue"));
		}
		
		Sponge.getServer().saveWorldProperties(properties);
		Sponge.getServer().loadWorld(properties);
		
		src.sendMessage(Text.of(TextColors.GREEN, "Le monde ", TextColors.DARK_GREEN, worldName, TextColors.GREEN, " a été importé et chargé avec succès !"));
		return CommandResult.success();
	}

}
