package io.github.codeblocteam.worlds.commands;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.storage.WorldProperties;

import com.flowpowered.math.vector.Vector3i;

public class PropertiesCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		WorldProperties properties = args.<WorldProperties>getOne("name").get();
		String worldName = properties.getWorldName();
		List<Text> list = new ArrayList<>();
		
		list.add(Text.of(TextColors.DARK_AQUA, "Dimension", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.getDimensionType().toString().toLowerCase() ));
		list.add(Text.of(TextColors.DARK_AQUA, "Mode de jeu", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.getGameMode().toString().toLowerCase() ));
		list.add(Text.of(TextColors.DARK_AQUA, "Difficulté", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.getDifficulty().toString().toLowerCase() ));
		list.add(Text.of(TextColors.DARK_AQUA, "Hardcore", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.isHardcore() ));
		list.add(Text.of(TextColors.DARK_AQUA, "PVP", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.isPVPEnabled() ));
		list.add(Text.of(TextColors.DARK_AQUA, "Commandes", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.areCommandsAllowed() ));
		
		if (args.hasAny("a") || args.hasAny("all")) {
			Vector3i spawn = properties.getSpawnPosition();
			list.add(Text.of(TextColors.DARK_AQUA, "Structures", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.usesMapFeatures() ));
			list.add(Text.of(TextColors.DARK_AQUA, "Enabled", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.isEnabled() ));
			list.add(Text.of(TextColors.DARK_AQUA, "LoadsOnStartup", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.loadOnStartup() ));
			list.add(Text.of(TextColors.DARK_AQUA, "Seed", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.getSeed() ));
			list.add(Text.of(TextColors.DARK_AQUA, "Spawn", TextColors.WHITE, " : ", TextStyles.ITALIC, "X=", spawn.getX(), " Y=", spawn.getY(), " Z=", spawn.getZ() ));
			list.add(Text.of(TextColors.DARK_AQUA, "KeepSpawnLoaded", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.doesKeepSpawnLoaded() ));
			list.add(Text.of(TextColors.DARK_AQUA, "GenerateSpawnOnLoad", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.doesGenerateSpawnOnLoad() ));
			list.add(Text.of(TextColors.DARK_AQUA, "GenerateBonusChest", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.doesGenerateBonusChest() ));
			list.add(Text.of(TextColors.DARK_AQUA, "GeneratorModifiers", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.getGeneratorModifiers().toString() ));
			list.add(Text.of(TextColors.DARK_AQUA, "TotalTime", TextColors.WHITE, " : ", TextStyles.ITALIC, properties.getTotalTime() ));
		}
		
		if (src instanceof Player && (args.hasAny("a") || args.hasAny("all"))) {
			PaginationList.Builder book = PaginationList.builder();
			if (Sponge.getServer().getWorld(worldName).isPresent()) {
				book.title(Text.of(TextColors.GOLD, "Propriétés du monde ", TextColors.AQUA, worldName, TextColors.WHITE, " [", TextColors.GREEN, "chargé", TextColors.WHITE, "]"));
			} else {
				book.title(Text.of(TextColors.GOLD, "Propriétés du monde ", TextColors.AQUA, worldName, TextColors.WHITE, " [", TextColors.RED, "non chargé", TextColors.WHITE, "]"));
			}

			book.contents(list);

			book.sendTo(src);
		} else {
			if (Sponge.getServer().getWorld(worldName).isPresent()) {
				src.sendMessage(Text.of(TextColors.GOLD, "Propriétés du monde ", TextColors.AQUA, worldName, TextColors.WHITE, " [", TextColors.GREEN, "chargé", TextColors.WHITE, "]"));
			} else {
				src.sendMessage(Text.of(TextColors.GOLD, "Propriétés du monde ", TextColors.AQUA, worldName, TextColors.WHITE, " [", TextColors.RED, "non chargé", TextColors.WHITE, "]"));
			}
			for (Text text : list) {
				src.sendMessage(text);
			}
		}
		
		return CommandResult.success();
	}

}
