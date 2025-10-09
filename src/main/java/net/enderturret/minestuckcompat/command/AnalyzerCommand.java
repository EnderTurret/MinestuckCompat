package net.enderturret.minestuckcompat.command;

import java.util.List;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;

import net.enderturret.minestuckcompat.alchemy.analysis.ObtainabilityAnalyzer;

/**
 * The command that runs Minestuck Compat's {@linkplain ObtainabilityAnalyzer obtainability analyzer}.
 * @author EnderTurret
 */
@Internal
public final class AnalyzerCommand {

	/**
	 * Registers the {@code AnalyzerCommand} to the specified {@code CommandDispatcher}.
	 * @param dispatcher The dispatcher to register to.
	 */
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("minestuckcompat").then(Commands.literal("analyze")
				.requires(src -> src.hasPermission(4))
				.executes(AnalyzerCommand::analyze)));
	}

	private static int analyze(CommandContext<CommandSourceStack> ctx) {
		final CommandSourceStack src = ctx.getSource();
		final MinecraftServer server = src.getServer();
		final ResourceManager resourceManager = server.getResourceManager();

		final ObtainabilityAnalyzer analyzer = new ObtainabilityAnalyzer(server.getRecipeManager(), resourceManager);

		final List<Item> unobtainable = analyzer.check((msg, error) -> {
			if (error)
				src.sendFailure(msg);
			else
				src.sendSuccess(() -> msg, false);
		});

		return Command.SINGLE_SUCCESS;
	}
}