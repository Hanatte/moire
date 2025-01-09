package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import jp.fishmans.moire.internal.ElementHolderExtensions;
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
import java.util.function.Consumer;

@Mixin(value = ElementHolder.class, remap = false)
public abstract class ElementHolderMixin implements ElementHolderExtensions {
    @Unique
    private final List<Consumer<ServerPlayNetworkHandler>> moire$startWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Consumer<@Nullable HolderAttachment>> moire$setAttachmentListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Runnable> moire$tickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private int moire$age;

    @Override
    public int moire$getAge() {
        return moire$age;
    }

    @Override
    public void moire$addStartWatchingListener(Consumer<ServerPlayNetworkHandler> consumer) {
        moire$startWatchingListeners.add(consumer);
    }

    @Override
    public void moire$addSetAttachmentListener(Consumer<@Nullable HolderAttachment> consumer) {
        moire$setAttachmentListeners.add(consumer);
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
        moire$tickListeners.forEach(Runnable::run);
        moire$age++;
    }
}
