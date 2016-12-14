package io.github.codeblocteam.worlds;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import io.github.codeblocteam.worlds.commands.CommandsAllowedCommand;
import io.github.codeblocteam.worlds.commands.CopyCommand;
import io.github.codeblocteam.worlds.commands.CreateCommand;
import io.github.codeblocteam.worlds.commands.DeleteCommand;
import io.github.codeblocteam.worlds.commands.DifficultyCommand;
import io.github.codeblocteam.worlds.commands.EnabledCommand;
import io.github.codeblocteam.worlds.commands.GamemodeCommand;
import io.github.codeblocteam.worlds.commands.GenerateSpawnOnLoadCommand;
import io.github.codeblocteam.worlds.commands.HardcoreCommand;
import io.github.codeblocteam.worlds.commands.ImportCommand;
import io.github.codeblocteam.worlds.commands.KeepSpawnLoadedCommand;
import io.github.codeblocteam.worlds.commands.ListCommand;
import io.github.codeblocteam.worlds.commands.LoadCommand;
import io.github.codeblocteam.worlds.commands.LoadOnStartupCommand;
import io.github.codeblocteam.worlds.commands.MapFeaturesEnabledCommand;
import io.github.codeblocteam.worlds.commands.PropertiesCommand;
import io.github.codeblocteam.worlds.commands.PvpCommand;
import io.github.codeblocteam.worlds.commands.RenameCommand;
import io.github.codeblocteam.worlds.commands.SetCommand;
import io.github.codeblocteam.worlds.commands.SetSpawnCommand;
import io.github.codeblocteam.worlds.commands.SpawnCommand;
import io.github.codeblocteam.worlds.commands.TpCommand;
import io.github.codeblocteam.worlds.commands.UnloadCommand;
import io.github.codeblocteam.worlds.commands.WorldCommand;
import io.github.codeblocteam.worlds.utils.DifficultiesMap;
import io.github.codeblocteam.worlds.utils.GamemodesMap;

@Plugin(id = "worlds_codebloc", name = "Worlds", version = "0.1")
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
	
	private CommandSpec renameCmd = CommandSpec.builder()
			.description(Text.of("Renommage d'un monde"))
			.permission("worlds.command.world.rename")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))) )
			.executor(new RenameCommand())
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
	
	private CommandSpec commandsAllowedCmd = CommandSpec.builder()
			.description(Text.of("Autauriser/interdire les blocs de commande dans un monde"))
			.permission("worlds.command.world.set.commands")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new CommandsAllowedCommand())
			.build();
	
	private CommandSpec loadOnStartupCmd = CommandSpec.builder()
			.description(Text.of("Changer la propriété loadOnStartup d'un monde"))
			.permission("worlds.command.world.set.loadonstartup")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new LoadOnStartupCommand())
			.build();
	
	private CommandSpec enabledCmd = CommandSpec.builder()
			.description(Text.of("Changer la propriété Enabled d'un monde"))
			.permission("worlds.command.world.set.enabled")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new EnabledCommand())
			.build();
	
	private CommandSpec pvpCmd = CommandSpec.builder()
			.description(Text.of("Autoriser/interdire le PVP d'un monde"))
			.permission("worlds.command.world.set.pvp")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new PvpCommand())
			.build();
	
	private CommandSpec hardcoreCmd = CommandSpec.builder()
			.description(Text.of("Activer/désactiver le Hardcore dans un monde"))
			.permission("worlds.command.world.set.hardcore")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new HardcoreCommand())
			.build();
	
	private CommandSpec keepSpawnLoadedCmd = CommandSpec.builder()
			.description(Text.of("Changer la propriété KeepSpawnLoaded d'un monde"))
			.permission("worlds.command.world.set.keepspawnloaded")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new KeepSpawnLoadedCommand())
			.build();
	
	private CommandSpec generateSpawnOnLoadCmd = CommandSpec.builder()
			.description(Text.of("Changer la propriété GenerateSpawnOnLoad d'un monde"))
			.permission("worlds.command.world.set.generatespawnonload")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new GenerateSpawnOnLoadCommand())
			.build();
	
	private CommandSpec mapFeaturesEnabledCmd = CommandSpec.builder()
			.description(Text.of("Changer la propriété MapFeaturesEnabled d'un monde"))
			.permission("worlds.command.world.set.mapfeaturesenabled")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.bool(Text.of("value"))) )
			.executor(new MapFeaturesEnabledCommand())
			.build();
	
	private CommandSpec gamemodeCmd = CommandSpec.builder()
			.description(Text.of("Changer le mode de jeu par défaut d'un monde"))
			.permission("worlds.command.world.set.gamemode")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.choices(Text.of("value"), new GamemodesMap().get())) )
			.executor(new GamemodeCommand())
			.build();
	
	private CommandSpec difficultyCmd = CommandSpec.builder()
			.description(Text.of("Changer la difficulté d'un monde"))
			.permission("worlds.command.world.set.difficulty")
			.arguments( GenericArguments.onlyOne(GenericArguments.world(Text.of("world"))), GenericArguments.onlyOne(GenericArguments.choices(Text.of("value"), new DifficultiesMap().get())) )
			.executor(new DifficultyCommand())
			.build();
	
	private CommandSpec setSpawnCmd = CommandSpec.builder()
			.description(Text.of("Changer le spawn d'un monde"))
			.permission("worlds.command.world.set.spawn")
			.executor(new SetSpawnCommand())
			.build();
	
	private CommandSpec setCmd = CommandSpec.builder()
			.description(Text.of("Comande pour modifier les propriété d'un monde"))
			.permission("worlds.command.world.set")
			.executor(new SetCommand())
			.child(commandsAllowedCmd, "commandsAllowed")
			.child(loadOnStartupCmd, "loadOnStartup")
			.child(enabledCmd, "enabled")
			.child(pvpCmd, "pvp")
			.child(hardcoreCmd, "hardcore")
			.child(keepSpawnLoadedCmd, "keepSpawnLoaded")
			.child(generateSpawnOnLoadCmd, "generateSpawnOnLoad")
			.child(mapFeaturesEnabledCmd, "structures", "mapFeaturesEnabled")
			.child(gamemodeCmd, "gamemode", "gm")
			.child(difficultyCmd, "difficulty")
			.child(setSpawnCmd, "spawn")
			.build();
	
	private CommandSpec worldCmd = CommandSpec.builder()
			.description(Text.of("Management des mondes"))	//à modifier peut-être
			.permission("worlds.command.world")
			.executor(new WorldCommand())
			.child(createCmd, "create", "c")
			.child(copyCmd, "copy", "cp")
			.child(renameCmd, "rename")
			.child(deleteCmd, "delete", "remove", "d", "rm")
			.child(tpCmd, "tp")
			.child(unloadCmd, "unload", "ul")
			.child(loadCmd, "load", "ld")
			.child(importCmd, "import", "i", "imp")
			.child(propertiesCmd, "properties", "p", "ppt")
			.child(listCmd, "list")
			.child(spawnCmd, "spawn")
			.child(setCmd, "set", "modify", "m")
			.build();
	
	private CommandManager cmdManager = Sponge.getCommandManager();
	
	@Listener
	public void onServerStart (GameInitializationEvent initialization) {
		cmdManager.register(this, worldCmd, "world", "worlds", "wd");
		cmdManager.register(this, spawnCmd, "spawn");
	}

}
