package net.enderturret.minestuckcompat.alchemy;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraft.core.HolderLookup;

import net.neoforged.neoforge.common.conditions.ICondition;

@Internal
public record ContextHolder(HolderLookup.Provider registryAccess, ICondition.IContext context) {}