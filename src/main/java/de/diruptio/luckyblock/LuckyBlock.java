package de.diruptio.luckyblock;

import io.papermc.paper.block.BlockEntity;
import io.papermc.paper.block.BlockImplementation;
import io.papermc.paper.registry.data.BlockRegistryEntry;
import io.papermc.paper.registry.data.ItemRegistryEntry;
import net.kyori.adventure.key.Key;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class LuckyBlock implements BlockImplementation {
    public static final Key KEY = Key.key("luckyblock:lucky_block");
    public static final Consumer<BlockRegistryEntry.Builder> BUILDER = builder -> builder.key(KEY)
            .hardness(1)
            .blastResistance(10)
            .implementation(new LuckyBlock())
            .blockEntityFactory(LuckyBlockEntity::new);
    public static final Consumer<ItemRegistryEntry.Builder> ITEM_BUILDER = builder -> builder.key(KEY)
            .block(KEY);

    public static class LuckyBlockEntity implements BlockEntity {}
}
