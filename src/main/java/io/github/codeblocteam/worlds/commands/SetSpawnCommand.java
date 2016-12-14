package io.github.codeblocteam.worlds.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.storage.WorldProperties;

import com.flowpowered.math.vector.Vector3i;

public class SetSpawnCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (! (src instanceof Player)) {
			throw new CommandException(Text.of(TextColors.RED, "Commande utilisable par un joueur uniquement"), false);
		}
		Player player = (Player) src;
		WorldProperties properties = player.getWorld().getProperties();
		Vector3i newSpawn = player.getLocation().getBlockPosition();
		
		properties.setSpawnPosition(newSpawn);
		src.sendMessage(Text.of(TextColors.GREEN, "Le spawn du monde ", TextColors.DARK_GREEN, properties.getWorldName(), TextColors.GREEN, " a bien été modifié"));
		return CommandResult.success();
	}

}
