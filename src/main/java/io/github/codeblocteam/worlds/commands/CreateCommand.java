package io.github.codeblocteam.worlds.commands;

import java.io.IOException;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.gen.WorldGeneratorModifier;
import org.spongepowered.api.world.storage.WorldProperties;

public class CreateCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		String worldName = args.<String>getOne("name").get();
		
		if (Sponge.getServer().getWorldProperties(worldName).isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Le monde ", TextColors.DARK_RED, worldName, TextColors.RED, " existe déjà"), false);
		}
		
		if ((args.hasAny("n") || args.hasAny("nether")) && (args.hasAny("e") || args.hasAny("ender"))) 
			throw new CommandException(Text.of(TextColors.RED, "Vous avez entré plus d'un type de dimension"), false);
		
		WorldArchetype.Builder worldBuilder = WorldArchetype.builder();
		if (args.hasAny("n") || args.hasAny("nether"))
			worldBuilder.dimension(DimensionTypes.NETHER);
		else if (args.hasAny("e") || args.hasAny("ender"))
			worldBuilder.dimension(DimensionTypes.THE_END);
		else
			worldBuilder.dimension(DimensionTypes.OVERWORLD);
		
		Optional<GeneratorType> generator = args.<GeneratorType>getOne("generator");
		Optional<WorldGeneratorModifier> modifier = args.<WorldGeneratorModifier>getOne("modifier");
		Optional<String> seed = args.<String>getOne("seed");
		
		if (generator.isPresent()){
			try {
				worldBuilder.generator(generator.get());
			} catch (Exception e) {
				throw new CommandException(Text.of(TextColors.RED, "Mauvais générateur"), false);
			}
		}
		
		if (modifier.isPresent()){
			try {
				worldBuilder.generatorModifiers(modifier.get());
			} catch (Exception e) {
				throw new CommandException(Text.of(TextColors.RED, "Mauvais modifieur de générateur"), false);
			}
		}
		
		if (args.hasAny("f") || args.hasAny("flat"))
			worldBuilder.seed("3;7,2*3,2;1;".hashCode());
		
		if (seed.isPresent()){
			try {
				Long s = Long.parseLong(seed.get());
				worldBuilder.seed(s);
			} catch (Exception e) {
				throw new CommandException(Text.of(TextColors.RED, "Mauvais seed"), false);
			}
		}
		WorldArchetype archetype = worldBuilder.enabled(true).keepsSpawnLoaded(true).loadsOnStartup(true).build(worldName, worldName);
		WorldProperties properties;
		try {
			properties = Sponge.getServer().createWorldProperties(worldName, archetype);
		} catch (IOException e) {
			throw new CommandException(Text.of(TextColors.RED, "Une erreur est survenue"));
		}
		Sponge.getServer().saveWorldProperties(properties);
		Sponge.getServer().loadWorld(properties);
		src.sendMessage(Text.of(TextColors.GREEN, "Le monde ", TextColors.DARK_GREEN, worldName, TextColors.GREEN, " a été créé et chargé avec succès !"));
		
		return CommandResult.success();
	}

}
