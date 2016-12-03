package io.github.codeblocteam.worlds;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import io.github.codeblocteam.worlds.commands.CreateCommand;
import io.github.codeblocteam.worlds.commands.DeleteCommand;
import io.github.codeblocteam.worlds.commands.TpCommand;
import io.github.codeblocteam.worlds.commands.UnloadCommand;
import io.github.codeblocteam.worlds.commands.WorldCommand;

@Plugin(id = "worldscb", name = "Worlds", version = "0.1")
public class Worlds {
	
	private CommandSpec createCmd = CommandSpec.builder()
			.description(Text.of("Création d'un monde"))
			.permission("worlds.command.world.create")
			.arguments(GenericArguments.flags()
					.flag("n").flag("-nether")
					.flag("e").flag("-ender")
					.valueFlag(GenericArguments.string(Text.of("generator")), "-generator")
					.valueFlag(GenericArguments.string(Text.of("modifier")), "-modifier")
					.valueFlag(GenericArguments.string(Text.of("seed")), "-seed")
					.flag("f").flag("-flat")
					.buildWith(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
			.executor(new CreateCommand())
			.build();
	
	private CommandSpec deleteCmd = CommandSpec.builder()
			.description(Text.of("Suppression d'un monde"))
			.permission("worlds.command.world.delete")
			.arguments(GenericArguments.flags()
					.flag("f").flag("-force")
					.buildWith(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
			.executor(new DeleteCommand())
			.build();
	
	private CommandSpec tpCmd = CommandSpec.builder()
			.description(Text.of("Se téléporter à un monde"))
			.permission("worlds.command.world.tp")
			.arguments(GenericArguments.flags()
					.flag("f").flag("-force")
					.buildWith(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
			.executor(new TpCommand())
			.build();
	
	private CommandSpec unloadCmd = CommandSpec.builder()
			.description(Text.of("Décharger un monde"))
			.permission("worlds.command.world.unload")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
			.executor(new UnloadCommand())
			.build();
	
	private CommandSpec worldCmd = CommandSpec.builder()
			.description(Text.of("Management des mondes"))	//à modifier peut-être
			.permission("worlds.command.world")
			.executor(new WorldCommand())
			.child(createCmd, "create", "c")
			.child(deleteCmd, "delete", "d")
			.child(tpCmd, "tp")
			.child(unloadCmd, "unload", "ul")
			.build();
	
	private CommandManager cmdManager = Sponge.getCommandManager();
	
	@Listener
	public void onServerStart (GameInitializationEvent initialization) {
		cmdManager.register(this, worldCmd, "world", "worlds", "wd");
	}

}
