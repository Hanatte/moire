package jp.fishmans.moire.internal;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ElementHolderExtensions {
    void moire$addStartWatchingListener(Consumer<ServerPlayNetworkHandler> consumer);

    void moire$addSetAttachmentListener(Consumer<@Nullable HolderAttachment> consumer);

    List<? extends BiConsumer<Entity, Entity.RemovalReason>> moire$getEntityRemoveListeners();

    void moire$addEntityRemoveListener(BiConsumer<Entity, Entity.RemovalReason> consumer);

    void moire$addTickListener(Runnable runnable);
}
