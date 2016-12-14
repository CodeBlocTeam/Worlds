package io.github.codeblocteam.worlds.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.difficulty.Difficulty;
import org.spongepowered.api.world.storage.WorldProperties;

public class DifficultyCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		WorldProperties properties = args.<WorldProperties>getOne("world").get();
		int value = (int) args.<Integer>getOne("value").get();
		
		Difficulty difficulty = Difficulties.PEACEFUL;
		switch (value) {
		case 0:
			difficulty = Difficulties.PEACEFUL;
			break;
		case 1:
			difficulty = Difficulties.EASY;
			break;
		case 2:
			difficulty = Difficulties.NORMAL;
			break;
		case 3:
			difficulty = Difficulties.HARD;
			break;
		}
		
		properties.setDifficulty(difficulty);
		src.sendMessage(Text.of(TextColors.GREEN, "La difficulté du monde ", TextColors.DARK_GREEN, properties.getWorldName(), TextColors.GREEN, " a été changé en ", TextColors.DARK_AQUA, difficulty.toString()));
		return CommandResult.success();
	}

}
