package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import jp.fishmans.moire.internal.ElementHolderExtensions;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Mixin(value = ElementHolder.class, remap = false)
public abstract class ElementHolderMixin implements ElementHolderExtensions {
    @Unique
    private final List<Consumer<ServerPlayNetworkHandler>> moire$startWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Consumer<@Nullable HolderAttachment>> moire$setAttachmentListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<BiConsumer<Entity, Entity.RemovalReason>> moire$entityRemoveListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<BooleanSupplier> moire$tickListeners = new CopyOnWriteArrayList<>();

    @Override
    public void moire$addStartWatchingListener(Consumer<ServerPlayNetworkHandler> consumer) {
        moire$startWatchingListeners.add(consumer);
    }

    @Override
    public void moire$addSetAttachmentListener(Consumer<@Nullable HolderAttachment> consumer) {
        moire$setAttachmentListeners.add(consumer);
    }

    @Override
    public List<? extends BiConsumer<Entity, Entity.RemovalReason>> moire$getEntityRemoveListeners() {
        return moire$entityRemoveListeners;
    }

    @Override
    public void moire$addEntityRemoveListener(BiConsumer<Entity, Entity.RemovalReason> consumer) {
        moire$entityRemoveListeners.add(consumer);
    }

    @Override
    public void moire$addTickListener(BooleanSupplier supplier) {
        moire$tickListeners.add(supplier);
    }

    @Inject(method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void moire$injectStartWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> info) {
        if (info.getReturnValue()) {
            moire$startWatchingListeners.forEach(consumer -> consumer.accept(player));
        }
    }

    @Inject(method = "setAttachment(Leu/pb4/polymer/virtualentity/api/attachment/HolderAttachment;)V", at = @At(value = "TAIL"))
    private void moire$injectSetAttachment(@Nullable HolderAttachment attachment, CallbackInfo info) {
        moire$setAttachmentListeners.forEach(consumer -> consumer.accept(attachment));
    }

    @Inject(method = "tick()V", at = @At(value = "TAIL"))
    private void moire$injectTick(CallbackInfo info) {
        moire$tickListeners.removeAll(
                moire$tickListeners.stream()
                        .filter(supplier -> !supplier.getAsBoolean())
                        .toList()
        );
    }
}
