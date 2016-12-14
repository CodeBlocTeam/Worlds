package io.github.codeblocteam.worlds.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.storage.WorldProperties;

public class GamemodeCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		WorldProperties properties = args.<WorldProperties>getOne("world").get();
		int value = (int) args.<Integer>getOne("value").get();
		
		GameMode gamemode = GameModes.SURVIVAL;
		switch (value) {
		case 0:
			gamemode = GameModes.SURVIVAL;
			break;
		case 1:
			gamemode = GameModes.CREATIVE;
			break;
		case 2:
			gamemode = GameModes.ADVENTURE;
			break;
		case 3:
			gamemode = GameModes.SPECTATOR;
			break;
		}
		
		properties.setGameMode(gamemode);
		src.sendMessage(Text.of(TextColors.GREEN, "Le mode de jeu par défaut du monde ", TextColors.DARK_GREEN, properties.getWorldName(), TextColors.GREEN, " a été changé en ", TextColors.DARK_AQUA, gamemode.toString()));
		return CommandResult.success();
	}

}
