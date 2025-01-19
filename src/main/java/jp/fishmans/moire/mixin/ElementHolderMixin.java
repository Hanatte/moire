package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import jp.fishmans.moire.internal.AbstractElementExtensions;
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
    private final List<Runnable> moire$tickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private int moire$duration = -1;
    @Unique
    private int moire$tickCount = 0;

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
    public void moire$addTickListener(Runnable runnable) {
        moire$tickListeners.add(runnable);
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
        moire$tickCount++;
        var elementHolder = (ElementHolder) (Object) this;
        for (var element : elementHolder.getElements()) {
            if (element instanceof AbstractElementExtensions extensions) {
                extensions.moire$setTickCount(extensions.moire$getTickCount() + 1);
            }
        }

        moire$tickListeners.forEach(Runnable::run);
        for (var element : elementHolder.getElements()) {
            if (element instanceof AbstractElementExtensions extensions) {
                if (extensions.moire$getDuration() > -1) {
                    extensions.moire$setDuration(extensions.moire$getDuration() - 1);
                    if (extensions.moire$getDuration() <= 0) {
                        elementHolder.removeElement(element);
                    }
                }
            }
        }

        if (moire$duration > -1) {
            --moire$duration;
            if (moire$duration <= 0) {
                elementHolder.destroy();
            }
        }
    }

    @Override
    public int moire$getDuration() {
        return moire$duration;
    }

    @Override
    public void moire$setDuration(int duration) {
        moire$duration = duration;
    }

    @Override
    public int moire$getTickCount() {
        return moire$tickCount;
    }
}
