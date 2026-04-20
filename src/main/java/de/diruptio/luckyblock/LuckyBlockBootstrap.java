package de.diruptio.luckyblock;

import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.registrar.RegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
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
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public final class LuckyBlockBootstrap implements PluginBootstrap {
    private Logger logger;

    @Override
    public void bootstrap(@NonNull BootstrapContext context) {
        logger = context.getLogger();
        context.getLifecycleManager().registerEventHandler(RegistryEvents.BLOCK.compose(), this::registerBlocks);
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ITEM.compose(), this::registerItems);
        context.getLifecycleManager().registerEventHandler(LifecycleEvents.DATAPACK_DISCOVERY, this::discoverDatapacks);
    }

    private void registerBlocks(@NotNull RegistryComposeEvent<BlockType, BlockRegistryEntry.Builder> event) {
        register(event, LuckyBlock.KEY, LuckyBlock.BUILDER);
    }

    private void registerItems(@NotNull RegistryComposeEvent<ItemType, ItemRegistryEntry.Builder> event) {
        register(event, LuckyBlock.KEY, LuckyBlock.ITEM_BUILDER);
    }

    private void discoverDatapacks(@NotNull RegistrarEvent<DatapackRegistrar> event) {
        try {
            URI uri = Objects.requireNonNull(getClass().getResource("/datapack")).toURI();
            event.registrar().discoverPack(uri, "provided");
        } catch (URISyntaxException | IOException e) {
            logger.error("Failed to register datapack", e);
        }
    }

    private <T, B extends RegistryBuilder<T>> void register(
            @NotNull RegistryComposeEvent<T, B> event,
            @NotNull Key key,
            @NotNull Consumer<B> builderConsumer
    ) {
        event.registry().register(TypedKey.create(event.registryKey(), key), builderConsumer);
    }
}
