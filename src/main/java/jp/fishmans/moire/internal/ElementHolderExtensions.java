package jp.fishmans.moire.internal;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ElementHolderExtensions {
    int moire$getAge();

    void moire$addStartWatchingListener(Consumer<ServerPlayNetworkHandler> consumer);

    void moire$addSetAttachmentListener(Consumer<@Nullable HolderAttachment> consumer);

    void moire$addTickListener(Runnable runnable);
}
