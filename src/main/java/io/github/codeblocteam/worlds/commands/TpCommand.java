package io.github.codeblocteam.worlds.commands;

import java.util.Optional;

import io.github.codeblocteam.worlds.events.PlayerChangeWorldEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

public class TpCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if (! (src instanceof Player)) {
			throw new CommandException(Text.of(TextColors.RED, "Commande utilisable par un joueur uniquement."), false);
		}
		
		Optional<World> optionalWorld = Sponge.getServer().getWorld(args.<WorldProperties>getOne("name").get().getWorldName());
		if (! optionalWorld.isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Ce monde n'existe pas ou n'est pas charg�"));
		}
		
		World world = optionalWorld.get();
		Location<World> location = world.getSpawnLocation();
		
		if (!(args.hasAny("f") || args.hasAny("force"))) {
			TeleportHelper teleportHelper = Sponge.getGame().getTeleportHelper();
			Optional<Location<World>> optionalLocation = teleportHelper.getSafeLocation(location);
			if (! (optionalLocation.isPresent())) {
				throw new CommandException(Text.of(TextColors.RED, "Aucun endroit s�r n'a �t� trouv� pour se t�l�porter dans ce monde. Utilisez -f ou --force pour y aller quand m�me"), false);
			}
			location = optionalLocation.get();
		}
		
		Player player = (Player) src;
		player.setLocation(location);

		PlayerChangeWorldEvent event = new PlayerChangeWorldEvent(Cause.source(src).build(), (Player) src, world);
		Sponge.getEventManager().post(event);
		
		src.sendMessage(Text.of(TextColors.GREEN, "T�l�port� au monde ", TextColors.DARK_GREEN, world.getName()));
		return CommandResult.success();
	}

}
