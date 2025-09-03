package net.enderturret.minestuckcompat.api.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

public record WrappedDataProvider(DataProvider delegate, String name) implements DataProvider {

	@Override
	public CompletableFuture<?> run(CachedOutput output) {
		return delegate.run(output);
	}

	@Override
	public String getName() {
		return name;
	}
}