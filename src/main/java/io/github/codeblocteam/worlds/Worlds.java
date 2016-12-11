package io.github.codeblocteam.worlds;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import io.github.codeblocteam.worlds.commands.CopyCommand;
import io.github.codeblocteam.worlds.commands.CreateCommand;
import io.github.codeblocteam.worlds.commands.DeleteCommand;
import io.github.codeblocteam.worlds.commands.ImportCommand;
import io.github.codeblocteam.worlds.commands.ListCommand;
import io.github.codeblocteam.worlds.commands.LoadCommand;
import io.github.codeblocteam.worlds.commands.PropertiesCommand;
import io.github.codeblocteam.worlds.commands.SpawnCommand;
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
					.buildWith(GenericArguments.onlyOne(GenericArguments.world(Text.of("name")))))
			.executor(new DeleteCommand())
			.build();
	
	private CommandSpec copyCmd = CommandSpec.builder()
			.description(Text.of("Duplication d'un monde"))
			.permission("worlds.command.world.copy")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("srcWorld"))), GenericArguments.onlyOne(GenericArguments.string(Text.of("dstWorld"))) )
			.executor(new CopyCommand())
			.build();
	
	private CommandSpec tpCmd = CommandSpec.builder()
			.description(Text.of("Se téléporter à un monde"))
			.permission("worlds.command.world.tp")
			.arguments(GenericArguments.flags()
					.flag("f").flag("-force")
					.buildWith(GenericArguments.onlyOne(GenericArguments.world(Text.of("name")))))
			.executor(new TpCommand())
			.build();
	
	private CommandSpec unloadCmd = CommandSpec.builder()
			.description(Text.of("Décharger un monde"))
			.permission("worlds.command.world.unload")
			.arguments(GenericArguments.onlyOne(GenericArguments.world(Text.of("name"))))
			.executor(new UnloadCommand())
			.build();
	
	private CommandSpec loadCmd = CommandSpec.builder()
			.description(Text.of("Charger un monde"))
			.permission("worlds.command.world.load")
			.arguments(GenericArguments.onlyOne(GenericArguments.world(Text.of("name"))))
			.executor(new LoadCommand())
			.build();
	
	private CommandSpec importCmd = CommandSpec.builder()
			.description(Text.of("Importer un monde"))
			.permission("worlds.command.world.import")
			.arguments(GenericArguments.flags()
					.flag("n").flag("-nether")
					.flag("e").flag("-ender")
					.buildWith(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
			.executor(new ImportCommand())
			.build();
	
	private CommandSpec propertiesCmd = CommandSpec.builder()
			.description(Text.of("Obtenir les propriétés d'un monde"))
			.permission("worlds.command.world.properties")
			.arguments(GenericArguments.flags()
					.flag("a").flag("-all")
					.buildWith(GenericArguments.onlyOne(GenericArguments.world(Text.of("name")))))
			.executor(new PropertiesCommand())
			.build();
	
	private CommandSpec listCmd = CommandSpec.builder()
			.description(Text.of("Obtenir la liste des mondes"))
			.permission("worlds.command.world.list")
			.executor(new ListCommand())
			.build();
	
	private CommandSpec spawnCmd = CommandSpec.builder()
			.description(Text.of("Aller au spawn du monde"))
			.permission("worlds.command.world.spawn")
			.executor(new SpawnCommand())
			.build();
	
	private CommandSpec worldCmd = CommandSpec.builder()
			.description(Text.of("Management des mondes"))	//à modifier peut-être
			.permission("worlds.command.world")
			.executor(new WorldCommand())
			.child(createCmd, "create", "c")
			.child(copyCmd, "copy", "cp")
			.child(deleteCmd, "delete", "remove", "d", "rm")
			.child(tpCmd, "tp")
			.child(unloadCmd, "unload", "ul")
			.child(loadCmd, "load", "ld")
			.child(importCmd, "import", "i", "imp")
			.child(propertiesCmd, "properties", "p", "ppt")
			.child(listCmd, "list", "l")
			.child(spawnCmd, "spawn", "s")
			.build();
	
	private CommandManager cmdManager = Sponge.getCommandManager();
	
	@Listener
	public void onServerStart (GameInitializationEvent initialization) {
		cmdManager.register(this, worldCmd, "world", "worlds", "wd");
	}

}
