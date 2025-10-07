package net.enderturret.minestuckcompat.command;

import java.util.List;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;

import net.enderturret.minestuckcompat.MinestuckCompat;
import net.enderturret.minestuckcompat.alchemy.analysis.ObtainabilityAnalyzer;

public final class AnalyzerCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("minestuckcompat").then(Commands.literal("analyze")
				.requires(src -> src.hasPermission(4))
				.executes(AnalyzerCommand::analyze)));
	}

	private static int analyze(CommandContext<CommandSourceStack> ctx) {
		final MinecraftServer server = ctx.getSource().getServer();

		final ResourceManager resourceManager = server.getResourceManager();
		final ObtainabilityAnalyzer analyzer = new ObtainabilityAnalyzer(server.getRecipeManager(), resourceManager);

		final List<Item> unobtainable = analyzer.check();

		if (!unobtainable.isEmpty())
			MinestuckCompat.LOGGER.info("Found {} unobtainable items!", unobtainable.size());

		return Command.SINGLE_SUCCESS;
	}
}