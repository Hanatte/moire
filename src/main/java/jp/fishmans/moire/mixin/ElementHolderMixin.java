package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import jp.fishmans.moire.element.ElementHolderExtensions;
import jp.fishmans.moire.element.listener.*;
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
abstract class ElementHolderMixin implements ElementHolderExtensions {
    @Unique
    private final List<Listener> moire$listeners = new CopyOnWriteArrayList<>();

    @Override
    public void moire$addListener(Listener listener) {
        moire$listeners.add(listener);
    }

    @Override
    public void moire$removeListener(Listener listener) {
        moire$listeners.remove(listener);
    }

    @Override
    public <T extends Listener> void moire$triggerEvent(Class<T> listenerClass, Consumer<T> consumer) {
        moire$listeners.stream()
                .filter(listenerClass::isInstance)
                .map(listenerClass::cast)
                .forEach(consumer);
    }

    @Inject(method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void moire$startWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            moire$triggerEvent(WatchingStartedListener.class, listener -> listener.onWatchingStarted(player));
        }
    }

    @Inject(method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void moire$stopWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            moire$triggerEvent(WatchingStoppedListener.class, listener -> listener.onWatchingStopped(player));
        }
    }

    @Inject(method = "onAttachmentSet", at = @At(value = "TAIL"))
    private void moire$onAttachmentSet(HolderAttachment attachment, @Nullable HolderAttachment oldAttachment, CallbackInfo ci) {
        moire$triggerEvent(AttachmentSetListener.class, listener -> listener.onAttachmentSet(attachment, oldAttachment));
    }

    @Inject(method = "onAttachmentRemoved", at = @At(value = "TAIL"))
    private void moire$onAttachmentRemoved(HolderAttachment oldAttachment, CallbackInfo ci) {
        moire$triggerEvent(AttachmentRemovedListener.class, listener -> listener.onAttachmentRemoved(oldAttachment));
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Leu/pb4/polymer/virtualentity/api/ElementHolder;onTick()V"))
    private void moire$beforeTick(CallbackInfo ci) {
        moire$triggerEvent(PreTickListener.class, PreTickListener::onTick);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", shift = At.Shift.AFTER))
    private void moire$afterTick(CallbackInfo ci) {
        moire$triggerEvent(PostTickListener.class, PostTickListener::onTick);
    }
}
