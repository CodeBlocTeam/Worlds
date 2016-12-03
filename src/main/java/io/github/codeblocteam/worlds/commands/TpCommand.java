package io.github.codeblocteam.worlds.commands;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;
import org.spongepowered.api.world.World;

public class TpCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if (! (src instanceof Player)) {
			throw new CommandException(Text.of(TextColors.RED, "Commande utilisable par un joueur uniquement."), false);
		}
		
		Optional<World> optionalWorld = Sponge.getServer().getWorld(args.<String>getOne("name").get());
		if (! optionalWorld.isPresent()) {
			throw new CommandException(Text.of(TextColors.RED, "Ce monde n'existe pas ou n'est pas chargé"));
		}
		
		World world = optionalWorld.get();
		Location<World> location = world.getSpawnLocation();
		
		if (!(args.hasAny("f") || args.hasAny("force"))) {
			TeleportHelper teleportHelper = Sponge.getGame().getTeleportHelper();
			Optional<Location<World>> optionalLocation = teleportHelper.getSafeLocation(location);
			if (! (optionalLocation.isPresent())) {
				throw new CommandException(Text.of(TextColors.RED, "Aucun endroit sûr n'a été trouvé pour se téléporter dans ce monde. Utilisez -f ou --force pour y aller quand même"), false);
			}
			location = optionalLocation.get();
		}
		
		Player player = (Player) src;
		player.setLocation(location);
		
		src.sendMessage(Text.of(TextColors.GREEN, "Téléportation au monde ", TextColors.DARK_GREEN, world.getName()));
		return CommandResult.success();
	}

}
