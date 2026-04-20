package de.diruptio.luckyblock;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.RegistryBuilder;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.BlockRegistryEntry;
import io.papermc.paper.registry.data.ItemRegistryEntry;
import io.papermc.paper.registry.event.RegistryComposeEvent;
import io.papermc.paper.registry.event.RegistryEvents;
import net.kyori.adventure.key.Key;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public final class LuckyBlockBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(@NonNull BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(RegistryEvents.BLOCK.compose(), this::registerBlocks);
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ITEM.compose(), this::registerItems);
    }

    private void registerBlocks(@NotNull RegistryComposeEvent<BlockType, BlockRegistryEntry.Builder> event) {
        register(event, LuckyBlock.KEY, LuckyBlock.BUILDER);
    }

    private void registerItems(@NotNull RegistryComposeEvent<ItemType, ItemRegistryEntry.Builder> event) {
        register(event, LuckyBlock.KEY, LuckyBlock.ITEM_BUILDER);
    }

    private <T, B extends RegistryBuilder<T>> void register(
            @NotNull RegistryComposeEvent<T, B> event,
            @NotNull Key key,
            @NotNull Consumer<B> builderConsumer
    ) {
        event.registry().register(TypedKey.create(event.registryKey(), key), builderConsumer);
    }
}
