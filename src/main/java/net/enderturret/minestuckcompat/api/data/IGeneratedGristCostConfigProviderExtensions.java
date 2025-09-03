package net.enderturret.minestuckcompat.api.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mraof.minestuck.data.GeneratedGristCostConfigProvider;

/**
 * Extensions for {@link GeneratedGristCostConfigProvider}.
 * @author EnderTurret
 */
public interface IGeneratedGristCostConfigProviderExtensions {

	/**
	 * Allows modifying the generated {@code JsonElement} before it is written to disk.
	 * @param root The root element. Will be a {@link JsonArray}.
	 * @return The root element.
	 */
	public default JsonElement modify(JsonElement root) {
		return root;
	}
}