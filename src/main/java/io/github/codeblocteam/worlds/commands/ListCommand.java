package io.github.codeblocteam.worlds.commands;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

public class ListCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		src.sendMessage(Text.of(TextColors.GOLD, "Liste des mondes : ", TextColors.WHITE, "[ ", TextColors.GREEN, "chargé", TextColors.WHITE, " / ", TextColors.RED, "non chargé", TextColors.WHITE, " ]"));
		Builder builder = Text.builder();
		for (WorldProperties properties : Sponge.getServer().getAllWorldProperties()){
			
			Optional<World> optionalWorld = Sponge.getServer().getWorld(properties.getUniqueId());
			
			if (optionalWorld.isPresent()) {
				builder.append(Text.builder().append(Text.of(TextColors.GREEN, properties.getWorldName() + "  ")).onHover(TextActions.showText(Text.of(TextColors.WHITE, "Propriétés"))).onClick(TextActions.runCommand("/world properties " + properties.getWorldName())).build());
			} else {
				builder.append(Text.builder().append(Text.of(TextColors.RED, properties.getWorldName() + "  ")).onHover(TextActions.showText(Text.of(TextColors.WHITE, "Propriétés"))).onClick(TextActions.runCommand("/world properties " + properties.getWorldName())).build());
			}
		}
		src.sendMessage(builder.build());
		return CommandResult.success();
	}

}
