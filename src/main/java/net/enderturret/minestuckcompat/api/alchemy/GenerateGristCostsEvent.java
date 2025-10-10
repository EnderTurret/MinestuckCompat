package net.enderturret.minestuckcompat.api.alchemy;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mraof.minestuck.alchemy.recipe.generator.GristCostGenerator;
import com.mraof.minestuck.alchemy.recipe.generator.recipe.RecipeInterpreter;

import net.minecraft.world.item.crafting.RecipeManager;

import net.neoforged.bus.api.Event;

/**
 * Fired during the {@link GristCostGenerator}'s lifecycle, allowing mods to respond to the generator's state.
 * @author EnderTurret
 * @see Pre GenerateGristCostsEvent.Pre
 * @see Post GenerateGristCostsEvent.Post
 * @see RegisterGristCostProvidersEvent
 */
public sealed abstract class GenerateGristCostsEvent extends Event {

	private final RecipeManager recipeManager;

	/**
	 * Constructs a new {@code GenerateGristCostsEvent}.
	 * @param recipeManager The recipe manager.
	 */
	@Internal
	protected GenerateGristCostsEvent(RecipeManager recipeManager) {
		this.recipeManager = recipeManager;
	}

	public RecipeManager getRecipeManager() {
		return recipeManager;
	}

	/**
	 * Fired just before the {@link GristCostGenerator} begins generating grist costs.
	 * This is a good time to set up any state needed for {@linkplain RecipeInterpreter recipe interpreters}.
	 * @author EnderTurret
	 */
	public static final class Pre extends GenerateGristCostsEvent {

		/**
		 * Constructs a new {@code GenerateGristCostsEvent.Pre}.
		 * @param recipeManager The recipe manager.
		 */
		@Internal
		public Pre(RecipeManager recipeManager) {
			super(recipeManager);
		}
	}

	/**
	 * Fired just after the {@link GristCostGenerator} finishes generating grist costs.
	 * This is a good time to set up grist cost caches or release state built during {@link Pre GenerateGristCostsEvent.Pre}.
	 * @author EnderTurret
	 */
	public static final class Post extends GenerateGristCostsEvent {

		/**
		 * Constructs a new {@code GenerateGristCostsEvent.Post}.
		 * @param recipeManager The recipe manager.
		 */
		@Internal
		public Post(RecipeManager recipeManager) {
			super(recipeManager);
		}
	}
}