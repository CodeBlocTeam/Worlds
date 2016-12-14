package io.github.codeblocteam.worlds.commands;

import java.util.ArrayList;
import java.util.List;

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

public class WorldCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		List<Text> list = new ArrayList<>();
		list.add(Text.of(TextColors.GRAY, TextStyles.ITALIC, "Plugin de gestion de mondes de l'équipe CodeBloc"));
		list.add(Text.of(TextColors.LIGHT_PURPLE, TextStyles.ITALIC, "Commande principale :"));
		list.add(Text.of(TextColors.YELLOW, "/worlds /world /wd", TextColors.WHITE, " : ", TextStyles.ITALIC, "Affiche cette aide"));
		list.add(Text.of(" "));
		list.add(Text.of(TextColors.LIGHT_PURPLE, TextStyles.ITALIC, "Sous commandes :"));
		
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "list", TextColors.WHITE, " : ", TextStyles.ITALIC, "Liste les mondes se trouvant sur le serveur"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "properties <monde> [-a, --all]", TextColors.WHITE, " : ", TextStyles.ITALIC, "Donne les propriétés du monde spécifié"));
		
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "spawn (ou /spawn)", TextColors.WHITE, " : ", TextStyles.ITALIC, "Vous téléporte au spawn du monde dans lequel vous vous trouvez"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "tp <monde>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Vous téléporte au spawn du monde spécifié"));
		
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "load <world>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Charge un monde"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "unload", TextColors.WHITE, " : ", TextStyles.ITALIC, "Décharge un monde"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "create <nom> [-n, --nether, -e, --ender, -f, --flat, --generator=<générateur>, --seed=<seed>, --modifier=<modifieur>]", TextColors.WHITE, " : ", TextStyles.ITALIC, "Crée un monde"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "delete <monde> ", TextColors.WHITE, " : ", TextStyles.ITALIC, "Supprimme un monde. Ne pas recréer un monde du même nom sans redémarrer le serveur !"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "copy <monde> <nom>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Copie un monde avec un nouveau nom"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "rename <monde> <nom>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Renomme un monde"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "import <nom> ", TextColors.WHITE, " : ", TextStyles.ITALIC, "Importe un monde se trouvant dans les fichiers du serveur et le charge"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world ",TextColors.YELLOW, "set", TextColors.WHITE, " : ", TextStyles.ITALIC, "Modifie les propriétés d'un monde. Voir sous-commandes associées"));
		
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "spawn", TextColors.WHITE, " : ", TextStyles.ITALIC, "Place le spawn du monde à votre position actuelle"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "gamemode <monde> <mode de jeu>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Change le mode de jeu par défaut du monde"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "difficulty <monde> <difficulté>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Change la difficulté du monde"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "pvp <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Active ou non le PVP"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "hardcore <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Active ou non le mode hardcore"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "commandsAllowed <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Autorise ou non les commandes"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "structures <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Active ou non la génération de structures"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "keepSpawnLoaded <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Définit si les spawn chunks sont chargés en permanence"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "loadOnStartup <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Définit si le monde se charge au démarrage du serveur"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "enabled <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Change la propriété \"enabled\" du monde"));
		list.add(Text.of(TextColors.DARK_GRAY, "/world set ",TextColors.YELLOW, "generateSpawnOnLoad <monde> <booléen>", TextColors.WHITE, " : ", TextStyles.ITALIC, "Définit si le monde génère le spawn lors de son chargement"));
		
		if (src instanceof Player) {
			PaginationList.Builder book = PaginationList.builder();
			book.title(Text.of(TextColors.GOLD, "Manuel d'utilisation du plugin Worlds"));
			book.contents(list);
			book.sendTo(src);
		} else {
			src.sendMessage(Text.of(TextColors.GOLD, "Manuel d'utilisation du plugin Worlds"));
			for (Text text : list) {
				src.sendMessage(text);
			}
		}
		
		return CommandResult.success();
	}

}
